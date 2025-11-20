package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.meta.CardRank;
import dev.stevensci.jokerpoker.card.meta.CardSuit;
import dev.stevensci.jokerpoker.view.GameView;

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
    private int round = 1;
    private int ante = 1;

    private Blind blind;

    public GameModel() {
        initializeDeck();

        this.blind = createBlind();
    }

    private Blind createBlind() {
        return new Blind(
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
        return Constant.SCORE_ARRAY[this.round];
    }

    public BlindType getBlindType() {
        return BlindType.values()[(this.round - 1) % 3];
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
