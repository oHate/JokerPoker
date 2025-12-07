package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class DrollJoker extends JokerCard {

    public DrollJoker() {
        super(JokerType.DROLL);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isFlush()) {
            blind.addHandMultiplier(10);
        }
    }

}