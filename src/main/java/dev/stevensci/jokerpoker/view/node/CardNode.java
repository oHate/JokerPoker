package dev.stevensci.jokerpoker.view.node;

import dev.stevensci.jokerpoker.card.Card;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class CardNode<T extends Card> extends StackPane implements Draggable {

    private final T card;
    private final Node view;

    public CardNode(T card) {
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
