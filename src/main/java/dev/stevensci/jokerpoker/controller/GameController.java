package dev.stevensci.jokerpoker.controller;

import dev.stevensci.jokerpoker.blind.BlindType;
import dev.stevensci.jokerpoker.blind.HandType;
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

        model.getRound().addListener(_ -> view.updateRound(model.getRound().get()));
        model.getAnte().addListener(_ -> view.updateAnte(model.getAnte().get()));
    }

    public void initialize() {
        Blind blind = this.model.getBlind();
        CardPane game = this.view.getGamePane();

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

        blind.getScore().addListener(_ -> this.view.updateScore(blind.getScore().get()));

        game.getPlayHandButton().setOnMouseClicked(_ -> {
            if (blind.getHands().get() <= 0 || blind.getResult().getHandType() == HandType.NONE) return;
            blind.decrementHands();
            blind.processCurrentHand();

            discard();

            this.view.getContinuePane().display();
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

        this.view.getContinuePane().getContinueButton().setOnMouseClicked(_ -> {
            // TODO -> Add round money

            if (this.model.getBlindType() == BlindType.BOSS) {
                this.model.getAnte().set(this.model.getAnte().get() + 1);
            }

            this.model.getRound().set(this.model.getRound().get() + 1);

            this.view.getContinuePane().hide();
            this.model.updateBlind();
            this.view.getGamePane().reset();

            initialize();
        });

//        this.view.getShopPane().getContinueButton().setOnMouseClicked(_ -> {
//             TODO ->
//        });

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
                PlayingCard card = cardView.getCard();

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
