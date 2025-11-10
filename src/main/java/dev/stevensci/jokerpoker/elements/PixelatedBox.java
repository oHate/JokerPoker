package dev.stevensci.jokerpoker.elements;

import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PixelatedBox extends Region {

    public static final int SHADOW_OFFSET_Y = 4;
    public static final int SHADOW_OFFSET_X = 4;
    public static final int SIZE = 2;

    private final Color color;
    private final boolean shadow;

    private Group mainBox;
    private Group shadowBox;

    public PixelatedBox(Color color) {
        this(color, true);
    }

    public PixelatedBox(Color color, boolean shadow) {
        this.color = color;
        this.shadow = shadow;

        this.mainBox = new Group();
        this.shadowBox = new Group();

        getChildren().addAll(this.shadowBox, this.mainBox);
    }

    @Override
    protected void layoutChildren() {
        getChildren().clear();

        double dx = this.shadow ? SHADOW_OFFSET_X : 0;
        double dy = this.shadow ? SHADOW_OFFSET_Y : 0;

        double contentWidth = Math.max(0, getWidth() - dx);
        double contentHeight = Math.max(0, getHeight() - dy);

        if (this.shadow) {
            this.shadowBox = createBoxGroup(this.color.darker(), contentWidth, contentHeight);
            this.shadowBox.setTranslateX(dx);
            this.shadowBox.setTranslateY(dy);
            getChildren().add(this.shadowBox);
        }

        this.mainBox = createBoxGroup(this.color, contentWidth, contentHeight);
        getChildren().add(this.mainBox);
    }

    private Group createBoxGroup(Color color, double width, double height) {
        return new Group(
                new Rectangle(0, 4 * SIZE, width, Math.max(0, height - 8 * SIZE)) {{
                    setFill(color);
                }},
                new Rectangle(SIZE, 2 * SIZE, Math.max(0, width - 2 * SIZE), Math.max(0, height - 4 * SIZE)) {{
                    setFill(color);
                }},
                new Rectangle(2 * SIZE, SIZE, Math.max(0, width - 4 * SIZE), Math.max(0, height - 2 * SIZE)) {{
                    setFill(color);
                }},
                new Rectangle(4 * SIZE, 0, Math.max(0, width - 8 * SIZE), height) {{
                    setFill(color);
                }}
        );
    }

}
