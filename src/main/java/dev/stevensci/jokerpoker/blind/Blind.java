package dev.stevensci.jokerpoker.blind;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;

import java.util.*;

public class Blind {

    private static final long[] SCORE_TABLE = {
            100,
            300,
            800,
            2_000,
            5_000,
            11_000,
            20_000,
            35_000,
            50_000,
            110_000,
            560_000,
            7_200_000,
            300_000_000,
            47_000_000_000L,
            (long) 2.9e13,
            (long) 7.7e16,
            (long) 8.6e20,
            (long) 4.2e25,
            (long) 9.2e30,
            (long) 9.2e36,
            (long) 4.3e43,
            (long) 9.7e50,
            (long) 1.0e59,
            (long) 5.8e67,
            (long) 1.6e77,
            (long) 2.4e87,
            (long) 1.9e98,
            (long) 8.4e109,
            (long) 2.0e122,
            (long) 2.7e135,
            (long) 2.1e149 // Ante 30
    };

    private final Stack<PlayingCard> deck;
    private final List<JokerCard> jokers;
    private List<PlayingCard> hand = new ArrayList<>();
    private List<PlayingCard> selectedCards;
    private final HandResult result;

    private int roundScore;
    private int hands = 4;
    private int discards = 3;
    private int handSize = 8;

    private int handChips = 0;
    private int handMultiplier = 0;

    public Blind(List<PlayingCard> deck, List<JokerCard> jokers) {
        this.deck = new Stack<>();
        this.deck.addAll(deck);

        Collections.shuffle(this.deck);

        this.jokers = jokers;

        this.selectedCards = new ArrayList<>();
        this.result = new HandResult(this.selectedCards);

        for (int i = 0; i < Math.min(deck.size(), 8); i++) {
            PlayingCard card = this.deck.pop();
            hand.add(card);
        }
    }

    public void selectCard(PlayingCard card) {
        this.selectedCards.add(card);
        this.selectedCards.sort(Comparator.comparingInt(this.hand::indexOf));
        this.result.updateHandType();
    }

    public void deselectCard(PlayingCard card) {
        this.selectedCards.remove(card);
        this.result.updateHandType();
    }

    public void processCurrentHand() {
        HandType type = this.result.getHandType();

        this.handChips = type.getChips();
        this.handMultiplier = type.getMultiplier();

        for (PlayingCard card : this.selectedCards) {
            this.handChips += card.getRank().getChips();
            card.triggerCardEdition(this);
        }

        this.roundScore += this.handChips * this.handMultiplier;
    }

    public Stack<PlayingCard> getDeck() {
        return this.deck;
    }

    public List<JokerCard> getJokers() {
        return this.jokers;
    }

    public List<PlayingCard> getHand() {
        return this.hand;
    }

    public List<PlayingCard> getSelectedCards() {
        return this.selectedCards;
    }

    public HandResult getResult() {
        return this.result;
    }

    public int getRoundScore() {
        return this.roundScore;
    }

    public int getHands() {
        return this.hands;
    }

    public int getDiscards() {
        return this.discards;
    }

    public int getHandSize() {
        return this.handSize;
    }

    public int getHandMultiplier() {
        return this.handMultiplier;
    }

    public void setHandMultiplier(int amount) {
        this.handMultiplier = amount;
    }

    public void addHandMultiplier(int amount) {
        this.handMultiplier += amount;
    }

    public void multiplyHandMultiplier(float amount) {
        this.handMultiplier = (int) (this.handMultiplier * amount);
    }

    public int getHandChips() {
        return this.handChips;
    }

    public void setHandChips(int amount) {
        this.handChips = amount;
    }

    public void addHandChips(int amount) {
        this.handChips += amount;
    }

    public void multiplyHandChips(float amount) {
        this.handChips = (int) (this.handChips * amount);
    }

}
