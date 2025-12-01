package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
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
        // TODO -> remove
        this.view.initializeSidebar(this.model.getBlindType(), this.model.getTargetScore());

        this.view.updateDiscards(this.model.getBlind().getDiscards().get());
        this.model.getBlind().getDiscards().addListener((ov, oldAmount, newAmount) -> {
            this.view.updateDiscards(newAmount.intValue());
        });

        this.view.updateHands(this.model.getBlind().getHands().get());
        this.model.getBlind().getHands().addListener((ov, oldAmount, newAmount) -> {
            this.view.updateHands(newAmount.intValue());
        });

        this.model.getBlind().getScore().addListener((ov, oldScore, newScore) -> {
            this.view.updateScore(newScore.longValue());
        });

        this.view.getGamePane().getPlayHandButton().setOnMouseClicked(event -> {
            this.model.getBlind().decrementHands();
            this.model.getBlind().processCurrentHand();
            discard();
        });

        this.view.getGamePane().getDiscardButton().setOnMouseClicked(event -> {
            this.model.getBlind().decrementDiscards();
            discard();
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
        List<PlayingCard> cards = blind.drawCards();
        List<CardView> views = this.view.getGamePane().addCards(cards);

        for (CardView view : views) {
            view.setOnMouseClicked(event -> {
                if (!(view.getCard() instanceof PlayingCard card)) {
                    return;
                }

                if (this.model.getBlind().getSelectedCards().size() == 5 && !view.isSelected()) {
                    return;
                }

                view.toggleSelected();

                if (view.isSelected()) {
                    this.model.getBlind().selectCard(card);
                } else {
                    this.model.getBlind().deselectCard(card);
                }
            });
        }
    }

}
