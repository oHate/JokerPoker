package dev.stevensci.jokerpoker;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.InputStream;

public class Constant {

    public static final Color BLUE_COLOR = Color.web("#0084D1");
    public static final Color GRAY_COLOR = Color.web("#52525C");
    public static final Color DARK_GRAY_COLOR = Color.web("#27272A");
    public static final Color GREEN_COLOR = Color.web("#00A63E");
    public static final Color RED_COLOR = Color.web("#E7000B");
    public static final Color YELLOW_COLOR = Color.web("#FDC700");
    public static final Color ORANGE_COLOR = Color.web("#E17100");
    public static final Color PURPLE_COLOR = Color.web("#7F22FE");

    public static final Font FONT = Font.loadFont(Constant.class.getResourceAsStream("/fonts/m6x11.ttf"), 28);

}
