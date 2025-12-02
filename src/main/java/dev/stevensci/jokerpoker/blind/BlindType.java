package dev.stevensci.jokerpoker.blind;

import dev.stevensci.jokerpoker.util.Constant;
import javafx.scene.paint.Color;

public enum BlindType {
    SMALL("Small Blind", Constant.BLUE, Constant.LIGHT_BLUE),
    BIG("Big Blind", Constant.ORANGE, Constant.LIGHT_ORANGE),
    BOSS("Boss Blind", Constant.PURPLE, Constant.LIGHT_PURPLE);

    private final String display;
    private final Color primaryColor;
    private final Color secondaryColor;

    private BlindType(String display, Color primaryColor, Color secondaryColor) {
        this.display = display;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public String getDisplay() {
        return this.display;
    }

    public Color getPrimaryColor() {
        return this.primaryColor;
    }

    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

}
