package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class CrazyJoker extends JokerCard {

    public CrazyJoker() {
        super(JokerType.CRAZY);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isStraight()) {
            blind.addHandMultiplier(12);
        }
    }

}