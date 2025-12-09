package dev.stevensci.jokerpoker.controller;

import dev.stevensci.jokerpoker.model.BlindType;
import dev.stevensci.jokerpoker.model.HandResult;
import dev.stevensci.jokerpoker.model.HandType;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.view.elements.PixelatedButton;
import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.util.AnimationUtil;
import dev.stevensci.jokerpoker.util.SortMode;
import dev.stevensci.jokerpoker.view.GameView;
import dev.stevensci.jokerpoker.view.manager.TooltipManager;
import dev.stevensci.jokerpoker.view.node.CardNode;
import dev.stevensci.jokerpoker.view.node.JokerCardNode;
import dev.stevensci.jokerpoker.view.node.PlayingCardNode;
import dev.stevensci.jokerpoker.view.pane.ContinuePane;
import dev.stevensci.jokerpoker.view.pane.GameOverPane;
import dev.stevensci.jokerpoker.view.pane.GamePane;
import dev.stevensci.jokerpoker.view.pane.ShopPane;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.IntegerProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameController {

    private final GameModel model;
    private final GameView view;

    private final TooltipManager tooltipManager;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.tooltipManager = new TooltipManager(this.view.getOverlayPane());

        model.getCash().addListener(_ -> view.updateCash(model.getCash().get()));
        model.getRound().addListener(_ -> view.updateRound(model.getRound().get()));
        model.getAnte().addListener(_ -> view.updateAnte(model.getAnte().get()));
    }

    public void initialize() {
        IntegerProperty rerollCost = this.model.getRerollCost();
        PixelatedButton rerollButton = this.view.getShopPane().getRerollButton();
        rerollCost.addListener(_ -> rerollButton.getLabel().setText("Reroll $" + rerollCost.get()));

        HandResult result = this.model.getResult();
        result.getHandTypeProperty().addListener(_ -> this.view.updateHandType(result.getHandType()));

        // TODO -> store curren blind type and bind it to a objectproperty
        this.model.getBlindType().addListener(_ -> {
            BlindType type = this.model.getBlindType().get();

            this.view.getSidebarPane().updateSidebar(type, this.model.getTargetScore());

            this.view.getShopPane().getHideTransition().setOnFinished(_ -> {
                Color color = type.getPrimaryColor();

                this.view.getShopPane().updateHeader(color);
                this.view.getContinuePane().setColor(color);
            });
        });

        this.model.getDiscards().addListener(_ -> this.view.updateDiscards(this.model.getDiscards().get()));

        this.model.getHands().addListener(_ -> this.view.updateHands(this.model.getHands().get()));

        this.model.getScore().addListener(_ -> this.view.updateScore(this.model.getScore().get()));

        this.model.getResultChips().addListener(_ -> this.view.getSidebarPane().getChipsLabel().setText(String.valueOf(this.model.getResultChips().get())));

        this.model.getResultMultiplier().addListener(_ -> this.view.getSidebarPane().getMultLabel().setText(String.valueOf(this.model.getResultMultiplier().get())));

        setupPlayHandButton();
        setupDiscardButton();

        setupSortRankButton();
        setupSortSuitButton();

        setupNextRoundButton();
        setupContinueButton();
        setupRerollButton();

        setupRestartButton();
        setupExitButton();

        this.model.initialize();
        drawCardsToHand();
    }

    private void setupPlayHandButton() {
        GamePane game = this.view.getGamePane();

        game.getPlayHandButton().setOnMouseClicked(_ -> {
            if (this.model.getHands().get() <= 0 || this.model.getResult().getHandType() == HandType.NONE) return;

            this.view.lockMouseClicks();
            this.model.decrementHands();

            this.model.getJokers().clear();
            this.view.getGamePane().getJokerArea().getChildren().forEach(node -> {
                if (node instanceof JokerCardNode view) {
                    this.model.getJokers().add(view.getCard());
                }
            });

            // TODO -> Should be tracked internally
            Map<PlayingCard, PlayingCardNode> map =
                    this.view.getGamePane().getCardArea().getChildren().stream()
                            .filter(node -> node instanceof PlayingCardNode)
                            .map(node -> (PlayingCardNode) node)
                            .collect(Collectors.toMap(
                                    PlayingCardNode::getCard,
                                    Function.identity()
                            ));

            this.model.startHand();

            SequentialTransition sequence = new SequentialTransition();

            Pane overlay = this.view.getOverlayPane();

            for (int i = 0; i < this.model.getSelectedCards().size(); i++) {
                if (this.model.getResult().getSkippedIndexes().contains(i)) continue;

                PlayingCard card = this.model.getSelectedCards().get(i);
                PlayingCardNode node = map.get(card);
                if (node == null) continue;

                ParallelTransition cardAnimation = new ParallelTransition(
                        AnimationUtil.buildWiggleAnimation(node),
                        AnimationUtil.buildPopupDiamondAnimation(overlay, node, "+" + card.getRank().getChips())
                );

                cardAnimation.setOnFinished(_ -> this.model.scoreCard(card));
                sequence.getChildren().add(cardAnimation);
            }

            sequence.getChildren().add(new PauseTransition(Duration.millis(500)));

            sequence.setOnFinished(e -> {
                this.model.finishHand();

                discard();

                if (this.model.getScore().get() >= this.model.getTargetScore()) {
                    List<Pair<String, Integer>> cashBreakdown = new ArrayList<>();

                    cashBreakdown.add(new Pair<>(
                            "Score at least: " + this.model.getTargetScore(),
                            switch (this.model.getBlindType().get()) {
                                case SMALL -> 3;
                                case BIG -> 4;
                                case BOSS -> 5;
                            }
                    ));

                    int handsRemaining = this.model.getHands().get();

                    if (handsRemaining > 0) {
                        cashBreakdown.add(new Pair<>(
                                handsRemaining + " Remaining Hands ($1 each)",
                                handsRemaining
                        ));
                    }

                    int cashTotal = cashBreakdown.stream()
                            .mapToInt(Pair::getValue)
                            .sum();

                    this.model.getCash().set(this.model.getCash().get() + cashTotal);
                    this.view.getContinuePane().setCashEntries(cashBreakdown);
                    this.view.getContinuePane().getShowTransition().play();
                } else if (this.model.getHands().get() <= 0) {
                    this.view.getGameOverPane().getShowTransition().play();
                } else {
                    this.view.unlockMouseClicks();
                }
            });

            sequence.play();
        });
    }

    private void setupDiscardButton() {
        GamePane game = this.view.getGamePane();

        game.getDiscardButton().setOnMouseClicked(_ -> {
            if (this.model.getSelectedCards().isEmpty()) return;
            if (this.model.getDiscards().get() <= 0) return;
            this.model.decrementDiscards();
            discard();
        });
    }

    private void setupSortRankButton() {
        this.view.getGamePane().getSortRankButton().setOnMouseClicked(_ -> setSortMode(SortMode.RANK));
    }

    private void setupSortSuitButton() {
        this.view.getGamePane().getSortSuitButton().setOnMouseClicked(_ -> setSortMode(SortMode.SUIT));
    }

    private void setSortMode(SortMode mode) {
        this.model.setSortMode(mode);
        this.view.getGamePane().sortCards(mode);
    }

    private void setupNextRoundButton() {
        ShopPane shopPane = this.view.getShopPane();

        shopPane.getNextRoundButton().setOnMouseClicked(_ -> {
            shopPane.getHideTransition().play();

            if (this.model.getBlindType().get() == BlindType.BOSS) {
                this.model.incrementAnte();
            }

            this.model.incrementRound();
            this.view.getGamePane().getCardArea().getChildren().clear();

            this.model.initialize();
            drawCardsToHand();

            this.view.unlockMouseClicks();
        });
    }

    private void setupContinueButton() {
        ContinuePane continuePane = this.view.getContinuePane();

        continuePane.getContinueButton().setOnMouseClicked(_ -> {
            setupShopJokers();

            new SequentialTransition(
                    this.view.getContinuePane().getHideTransition(),
                    this.view.getShopPane().getShowTransition()
            ).play();
        });
    }

    private void setupShopJokers() {
        ShopPane shopPane = this.view.getShopPane();
        shopPane.getJokerArea().getChildren().clear();

        List<JokerCard> jokerCards = new ArrayList<>();
        Set<JokerType> used = new HashSet<>();

        JokerType[] types = JokerType.values();

        while (jokerCards.size() < 4) {
            JokerType type = types[(int)(Math.random() * types.length)];

            if (used.add(type)) {
                jokerCards.add(type.getSupplier().get());
            }
        }

        for (CardNode<JokerCard> node : shopPane.addCards(jokerCards)) {
            this.tooltipManager.attach(node, node.getCard());

            node.setOnMouseClicked(_ -> {
                if (this.model.getJokers().size() >= GameModel.DEFAULT_JOKER_SIZE) return;

                JokerCard card = node.getCard();
                int cost = card.getType().getCost();
                int balance = this.model.getCash().get();

                if (balance < cost) return;
                this.model.getCash().set(balance - cost);

                this.view.getShopPane().removeCard(node);
                this.model.getJokers().add(card);

                JokerCardNode gameView = new JokerCardNode(card);

                this.tooltipManager.attach(gameView, gameView.getCard());

                gameView.setOnMouseClicked(event -> {
                    if (event.getButton() != MouseButton.SECONDARY) return;
                    this.view.getGamePane().getJokerArea().getChildren().remove(gameView);
                    this.model.getCash().set(this.model.getCash().get() + (cost / 2));
                    this.model.getJokers().remove(card);
                    this.view.getGamePane().getJokerCountLabel().setText(this.model.getJokers().size() + "/5");
                });

                this.view.getGamePane().getJokerArea().getChildren().add(gameView);
                this.view.getGamePane().getJokerCountLabel().setText(this.model.getJokers().size() + "/5");
            });
        }
    }

    private void setupRerollButton() {
        PixelatedButton rerollButton = this.view.getShopPane().getRerollButton();

        rerollButton.setOnMouseClicked(_ -> {
            if (this.model.getCash().get() < this.model.getRerollCost().get()) return;

            this.model.subtractCash(this.model.getRerollCost().get());
            this.model.increaseRerollCost();

            setupShopJokers();
        });
    }

    private void setupRestartButton() {
        GameOverPane gameOverPane = this.view.getGameOverPane();

        gameOverPane.getRestartButton().setOnMouseClicked(_ -> {
            gameOverPane.getHideTransition().play();

            if (this.model.getBlindType().get() == BlindType.BOSS) {
                this.model.incrementAnte();
            }

            this.model.getCash().set(0);
            this.model.getRound().set(1);
            this.model.getAnte().set(1);
            this.view.getGamePane().getCardArea().getChildren().clear();

            this.model.initialize();
            drawCardsToHand();

            this.view.unlockMouseClicks();
        });
    }

    private void setupExitButton() {
        this.view.getGameOverPane().getExitButton().setOnMouseClicked(_ -> System.exit(0));
    }

    public void discard() {
        this.model.discard();
        this.view.getGamePane().discard();
        this.model.getResult().updateHandType();
        drawCardsToHand();
    }

    public void drawCardsToHand() {
        GamePane view = this.view.getGamePane();

        List<PlayingCard> cards = this.model.drawCards();
        List<PlayingCardNode> playingCardViews = view.addCards(cards);

        view.sortCards(this.model.getSortMode());

        for (PlayingCardNode playingCardView : playingCardViews) {
            playingCardView.setOnMouseClicked(event -> {
                PlayingCard card = playingCardView.getCard();

                if (this.model.getSelectedCards().size() == 5 && !playingCardView.isSelected()) {
                    return;
                }

                playingCardView.toggleSelected();

                if (playingCardView.isSelected()) {
                    this.model.selectCard(card);
                } else {
                    this.model.deselectCard(card);
                }
            });
        }
    }

}
