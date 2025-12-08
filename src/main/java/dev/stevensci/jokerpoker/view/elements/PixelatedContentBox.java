package dev.stevensci.jokerpoker.view.elements;

import dev.stevensci.jokerpoker.util.Constant;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PixelatedContentBox extends PixelatedBox {

    private final VBox layout;

    public PixelatedContentBox(Color color, Node... nodes) {
        super(color);

        this.layout = new VBox(Constant.SPACING, nodes);
        this.layout.setAlignment(Pos.CENTER);

        getChildren().addAll(this.layout);
    }

    public VBox getLayout() {
        return this.layout;
    }

}
