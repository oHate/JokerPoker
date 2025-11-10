package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.Constant;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PixelatedContentBox extends StackPane {

    private final PixelatedBox container;
    private final Text text;
    private final Node node;
    private final VBox layout;

    public PixelatedContentBox(String text, Color color, Node node) {
        this(text, Color.WHITE, color, node);
    }

    public PixelatedContentBox(String text, Color textColor, Color color, Node node) {
        this.container = new PixelatedBox(color, true);

        this.text = Constant.getText(text, textColor, color.darker());

        this.node = node;

        this.layout = new VBox(Constant.SPACING, this.text, this.node);
        this.layout.setPadding(Constant.PADDING_INSETS);
        this.layout.setAlignment(Pos.CENTER);

        getChildren().addAll(this.container, this.layout);
        setAlignment(Pos.CENTER);
    }

    public PixelatedBox getContainer() {
        return this.container;
    }

    public Text getText() {
        return this.text;
    }

    public VBox getLayout() {
        return this.layout;
    }

    public Node getNode() {
        return this.node;
    }

}
