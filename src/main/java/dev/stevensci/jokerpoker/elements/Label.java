package dev.stevensci.jokerpoker.elements;

import dev.stevensci.jokerpoker.util.Constant;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Label extends Text {

    public static final int TEXT_SHADOW_OFFSET = 2;

    public Label(String value, Color color, Color shadowColor) {
        this(value, Constant.FONT_32, color, shadowColor);
    }

    public Label(String value, Font font, Color color, Color shadowColor) {
        super(value);
        setFont(font);
        setFill(color);
        setEffect(new DropShadow(0, TEXT_SHADOW_OFFSET, TEXT_SHADOW_OFFSET, shadowColor));
    }

}
