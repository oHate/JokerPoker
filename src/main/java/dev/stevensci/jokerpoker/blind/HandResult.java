package dev.stevensci.jokerpoker.blind;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.meta.CardRank;
import dev.stevensci.jokerpoker.card.meta.CardSuit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HandResult {

    private final List<PlayingCard> hand;
    private final List<PlayingCard> sortedHand;
    private final ObjectProperty<HandType> handTypeProperty;

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

    public void updateHandType() {
        this.sortedHand.clear();
        this.sortedHand.addAll(this.hand);
        this.sortedHand.sort(Comparator.comparing(PlayingCard::getRank));

        this.handTypeProperty.set(computeHandType());
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

    private boolean isRoyalStraight() {
        return sortedHand.get(0).getRank() == CardRank.TEN &&
                sortedHand.get(1).getRank() == CardRank.JACK &&
                sortedHand.get(2).getRank() == CardRank.QUEEN &&
                sortedHand.get(3).getRank() == CardRank.KING &&
                sortedHand.get(4).getRank() == CardRank.ACE;
    }

    private boolean isFiveOfAKind() {
        CardRank rank = this.sortedHand.getFirst().getRank();

        for (int i = 1; i < this.sortedHand.size(); i++) {
            if (sortedHand.get(i).getRank() != rank) {
                return false;
            }
        }

        return true;
    }

    private boolean isStraight() {
        for (int i = 1; i < sortedHand.size(); i++) {
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
        return sortedHand.get(0).getRank() == sortedHand.get(3).getRank() || (sortedHand.size() == 5 && sortedHand.get(1).getRank() == sortedHand.get(4).getRank());
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
        for (int i = 0; i <= sortedHand.size() - 3; i++) {
            CardRank firstRank = sortedHand.get(i).getRank();
            CardRank secondRank = sortedHand.get(i + 1).getRank();
            CardRank thirdRank = sortedHand.get(i + 2).getRank();

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
