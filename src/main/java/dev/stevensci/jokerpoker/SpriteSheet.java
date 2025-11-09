package dev.stevensci.jokerpoker;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteSheet {

    private final Image spriteSheet;
    private final int width;
    private final int height;

    public SpriteSheet(String texturePath, int width, int height) {
        this.spriteSheet = new Image(
                getClass().getResourceAsStream(texturePath),
                0, 0,
                true,
                false
        );

        this.width = width;
        this.height = height;
    }

    public ImageView getView(int x, int y) {
        ImageView view = new ImageView(this.spriteSheet);
        view.setViewport(new Rectangle2D(x * this.width, y * this.height, this.width, this.height));
        view.setSmooth(false);
        return view;
    }

}
