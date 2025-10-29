package jokerpoker.card;

public enum HandType {
    FLUSH_FIVE(160, 16),
    FLUSH_HOUSE(140, 14),
    FIVE_OF_A_KIND(120, 12),
    ROYAL_FLUSH(100, 8),
    STRAIGHT_FLUSH(100, 8),
    FOUR_OF_A_KIND(60, 7),
    FULL_HOUSE(40, 4),
    FLUSH(35, 4),
    STRAIGHT(30, 4),
    THREE_OF_A_KIND(30, 3),
    TWO_PAIR(20, 2),
    PAIR(10, 2),
    HIGH_CARD(5, 1),
    NONE(0, 0);

    private final int chips;
    private final int multiplier;

    private HandType(int chips, int multiplier) {
        this.chips = chips;
        this.multiplier = multiplier;
    }

    public int getChips() {
        return this.chips;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

}
