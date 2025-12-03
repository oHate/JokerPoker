package dev.stevensci.jokerpoker.model;

import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.blind.BlindType;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.meta.CardRank;
import dev.stevensci.jokerpoker.card.meta.CardSuit;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    public static final int DEFAULT_HANDS = 4;
    public static final int DEFAULT_DISCARDS = 3;
    public static final int DEFAULT_HAND_SIZE = 8;
    public static final int DEFAULT_JOKER_SIZE = 5;
    public static final int DEFAULT_CONSUMABLE_SIZE = 2;

    private final List<PlayingCard> deck = new ArrayList<>(52);
    private final List<JokerCard> jokers = new ArrayList<>();

    private int hands = DEFAULT_HANDS;
    private int discards = DEFAULT_DISCARDS;
    private int baseHandSize = DEFAULT_HAND_SIZE;
    private IntegerProperty round = new SimpleIntegerProperty(1);
    private IntegerProperty ante = new SimpleIntegerProperty(1);

    private Blind blind;

    public GameModel() {
        initializeDeck();
        updateBlind();
    }

    public void updateBlind() {
        this.blind = new Blind(
                getBlindType(),
                this.deck,
                this.jokers,
                getTargetScore(),
                this.hands,
                this.discards,
                this.baseHandSize
        );
    }

    private void initializeDeck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                this.deck.add(new PlayingCard(rank, suit));
            }
        }
    }

    public long getTargetScore() {
        return (long) (Constant.SCORE_ARRAY[this.ante.get()] * getBlindType().getMultiplier());
    }

    public BlindType getBlindType() {
        return BlindType.values()[(this.round.get() - 1) % 3];
    }

    public IntegerProperty getRound() {
        return this.round;
    }

    public IntegerProperty getAnte() {
        return this.ante;
    }

    public List<PlayingCard> getDeck() {
        return this.deck;
    }

    public List<JokerCard> getJokers() {
        return this.jokers;
    }

    public Blind getBlind() {
        return this.blind;
    }

}
