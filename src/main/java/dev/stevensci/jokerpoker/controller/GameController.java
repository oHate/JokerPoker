package dev.stevensci.jokerpoker.controller;

import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.util.SortMode;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.view.CardPane;
import dev.stevensci.jokerpoker.view.CardView;
import dev.stevensci.jokerpoker.view.GameView;

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
        blind.getDiscards().addListener((ov, oldAmount, newAmount) -> {
            this.view.updateDiscards(newAmount.intValue());
        });

        this.view.updateHands(this.model.getBlind().getHands().get());
        blind.getHands().addListener((ov, oldAmount, newAmount) -> {
            this.view.updateHands(newAmount.intValue());
        });

        blind.getScore().addListener((ov, oldScore, newScore) -> {
            this.view.updateScore(newScore.longValue());
        });

        game.getPlayHandButton().setOnMouseClicked(event -> {
            blind.decrementHands();
            blind.processCurrentHand();
            discard();
        });

        game.getDiscardButton().setOnMouseClicked(event -> {
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
