package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.PlayingCard;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane implements Draggable {

    private final PlayingCard playingCard;

    public CardView(PlayingCard card) {
        this.playingCard = card;
        getChildren().add(card.getView());
    }

    public PlayingCard getPlayingCard() {
        return playingCard;
    }
}
