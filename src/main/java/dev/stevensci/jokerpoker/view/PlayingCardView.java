package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.PlayingCard;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class PlayingCardView extends CardView<PlayingCard> {

    private boolean selected;

    public PlayingCardView(PlayingCard card) {
        super(card);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void toggleSelected() {
        this.selected = !this.selected;
        setTranslateY(this.selected ? -20 : 0);
    }

}
