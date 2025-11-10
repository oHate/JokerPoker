package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.Constant;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PixelatedBox extends StackPane {

    public static final int SHADOW_OFFSET_Y = 4;
    public static final int SHADOW_OFFSET_X = 4;
    public static final int SIZE = 2;

    private final Color color;
    private final Color shadowColor;

    private final Rectangle[] rectangles = new Rectangle[4];
    private final Rectangle[] shadowRectangles = new Rectangle[4];

    public PixelatedBox(Color color) {
        this.color = color;
        this.shadowColor = color.darker();

        setPadding(Constant.PADDING_INSETS);

        initRectangles(this.rectangles, this.color);
        initRectangles(this.shadowRectangles, this.shadowColor);

        getChildren().addAll(this.shadowRectangles);
        getChildren().addAll(this.rectangles);

        for (Rectangle rectangle : this.shadowRectangles) {
            rectangle.setTranslateX(SHADOW_OFFSET_X);
            rectangle.setTranslateY(SHADOW_OFFSET_Y);
        }
    }

    private void initRectangles(Rectangle[] rectangles, Color fill) {
        rectangles[0] = createRectangle(0, 4 * SIZE, fill);
        rectangles[1] = createRectangle(SIZE, 2 * SIZE, fill);
        rectangles[2] = createRectangle(2 * SIZE, SIZE, fill);
        rectangles[3] = createRectangle(4 * SIZE, 0, fill);
    }

    private Rectangle createRectangle(double x, double y, Color fill) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(fill);
        rectangle.setManaged(false);
        return rectangle;
    }

    private void updateRectangles(Rectangle[] rectangles, double width, double height) {
        rectangles[0].setWidth(width);
        rectangles[0].setHeight(Math.max(0, height - 8 * SIZE));

        rectangles[1].setWidth(Math.max(0, width - 2 * SIZE));
        rectangles[1].setHeight(Math.max(0, height - 4 * SIZE));

        rectangles[2].setWidth(Math.max(0, width - 4 * SIZE));
        rectangles[2].setHeight(Math.max(0, height - 2 * SIZE));

        rectangles[3].setWidth(Math.max(0, width - 8 * SIZE));
        rectangles[3].setHeight(height);
    }

    public Color getColor() {
        return this.color;
    }

    public Color getShadowColor() {
        return this.shadowColor;
    }

    public Rectangle[] getRectangles() {
        return this.rectangles;
    }

    public Rectangle[] getShadowRectangles() {
        return this.shadowRectangles;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        double width = Math.max(0, getWidth() - SHADOW_OFFSET_X);
        double height = Math.max(0, getHeight() - SHADOW_OFFSET_Y);

        updateRectangles(this.shadowRectangles, width, height);
        updateRectangles(this.rectangles, width, height);
    }

}