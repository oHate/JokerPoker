package dev.stevensci.jokerpoker.controller;

import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.util.SortMode;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.view.CardPane;
import dev.stevensci.jokerpoker.view.CardView;
import dev.stevensci.jokerpoker.view.GameView;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        drawCardsToHand();

        this.model.getBlind().getResult().getHandTypeProperty().addListener((ov, oldType, newType) -> {
            this.view.updateHandType(newType);
        });
    }

    public void initialize() {
        Blind blind = this.model.getBlind();
        CardPane game = this.view.getGamePane();

        this.view.initializeSidebar(this.model.getBlindType(), this.model.getTargetScore());

        this.view.updateDiscards(this.model.getBlind().getDiscards().get());
        blind.getDiscards().addListener(_ -> this.view.updateDiscards(blind.getDiscards().get()));

        this.view.updateHands(this.model.getBlind().getHands().get());
        blind.getHands().addListener(_ -> this.view.updateHands(blind.getHands().get()));

        blind.getScore().addListener(_ -> this.view.updateScore(blind.getScore().get()));

        game.getPlayHandButton().setOnMouseClicked(event -> {
            if (blind.getHands().get() <= 0) return;
            blind.decrementHands();
            blind.processCurrentHand();
//            discard();

            final Duration delayBetween = Duration.millis(120);  // stagger

            List<Node> nodes = new ArrayList<>(this.view.getGamePane().getCardArea().getChildren());
            nodes.removeIf(node -> !(node instanceof CardView cardView) || !cardView.isSelected());

            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                
//                showDiamond(this.view.getOverlayPane(), node);
//
//                playSingleAnimation(node, delayBetween.multiply(i));
            }

//            if (blind.getScore().get() >= blind.getTargetScore()) {
//                // TODO -> Win condition
//            }
        });

        game.getDiscardButton().setOnMouseClicked(event -> {
            if (blind.getSelectedCards().isEmpty()) return;
            if (blind.getDiscards().get() <= 0) return;
            blind.decrementDiscards();
            discard();
        });

        game.getSortRankButton().setOnMouseClicked(event -> {
            blind.setSortMode(SortMode.RANK);
            game.sortCards(SortMode.RANK);
        });

        game.getSortSuitButton().setOnMouseClicked(event -> {
            blind.setSortMode(SortMode.SUIT);
            game.sortCards(SortMode.SUIT);
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
        CardPane view = this.view.getGamePane();

        List<PlayingCard> cards = blind.drawCards();
        List<CardView> cardViews = view.addCards(cards);

        view.sortCards(blind.getSortMode());

        for (CardView cardView : cardViews) {
            cardView.setOnMouseClicked(event -> {
                PlayingCard card = (PlayingCard) cardView.getCard();

                if (blind.getSelectedCards().size() == 5 && !cardView.isSelected()) {
                    return;
                }

                cardView.toggleSelected();

                if (cardView.isSelected()) {
                    this.model.getBlind().selectCard(card);
                } else {
                    this.model.getBlind().deselectCard(card);
                }
            });
        }
    }

}
