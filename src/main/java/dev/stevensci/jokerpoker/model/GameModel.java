package dev.stevensci.jokerpoker.model;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.CardRank;
import dev.stevensci.jokerpoker.card.CardSuit;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.util.SortMode;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.*;

public class GameModel {

    public static final int REROLL_COST_STEP = 2;
    public static final int DEFAULT_REROLL_COST = 3;
    public static final int DEFAULT_HANDS = 4;
    public static final int DEFAULT_DISCARDS = 3;
    public static final int DEFAULT_HAND_SIZE = 8;
    public static final int DEFAULT_JOKER_SIZE = 5;

    private final List<PlayingCard> gameDeck = new ArrayList<>(52);

    private final Stack<PlayingCard> roundDeck = new Stack<>();
    private final List<PlayingCard> cardsInHand = new ArrayList<>();
    private final List<PlayingCard> selectedCards = new ArrayList<>();

    private final List<JokerCard> jokers = new ArrayList<>();

    private int handsAmount = DEFAULT_HANDS;
    private int discardsAmount = DEFAULT_DISCARDS;

    private IntegerProperty score = new SimpleIntegerProperty(-1);
    private IntegerProperty hands = new SimpleIntegerProperty(-1);
    private IntegerProperty discards = new SimpleIntegerProperty(-1);

    private IntegerProperty round = new SimpleIntegerProperty(1);
    private IntegerProperty ante = new SimpleIntegerProperty(1);
    private IntegerProperty cash = new SimpleIntegerProperty(0);
    private IntegerProperty rerollCost = new SimpleIntegerProperty(5);

    private SortMode sortMode = SortMode.NONE;
    private ObjectProperty<BlindType> blindType = new SimpleObjectProperty<>();

    private HandResult result;
    private IntegerProperty resultChips = new SimpleIntegerProperty(0);
    private IntegerProperty resultMultiplier = new SimpleIntegerProperty(0);

    public GameModel() {
        this.result = new HandResult(this.selectedCards);

        this.result.getHandTypeProperty().addListener(_ -> {
            HandType type = this.result.getHandTypeProperty().get();
            this.resultChips.set(type.getChips());
            this.resultMultiplier.set(type.getMultiplier());
        });

        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                this.gameDeck.add(new PlayingCard(rank, suit));
            }
        }
    }

    public void initialize() {
        this.score.set(0);
        this.hands.set(this.handsAmount);
        this.discards.set(this.discardsAmount);

        this.roundDeck.clear();
        this.selectedCards.clear();
        this.cardsInHand.clear();

        this.roundDeck.addAll(this.gameDeck);
        Collections.shuffle(this.roundDeck);

        this.blindType.set(BlindType.values()[(this.round.get() - 1) % 3]);
    }

    public List<PlayingCard> drawCards() {
        List<PlayingCard> cards = new ArrayList<>();

        int cardsNeeded = Math.min(this.roundDeck.size(), DEFAULT_HAND_SIZE - this.cardsInHand.size());

        for (int i = 0; i < cardsNeeded; i++) {
            PlayingCard card = this.roundDeck.pop();
            this.cardsInHand.add(card);
            cards.add(card);
        }

        sortCards();
        return cards;
    }

    public void selectCard(PlayingCard card) {
        this.selectedCards.add(card);
        this.selectedCards.sort(Comparator.comparingInt(this.cardsInHand::indexOf));
        this.result.updateHandType();
    }

    public void deselectCard(PlayingCard card) {
        this.selectedCards.remove(card);
        this.result.updateHandType();
    }

    public void startHand() {
        for (JokerCard joker : this.jokers) {
            joker.onPreHandScore(this);
        }
    }

    public void scoreCard(PlayingCard card) {
        this.resultChips.set(this.resultChips.get() + card.getRank().getChips());

        for (JokerCard joker : this.jokers) {
            joker.onCardScore(this, card);
        }
    }

    public void finishHand() {
        for (JokerCard joker : this.jokers) {
            joker.onPostHandScore(this);
        }

        this.score.set(this.score.get() + this.resultChips.get() * this.resultMultiplier.get());

        discard();
    }

    public void discard() {
        this.cardsInHand.removeAll(this.selectedCards);
        this.selectedCards.clear();
    }

    private void sortCards() {
        this.cardsInHand.sort(this.sortMode.getComparator());
    }

    public long getTargetScore() {
        return (long) (Constant.SCORE_ARRAY[this.ante.get()] * this.blindType.get().getMultiplier());
    }

    public Stack<PlayingCard> getRoundDeck() {
        return this.roundDeck;
    }

    public List<PlayingCard> getCardsInHand() {
        return this.cardsInHand;
    }

    public List<PlayingCard> getSelectedCards() {
        return this.selectedCards;
    }

    public IntegerProperty getScore() {
        return this.score;
    }

    public IntegerProperty getHands() {
        return this.hands;
    }

    public void decrementHands() {
        this.hands.set(this.hands.get() - 1);
    }

    public IntegerProperty getDiscards() {
        return this.discards;
    }

    public void decrementDiscards() {
        this.discards.set(this.discards.get() - 1);
    }

    public IntegerProperty getRound() {
        return this.round;
    }

    public void incrementRound() {
        this.round.set(this.round.get() + 1);
    }

    public IntegerProperty getAnte() {
        return this.ante;
    }

    public void incrementAnte() {
        this.ante.set(this.ante.get() + 1);
    }

    public IntegerProperty getCash() {
        return this.cash;
    }

    public void addCash(int amount) {
        this.cash.set(this.cash.get() + amount);
    }

    public void subtractCash(int amount) {
        this.cash.set(this.cash.get() - amount);
    }

    public IntegerProperty getRerollCost() {
        return this.rerollCost;
    }

    public void increaseRerollCost() {
        this.rerollCost.set(this.rerollCost.get() + REROLL_COST_STEP);
    }

    public List<JokerCard> getJokers() {
        return this.jokers;
    }

    public SortMode getSortMode() {
        return this.sortMode;
    }

    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
        sortCards();
    }

    public ObjectProperty<BlindType> getBlindType() {
        return this.blindType;
    }

    public HandResult getResult() {
        return this.result;
    }

    public IntegerProperty getResultChips() {
        return this.resultChips;
    }

    public void addResultChips(int amount) {
        this.resultChips.set(this.resultChips.get() + amount);
    }

    public IntegerProperty getResultMultiplier() {
        return this.resultMultiplier;
    }

    public void addResultMultiplier(int amount) {
        this.resultMultiplier.set(this.resultMultiplier.get() + amount);
    }

    public void multiplyResultMultiplier(float amount) {
        this.resultMultiplier.set((int) (this.resultMultiplier.get() * amount));
    }

}
