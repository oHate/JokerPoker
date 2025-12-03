package dev.stevensci.jokerpoker.card.joker.common;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.meta.CardSuit;

public class WrathfulJoker extends JokerCard {

    public WrathfulJoker() {
        super(0, 0);
    }

    @Override
    public void onCardScore(Blind blind, PlayingCard card) {
        if (card.getSuit() == CardSuit.SPADE) {

            blind.addHandMultiplier(3);

        }
    }

}