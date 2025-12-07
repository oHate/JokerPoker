package dev.stevensci.jokerpoker.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PixelatedButton extends PixelatedContentBox {

    private final Label label;
    private final Color color;
    private final Color pressColor;

    public PixelatedButton(Label label, Color color) {
        super(color);

        this.label = label;

        this.color = color;
        this.pressColor = color.deriveColor(0, 1, 0.85, 1);

        getLayout().getChildren().add(this.label);
        setupEventHandlers();
    }
    private void setupEventHandlers() {
        setOnMouseEntered(e -> setColor(this.pressColor));
        setOnMouseExited(e -> setColor(this.color));

        setOnMousePressed(e -> {
            for (Rectangle rectangle : getRectangles()) {
                rectangle.setTranslateX(PixelatedBox.SHADOW_OFFSET_X);
                rectangle.setTranslateY(PixelatedBox.SHADOW_OFFSET_Y);
                rectangle.setFill(this.pressColor);
            }

            this.label.setTranslateX(PixelatedBox.SHADOW_OFFSET_X);
            this.label.setTranslateY(PixelatedBox.SHADOW_OFFSET_Y);
        });

        setOnMouseReleased(e -> {
            for (Rectangle rectangle : getRectangles()) {
                rectangle.setTranslateX(0);
                rectangle.setTranslateY(0);
                rectangle.setFill(this.color);
            }

            this.label.setTranslateX(0);
            this.label.setTranslateY(0);

            setColor(isHover() ? this.pressColor : this.color);
        });
    }

    public Label getLabel() {
        return this.label;
    }
}
