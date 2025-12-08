package dev.stevensci.jokerpoker.model;

import dev.stevensci.jokerpoker.util.Constant;
import javafx.scene.paint.Color;

public enum BlindType {
    SMALL("Small Blind", 1.0f, Constant.BLUE, Constant.LIGHT_BLUE),
    BIG("Big Blind", 1.5f, Constant.ORANGE, Constant.LIGHT_ORANGE),
    BOSS("Boss Blind", 2.0f, Constant.PURPLE, Constant.LIGHT_PURPLE);

    private final String display;
    private final float multiplier;
    private final Color primaryColor;
    private final Color secondaryColor;

    private BlindType(String display, float multiplier, Color primaryColor, Color secondaryColor) {
        this.display = display;
        this.multiplier = multiplier;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public String getDisplay() {
        return this.display;
    }

    public float getMultiplier() {
        return this.multiplier;
    }

    public Color getPrimaryColor() {
        return this.primaryColor;
    }

    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

}
