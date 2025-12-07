package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class CraftyJoker extends JokerCard {

    public CraftyJoker() {
        super(JokerType.CRAFTY);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isFlush()) {
            blind.addHandChips(80);
        }
    }

}