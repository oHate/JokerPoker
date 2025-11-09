package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.Constant;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PixelatedBox extends Region {

    private final Color color;
    private final int size;
    private final boolean shadow;
    private final int shadowOffsetX;
    private final int shadowOffsetY;

    private Group mainBox;
    private Group shadowBox;

    private PixelatedBox(Builder builder) {
        this.color = builder.color;
        this.shadow = builder.shadow;
        this.shadowOffsetX = builder.shadowOffsetX;
        this.shadowOffsetY = builder.shadowOffsetY;
        this.size = builder.size;

        this.mainBox = new Group();
        this.shadowBox = new Group();

        getChildren().addAll(this.shadowBox, this.mainBox);
    }

    public PixelatedBox(Color color) {
        this(new Builder().color(color));
    }

    public PixelatedBox(Color color, boolean shadow) {
        this(new Builder().color(color).shadow(shadow));
    }

    @Override
    protected void layoutChildren() {
        getChildren().clear();

        double dx = this.shadow ? this.shadowOffsetX : 0;
        double dy = this.shadow ? this.shadowOffsetY : 0;

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
                new Rectangle(0, 4 * this.size, width, Math.max(0, height - 8 * this.size)) {{
                    setFill(color);
                }},
                new Rectangle(this.size, 2 * this.size, Math.max(0, width - 2 * this.size), Math.max(0, height - 4 * this.size)) {{
                    setFill(color);
                }},
                new Rectangle(2 * this.size, this.size, Math.max(0, width - 4 * this.size), Math.max(0, height - 2 * this.size)) {{
                    setFill(color);
                }},
                new Rectangle(4 * this.size, 0, Math.max(0, width - 8 * this.size), height) {{
                    setFill(color);
                }}
        );
    }

    public int getShadowOffsetX() {
        return shadow ? this.shadowOffsetX : 0;
    }

    public int getShadowOffsetY() {
        return shadow ? this.shadowOffsetY : 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Color color = Constant.GRAY;

        private int size = 2;

        private boolean shadow = true;
        private int shadowOffsetY = 4;
        private int shadowOffsetX = 4;

        private Builder() {
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder shadow(boolean shadow) {
            this.shadow = shadow;
            return this;
        }

        public Builder shadowOffsetY(int shadowOffsetY) {
            this.shadowOffsetY = shadowOffsetY;
            return this;
        }

        public Builder shadowOffsetX(int shadowOffsetX) {
            this.shadowOffsetX = shadowOffsetX;
            return this;
        }

        public PixelatedBox build() {
            return new PixelatedBox(this);
        }

    }

}
