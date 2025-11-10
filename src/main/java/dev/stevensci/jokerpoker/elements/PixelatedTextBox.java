package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.Constant;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PixelatedTextBox extends StackPane {

    private final PixelatedBox container;
    private final Text text;
    private final StackPane layout;

    public PixelatedTextBox(String text, Color containerColor) {
        this(text, Color.WHITE, containerColor);
    }

    public PixelatedTextBox(String text, Color textColor, Color containerColor) {
        this.container = new PixelatedBox(containerColor);

        this.text = Constant.getText(text, textColor, containerColor.darker());

        this.layout = new StackPane(this.text);
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

    public StackPane getLayout() {
        return this.layout;
    }

}
