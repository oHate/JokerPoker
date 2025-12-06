package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class MadJoker extends JokerCard {

    public MadJoker() {
        super(JokerType.MAD);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isTwoPair()) {
            blind.addHandMultiplier(10);
        }
    }

}