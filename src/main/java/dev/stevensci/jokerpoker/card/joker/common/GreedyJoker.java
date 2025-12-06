package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.card.meta.CardSuit;

public class GreedyJoker extends JokerCard {

    public GreedyJoker() {
        super(JokerType.GREEDY);
    }

    @Override
    public void onCardScore(Blind blind, PlayingCard card) {
        if (card.getSuit() == CardSuit.DIAMOND) {
            blind.addHandMultiplier(3);
        }
    }

}