package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.view.CardView;
import dev.stevensci.jokerpoker.view.GameView;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        setupCardViews();

        this.model.getBlind().getResult().getHandTypeProperty().addListener((ov, oldType, newType) -> {
           this.view.updateHandType(newType);
        });
    }

    private void setupCardViews() {
        this.view.getCards().clear();

        for (PlayingCard card : this.model.getDeck()) {
            CardView cardView = new CardView(card);

            cardView.setOnMouseClicked(event -> {
                cardView.toggleSelected();

                if (cardView.isSelected()) {
                    this.model.getBlind().selectCard(card);
                } else {
                    this.model.getBlind().deselectCard(card);
                }
            });

            this.view.getCards().put(card, cardView);
        }

        List<CardView> currentHand = new ArrayList<>();

        for (PlayingCard card : this.model.getBlind().getHand()) {
            currentHand.add(this.view.getCards().get(card));
        }

        this.view.getGamePane().populateHand(currentHand);
    }

    public void initialize() {
        // TODO -> remove
        this.view.initializeSidebar(this.model.getBlindType(), this.model.getTargetScore());

        this.model.getBlind().getScore().addListener((ov, oldScore, newScore) -> {
            this.view.updateScore(newScore.longValue());
        });

        this.view.getGamePane().getPlayHandButton().setOnMouseClicked(event -> {
            this.model.getBlind().processCurrentHand();
        });
    }

    private void populateHand() {
        List<CardView> currentHand = new ArrayList<>();

        for (PlayingCard card : this.model.getBlind().getHand()) {
            currentHand.add(this.view.getCards().get(card));
        }

        this.view.getGamePane().populateHand(currentHand);
    }

}
