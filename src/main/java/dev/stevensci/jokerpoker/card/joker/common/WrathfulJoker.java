package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.card.CardSuit;
import dev.stevensci.jokerpoker.model.GameModel;

public class WrathfulJoker extends JokerCard {

    public WrathfulJoker() {
        super(JokerType.WRATHFUL_JOKER);
    }

    @Override
    public void onCardScore(GameModel game, PlayingCard card) {
        if (card.getSuit() == CardSuit.SPADE) {
            game.addResultMultiplier(3);
        }
    }

}