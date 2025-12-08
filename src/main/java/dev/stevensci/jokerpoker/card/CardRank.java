package dev.stevensci.jokerpoker.card;

public enum CardRank {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10),
    ACE("A", 11);

    private final String display;
    private final int chips;

    private CardRank(String display, int chips) {
        this.display = display;
        this.chips = chips;
    }

    public String getDisplay() {
        return display;
    }

    public int getChips() {
        return this.chips;
    }

}
