package dev.stevensci.jokerpoker.blind;

public enum HandType {
    FLUSH_FIVE("Flush Five", 160, 16),
    FLUSH_HOUSE("Flush House", 140, 14),
    FIVE_OF_A_KIND("Five of a Kind", 120, 12),
    ROYAL_FLUSH("Royal Flush", 100, 8),
    STRAIGHT_FLUSH("Straight Flush", 100, 8),
    FOUR_OF_A_KIND("Four of a Kind", 60, 7),
    FULL_HOUSE("Full House", 40, 4),
    FLUSH("Flush", 35, 4),
    STRAIGHT("Straight", 30, 4),
    THREE_OF_A_KIND("Three of a Kind", 30, 3),
    TWO_PAIR("Two Pair", 20, 2),
    PAIR("Pair", 10, 2),
    HIGH_CARD("High Card", 5, 1),
    NONE("None", 0, 0);

    private final String display;
    private final int chips;
    private final int multiplier;

    private HandType(String display, int chips, int multiplier) {
        this.display = display;
        this.chips = chips;
        this.multiplier = multiplier;
    }

    public String getDisplay() {
        return this.display;
    }

    public int getChips() {
        return this.chips;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

}
