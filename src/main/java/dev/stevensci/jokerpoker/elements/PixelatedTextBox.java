package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.Constant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PixelatedTextBox extends StackPane {

    private final PixelatedBox container;
    private final Text text;
    private final StackPane textWrapper;

    public PixelatedTextBox(Color containerColor, String text) {
        this(containerColor, Color.WHITE, text);
    }

    public PixelatedTextBox(Color containerColor, Color textColor, String text) {
        this.container = new PixelatedBox(containerColor);

        this.text = new Text(text);
        this.text.setFill(textColor);
        this.text.setFont(Constant.FONT);
        this.text.setEffect(new DropShadow(0, 2, 2, containerColor.darker()));

        this.textWrapper = new StackPane(this.text);
        this.textWrapper.setPadding(new Insets(8));
        this.textWrapper.setAlignment(Pos.CENTER);

        getChildren().addAll(this.container, this.textWrapper);
        setAlignment(Pos.CENTER);
    }

    public PixelatedBox getContainer() {
        return this.container;
    }

    public Text getText() {
        return this.text;
    }

    public StackPane getTextWrapper() {
        return this.textWrapper;
    }

}
