package dev.stevensci.jokerpoker.view;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class JokerCardView extends StackPane implements Draggable {

    private final JokerCard jokerCard;
    private final Node view;

    public JokerCardView(JokerCard jokerCard) {
        this.jokerCard = jokerCard;
        this.view = jokerCard.createView();

        getChildren().add(this.view);
    }

    public JokerCard getCard() {
        return this.jokerCard;
    }

    public Node getView() {
        return this.view;
    }

}
