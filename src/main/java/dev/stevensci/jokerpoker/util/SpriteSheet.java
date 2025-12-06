package dev.stevensci.jokerpoker.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteSheet {

    private final Image spriteSheet;
    private final int spriteWidth;
    private final int spriteHeight;

    public SpriteSheet(Image texture, int spriteWidth, int spriteHeight) {
        this.spriteSheet = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public ImageView getView(int row, int col) {
        ImageView view = new ImageView(this.spriteSheet);
        view.setViewport(new Rectangle2D(col * this.spriteWidth, row * this.spriteHeight, this.spriteWidth, this.spriteHeight));
        view.setSmooth(false);
        return view;
    }

}
