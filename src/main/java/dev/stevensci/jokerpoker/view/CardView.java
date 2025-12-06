package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.Card;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class CardView<T extends Card> extends StackPane implements Draggable {

    private final T card;
    private final Node view;

    public CardView(T card) {
        this.card = card;
        this.view = card.createView();

        getChildren().add(this.view);
    }

    public T getCard() {
        return this.card;
    }

    public Node getView() {
        return this.view;
    }

}
