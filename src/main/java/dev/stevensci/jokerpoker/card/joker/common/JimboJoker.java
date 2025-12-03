package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;

public class JimboJoker extends JokerCard {

    public JimboJoker() {
        super(0, 0);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        blind.addHandMultiplier(4);
    }

}
