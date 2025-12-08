package dev.stevensci.jokerpoker.view.node;

import dev.stevensci.jokerpoker.card.PlayingCard;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class PlayingCardNode extends CardNode<PlayingCard> {

    private boolean selected;

    public PlayingCardNode(PlayingCard card) {
        super(card);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void toggleSelected() {
        this.selected = !this.selected;

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), this);
        transition.setToY(this.selected ? -30 : 0);
        transition.play();
    }

}
