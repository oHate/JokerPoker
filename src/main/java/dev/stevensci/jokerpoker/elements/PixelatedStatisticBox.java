package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.Constant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PixelatedStatisticBox extends StackPane {

    private final PixelatedBox container;
    private final Text text;
    private final VBox layout;

    public PixelatedStatisticBox(Color color, String text, PixelatedTextBox innerBox) {
        this.container = new PixelatedBox(color, true);

        this.text = new Text(text);
        this.text.setFill(Color.WHITE);
        this.text.setFont(Constant.FONT);
        this.text.setEffect(new DropShadow(0, 2, 2, color.darker()));

        this.layout = new VBox(4, this.text, innerBox);
        this.layout.setPadding(new Insets(8, 8 + this.container.getShadowOffsetX(), 8 + this.container.getShadowOffsetY(), 8));
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

}
