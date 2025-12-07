package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.util.Constant;
import javafx.scene.paint.Color;

public enum JokerRarity {
    COMMON("Common", Constant.BLUE),
    UNCOMMON("Uncommon", Constant.GREEN),
    RARE("Rare", Constant.RED);

    private final String display;
    private final Color color;

    private JokerRarity(String display, Color color) {
        this.display = display;
        this.color = color;
    }

    public String getDisplay() {
        return this.display;
    }

    public Color getColor() {
        return this.color;
    }

}
