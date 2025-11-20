package dev.stevensci.jokerpoker.blind;

import dev.stevensci.jokerpoker.BlindType;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;

import java.util.*;

public class Blind {

    private final Stack<PlayingCard> deck;
    private final List<JokerCard> jokers;
    private List<PlayingCard> hand = new ArrayList<>();
    private List<PlayingCard> selectedCards;
    private final HandResult result;

    private long targetScore;

    private int roundScore;
    private int hands = 4;
    private int discards = 3;
    private int handSize = 8;

    private int handChips = 0;
    private int handMultiplier = 0;

    public Blind(BlindType type, List<PlayingCard> deck, List<JokerCard> jokers, long targetScore, int hands, int discards, int handSize) {
        this.targetScore = targetScore;
        this.hands = hands;
        this.discards = discards;
        this.handSize = handSize;

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
