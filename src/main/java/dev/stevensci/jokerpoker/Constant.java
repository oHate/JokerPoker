package dev.stevensci.jokerpoker;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.NumberFormat;

public class Constant {

    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    public static final ColumnConstraints COL_50 = createColumnConstraints(50);
    public static final ColumnConstraints COL_45 = createColumnConstraints(45);
    public static final ColumnConstraints COL_10 = createColumnConstraints(10);
    public static final ColumnConstraints COL_70 = createColumnConstraints(70);
    public static final ColumnConstraints COL_30 = createColumnConstraints(30);

    public static final int SPACING = 4;
    public static final int PADDING = 8;
    public static final Insets PADDING_INSETS = new Insets(PADDING);

    public static final Image BACKGROUND_IMAGE;
    public static final SpriteSheet CARD_SPRITESHEET;
    public static final SpriteSheet JOKERS_SPRITESHEET;
    public static final Font FONT;

    static {
        CARD_SPRITESHEET = new SpriteSheet(new Image(
                Constant.class.getResourceAsStream("/images/deck.png"),
                0, 0,
                true,
                false
        ),96, 128);

        JOKERS_SPRITESHEET = new SpriteSheet(new Image(
                Constant.class.getResourceAsStream("/images/jokers.png"),
                0, 0,
                true,
                false
        ),96, 128);

        BACKGROUND_IMAGE = new Image(Constant.class.getResourceAsStream("/images/pokertable.jpg"));

        FONT = Font.loadFont(Constant.class.getResourceAsStream("/fonts/m6x11.ttf"), 32);
    }

    public static final Color LIGHT_BLUE = Color.web("#00A6F4");
    public static final Color BLUE = Color.web("#0084D1");

    public static final Color GRAY = Color.web("#52525C");
    public static final Color DARK_GRAY = Color.web("#27272A");

    public static final Color GREEN = Color.web("#00A63E");

    public static final Color LIGHT_RED = Color.web("#FB2C36");
    public static final Color RED = Color.web("#E7000B");

    public static final Color LIGHT_YELLOW = Color.web("#FFDF20");
    public static final Color YELLOW = Color.web("#FDC700");

    public static final Color LIGHT_ORANGE = Color.web("#FE9A00");
    public static final Color ORANGE = Color.web("#E17100");

    public static final Color LIGHT_PURPLE = Color.web("#8E51FF");
    public static final Color PURPLE = Color.web("#7F22FE");

    public static ColumnConstraints createColumnConstraints(double widthPercent) {
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(widthPercent);
        constraints.setFillWidth(true);
        constraints.setHgrow(Priority.ALWAYS);
        constraints.setHalignment(HPos.CENTER);
        return constraints;
    }

}
