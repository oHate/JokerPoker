package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.meta.CardRank;
import dev.stevensci.jokerpoker.card.meta.CardSuit;

import java.util.ArrayList;
import java.util.List;

public class JokerPokerGame {

    private final List<PlayingCard> deck = new ArrayList<>(52);
    private final List<JokerCard> jokers = new ArrayList<>();

    private Blind blind;

    public JokerPokerGame() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                this.deck.add(new PlayingCard(rank, suit));
            }
        }

        this.blind = new Blind(this.deck, this.jokers);
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
