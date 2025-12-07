package dev.stevensci.jokerpoker.controller;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.blind.BlindType;
import dev.stevensci.jokerpoker.blind.HandType;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.util.SortMode;
import dev.stevensci.jokerpoker.view.*;
import javafx.animation.SequentialTransition;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final GameModel model;
    private final GameView view;

    private final ShopController shopController = new ShopController();
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
        Blind blind = this.model.getBlind();
        GamePane game = this.view.getGamePane();

        drawCardsToHand();

        this.model.getBlind().getResult().getHandTypeProperty().addListener(_ -> {
            this.view.updateHandType(this.model.getBlind().getResult().getHandType());
        });

        this.view.getSidebarPane().updateHeader(this.model.getBlindType(), this.model.getTargetScore());
        this.view.getSidebarPane().updateBorder(this.model.getBlindType());

        this.view.updateDiscards(this.model.getBlind().getDiscards().get());
        blind.getDiscards().addListener(_ -> this.view.updateDiscards(blind.getDiscards().get()));

        this.view.updateHands(this.model.getBlind().getHands().get());
        blind.getHands().addListener(_ -> this.view.updateHands(blind.getHands().get()));

        this.view.updateScore(0);
        blind.getScore().addListener(_ -> this.view.updateScore(blind.getScore().get()));

        game.getPlayHandButton().setOnMouseClicked(_ -> {
            if (blind.getHands().get() <= 0 || blind.getResult().getHandType() == HandType.NONE) return;
            blind.decrementHands();


            // TODO -> Lock current joker hand. Get the HBox loop through all jokers and make a list and replace the current joker list with that list.

            this.model.getJokers().clear();
            this.view.getGamePane().getJokerArea().getChildren().forEach(node -> {
                if (!(node instanceof JokerCardView view)) return;
                this.model.getJokers().add(view.getCard());
            });

            blind.processCurrentHand();

            discard();

            // TODO -> Win condition
            if (blind.getScore().get() >= blind.getTargetScore()) {
                this.view.lockMouseClicks();

                List<Pair<String, Integer>> cashBreakdown = new ArrayList<>();

                cashBreakdown.add(new Pair<>(
                        "Score at least: " + blind.getTargetScore(),
                        switch (blind.getType()) {
                            case SMALL -> 3;
                            case BIG -> 4;
                            case BOSS -> 5;
                        }
                ));

                int handsRemaining = blind.getHands().get();

                if (blind.getHands().get() > 0) {
                    cashBreakdown.add(new Pair<>(
                            handsRemaining + " Remaining Hands ($1 each)",
                            handsRemaining
                    ));
                }

                int cashTotal = 0;

                for (Pair<String, Integer> breakdown : cashBreakdown) {
                    cashTotal += breakdown.getValue();
                }

                this.model.getCash().set(this.model.getCash().get() + cashTotal);
                this.view.getContinuePane().setCashEntries(cashBreakdown);

                this.view.getContinuePane().getShowTransition().play();
            }
        });

        game.getDiscardButton().setOnMouseClicked(_ -> {
            if (blind.getSelectedCards().isEmpty()) return;
            if (blind.getDiscards().get() <= 0) return;
            blind.decrementDiscards();
            discard();
        });

        game.getSortRankButton().setOnMouseClicked(_ -> {
            blind.setSortMode(SortMode.RANK);
            game.sortCards(SortMode.RANK);
        });

        game.getSortSuitButton().setOnMouseClicked(_ -> {
            blind.setSortMode(SortMode.SUIT);
            game.sortCards(SortMode.SUIT);
        });

        this.view.getShopPane().getNextRoundButton().setOnMouseClicked(_ -> {
            this.view.getShopPane().getHideTransition().setOnFinished(_ -> {
                Color color = this.model.getBlind().getType().getPrimaryColor();

                this.view.getShopPane().updateHeader(color);
                this.view.getContinuePane().setColor(color);
            });

            this.view.getShopPane().getHideTransition().play();

            if (this.model.getBlindType() == BlindType.BOSS) {
                this.model.getAnte().set(this.model.getAnte().get() + 1);
            }

            this.model.getRound().set(this.model.getRound().get() + 1);

            this.model.updateBlind();
            this.view.getGamePane().reset();

            initialize();

            this.view.unlockMouseClicks();
        });

        this.view.getContinuePane().getContinueButton().setOnMouseClicked(_ -> {
            List<JokerCard> shopJokers = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                shopJokers.add(this.shopController.getRandomJoker());
            }

            List<CardView<JokerCard>> shopJokerViews = this.view.getShopPane().addCards(shopJokers);

            for (CardView<JokerCard> view : shopJokerViews) {
                tooltipManager.attach(view, view.getCard());

                view.setOnMouseClicked(_ -> {
                    JokerCard card = view.getCard();
                    int cost = card.getType().getCost();
                    int balance = this.model.getCash().get();

                    if (balance < cost) return;
                    this.model.getCash().set(balance - cost);

                    this.view.getShopPane().removeCard(view);
                    this.model.getJokers().add(card);
//                    this.view.getGamePane().

                    JokerCardView gameView = new JokerCardView(card);

                    gameView.setOnMouseClicked(event -> {
                        if (event.getButton() != MouseButton.SECONDARY) return;
                        System.out.println("SOLD " + card.getType().getName());
                        // TODO -> sell card
                    });

                    // TODO -> add joker to main pane
                    this.view.getGamePane().getJokerArea().getChildren().add(gameView);
                });
            }

            new SequentialTransition(
                    this.view.getContinuePane().getHideTransition(),
                    this.view.getShopPane().getShowTransition()
            ).play();
        });

    }

    public void discard() {
        this.model.getBlind().discard();
        this.view.getGamePane().discard();
        this.model.getBlind().getResult().updateHandType();
        drawCardsToHand();
    }

    public void drawCardsToHand() {
        Blind blind = this.model.getBlind();
        GamePane view = this.view.getGamePane();

        List<PlayingCard> cards = blind.drawCards();
        List<PlayingCardView> playingCardViews = view.addCards(cards);

        view.sortCards(blind.getSortMode());

        for (PlayingCardView playingCardView : playingCardViews) {
            playingCardView.setOnMouseClicked(event -> {
                PlayingCard card = playingCardView.getCard();

                if (blind.getSelectedCards().size() == 5 && !playingCardView.isSelected()) {
                    return;
                }

                playingCardView.toggleSelected();

                if (playingCardView.isSelected()) {
                    this.model.getBlind().selectCard(card);
                } else {
                    this.model.getBlind().deselectCard(card);
                }
            });
        }
    }

}
