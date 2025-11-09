package dev.stevensci.jokerpoker;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Constant {

    public static final Font FONT;

    static {
        FONT = Font.loadFont(Constant.class.getResourceAsStream("/fonts/m6x11.ttf"), 36);
    }

    public static final Color BLUE = Color.web("#0084D1");
    public static final Color GRAY = Color.web("#52525C");
    public static final Color DARK_GRAY = Color.web("#27272A");
    public static final Color GREEN = Color.web("#00A63E");
    public static final Color RED = Color.web("#E7000B");
    public static final Color YELLOW = Color.web("#FDC700");
    public static final Color LIGHT_ORANGE = Color.web("#FE9A00");
    public static final Color ORANGE = Color.web("#E17100");
    public static final Color PURPLE = Color.web("#7F22FE");

}
