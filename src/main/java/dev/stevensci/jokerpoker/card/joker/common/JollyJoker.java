package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class JollyJoker extends JokerCard {

    public JollyJoker() {
        super(JokerType.JOLLY);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isPair()) {
            blind.addHandMultiplier(8);
        }
    }

}