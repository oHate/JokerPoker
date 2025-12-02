package dev.stevensci.jokerpoker.util;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.meta.CardRank;

import java.util.Comparator;

public enum SortMode {
    NONE(Comparator.comparing(_ -> 0)),
    RANK((a, b) -> {
        if (a == null || b == null) return 0;
        int ranks = CardRank.values().length;
        return Integer.compare(ranks - a.getRank().ordinal(), ranks - b.getRank().ordinal());
    }),
    SUIT((a, b) -> {
        if (a == null || b == null) return 0;
        int suitCompare = Integer.compare(a.getSuit().ordinal(), b.getSuit().ordinal());
        if (suitCompare != 0) return suitCompare;
        return RANK.getComparator().compare(a, b);
    });

    private final Comparator<PlayingCard> comparator;

    private SortMode(Comparator<PlayingCard> comparator) {
        this.comparator = comparator;
    }

    public Comparator<PlayingCard> getComparator() {
        return this.comparator;
    }

}
