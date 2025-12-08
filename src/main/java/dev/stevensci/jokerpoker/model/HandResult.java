package dev.stevensci.jokerpoker.model;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.CardRank;
import dev.stevensci.jokerpoker.card.CardSuit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HandResult {

    private final List<PlayingCard> hand;
    private final List<PlayingCard> sortedHand;
    private final ObjectProperty<HandType> handTypeProperty;
    private final Set<Integer> skippedIndexes = new HashSet<>();

    public HandResult(List<PlayingCard> hand) {
        this.hand = hand;
        this.sortedHand = new ArrayList<>();
        this.handTypeProperty = new SimpleObjectProperty<>(HandType.NONE);
        updateHandType();
    }

    public ObjectProperty<HandType> getHandTypeProperty() {
        return this.handTypeProperty;
    }

    public HandType getHandType() {
        return this.handTypeProperty.get();
    }

    public Set<Integer> getSkippedIndexes() {
        return Set.copyOf(this.skippedIndexes);
    }

    public void updateHandType() {
        this.sortedHand.clear();
        this.sortedHand.addAll(this.hand);
        this.sortedHand.sort(Comparator.comparing(PlayingCard::getRank));

        HandType type = computeHandType();
        this.handTypeProperty.set(type);

        updateSkippedIndexesFor(type);
    }

    private HandType computeHandType() {
        int size = this.hand.size();

        if (size == 0) {
            return HandType.NONE;
        }

        if (size == 5) {
            boolean flush = isFlush();

            if (isFiveOfAKind()) {
                return flush ? HandType.FLUSH_FIVE : HandType.FIVE_OF_A_KIND;
            }

            boolean fullHouse = isFullHouse();

            if (flush && fullHouse) {
                return HandType.FLUSH_HOUSE;
            }

            if (flush && isRoyalStraight()) {
                return HandType.ROYAL_FLUSH;
            }

            boolean straight = isStraight();

            if (straight && flush) {
                return HandType.STRAIGHT_FLUSH;
            }

            if (fullHouse) {
                return HandType.FULL_HOUSE;
            }

            if (flush) {
                return HandType.FLUSH;
            }

            if (straight) {
                return HandType.STRAIGHT;
            }
        }

        if (size >= 4) {
            if (isFourOfAKind()) {
                return HandType.FOUR_OF_A_KIND;
            }

            if (isTwoPair()) {
                return HandType.TWO_PAIR;
            }
        }

        if (size >= 3) {
            if (isThreeOfAKind()) {
                return HandType.THREE_OF_A_KIND;
            }
        }

        if (size >= 2) {
            if (isPair()) {
                return HandType.PAIR;
            }
        }

        return HandType.HIGH_CARD;
    }

    private void updateSkippedIndexesFor(HandType type) {
        this.skippedIndexes.clear();

        if (this.hand.isEmpty()) {
            return;
        }

        switch (type) {
            case FLUSH_FIVE, FLUSH_HOUSE, FIVE_OF_A_KIND, ROYAL_FLUSH, STRAIGHT_FLUSH, FULL_HOUSE, FLUSH, STRAIGHT, NONE -> {
                return;
            }
            default -> {}
        }

        Map<CardRank, List<Integer>> groups = groupSortedIndexesByRank();
        Set<Integer> usedSortedIndexes = new HashSet<>();

        switch (type) {
            case FOUR_OF_A_KIND -> usedSortedIndexes.addAll(groups.get(findRankWithCount(groups, 4)));
            case THREE_OF_A_KIND -> usedSortedIndexes.addAll(groups.get(findRankWithCount(groups, 3)));
            case TWO_PAIR -> {
                for (CardRank rank : findRanksWithCount(groups, 2)) {
                    usedSortedIndexes.addAll(groups.get(rank));
                }
            }
            case PAIR -> usedSortedIndexes.addAll(groups.get(findRanksWithCount(groups, 2).getFirst()));
            case HIGH_CARD -> usedSortedIndexes.add(this.sortedHand.size() - 1);
            default -> {
                for (int i = 0; i < this.sortedHand.size(); i++) {
                    usedSortedIndexes.add(i);
                }
            }
        }

        Set<PlayingCard> usedCards = new HashSet<>();
        for (int index : usedSortedIndexes) {
            if (index >= 0 && index < this.sortedHand.size()) {
                usedCards.add(this.sortedHand.get(index));
            }
        }

        for (int i = 0; i < this.hand.size(); i++) {
            if (!usedCards.contains(this.hand.get(i))) {
                this.skippedIndexes.add(i);
            }
        }
    }

    private Map<CardRank, List<Integer>> groupSortedIndexesByRank() {
        Map<CardRank, List<Integer>> groups = new EnumMap<>(CardRank.class);

        for (int i = 0; i < this.sortedHand.size(); i++) {
            CardRank rank = this.sortedHand.get(i).getRank();
            groups.computeIfAbsent(rank, _ -> new ArrayList<>()).add(i);
        }

        return groups;
    }

    private CardRank findRankWithCount(Map<CardRank, List<Integer>> groups, int count) {
        for (Map.Entry<CardRank, List<Integer>> entry : groups.entrySet()) {
            if (entry.getValue().size() == count) {
                return entry.getKey();
            }
        }
        return null;
    }

    private List<CardRank> findRanksWithCount(Map<CardRank, List<Integer>> groups, int count) {
        List<CardRank> result = new ArrayList<>();
        for (Map.Entry<CardRank, List<Integer>> entry : groups.entrySet()) {
            if (entry.getValue().size() == count) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private boolean isRoyalStraight() {
        return this.sortedHand.get(0).getRank() == CardRank.TEN &&
                this.sortedHand.get(1).getRank() == CardRank.JACK &&
                this.sortedHand.get(2).getRank() == CardRank.QUEEN &&
                this.sortedHand.get(3).getRank() == CardRank.KING &&
                this.sortedHand.get(4).getRank() == CardRank.ACE;
    }

    private boolean isFiveOfAKind() {
        CardRank rank = this.sortedHand.getFirst().getRank();

        for (int i = 1; i < this.sortedHand.size(); i++) {
            if (this.sortedHand.get(i).getRank() != rank) {
                return false;
            }
        }

        return true;
    }

    private boolean isStraight() {
        for (int i = 1; i < this.sortedHand.size(); i++) {
            PlayingCard card = this.sortedHand.get(i);
            CardRank currentRank = card.getRank();

            PlayingCard previousCard = this.sortedHand.get(i - 1);
            CardRank previousRank = previousCard.getRank();

            if (previousRank == CardRank.FIVE && currentRank == CardRank.ACE) {
                continue;
            }

            int previousOrdinal = previousRank.ordinal();

            if (previousOrdinal != currentRank.ordinal() - 1) {
                return false;
            }
        }

        return true;
    }

    private boolean isFourOfAKind() {
        return this.sortedHand.get(0).getRank() == this.sortedHand.get(3).getRank()
                || (this.sortedHand.size() == 5 && this.sortedHand.get(1).getRank() == this.sortedHand.get(4).getRank());
    }

    private boolean isFullHouse() {
        CardRank firstRank = this.sortedHand.get(0).getRank();
        CardRank secondRank = this.sortedHand.get(1).getRank();
        CardRank thirdRank = this.sortedHand.get(2).getRank();
        CardRank fourthRank = this.sortedHand.get(3).getRank();
        CardRank fifthRank = this.sortedHand.get(4).getRank();

        return (firstRank == secondRank && secondRank == thirdRank && fourthRank == fifthRank) ||
                (firstRank == secondRank && thirdRank == fourthRank && fourthRank == fifthRank);
    }

    private boolean isFlush() {
        CardSuit suit = this.sortedHand.getFirst().getSuit();

        for (int i = 1; i < this.sortedHand.size(); i++) {
            if (this.sortedHand.get(i).getSuit() != suit) {
                return false;
            }
        }

        return true;
    }

    private boolean isThreeOfAKind() {
        for (int i = 0; i <= this.sortedHand.size() - 3; i++) {
            CardRank firstRank = this.sortedHand.get(i).getRank();
            CardRank secondRank = this.sortedHand.get(i + 1).getRank();
            CardRank thirdRank = this.sortedHand.get(i + 2).getRank();

            if (firstRank == secondRank && secondRank == thirdRank) {
                return true;
            }
        }

        return false;
    }

    private int countPairs() {
        int pairsFound = 0;

        for (int i = 1; i < this.sortedHand.size(); i++) {
            if (this.sortedHand.get(i - 1).getRank() == this.sortedHand.get(i).getRank()) {
                pairsFound++;
                i++;
            }
        }

        return pairsFound;
    }

    private boolean isTwoPair() {
        return countPairs() == 2;
    }

    private boolean isPair() {
        return countPairs() == 1;
    }

}
