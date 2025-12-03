package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.PlayingCard;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane implements Draggable {

    private final PlayingCard card;
    private final Node view;

    private boolean selected;

    public CardView(PlayingCard card) {
        this.card = card;
        this.view = card.createView();

        getChildren().add(this.view);
    }

    public PlayingCard getCard() {
        return this.card;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void toggleSelected() {
        this.selected = !this.selected;

        setTranslateY(this.selected ? -20 : 0);
    }

}
