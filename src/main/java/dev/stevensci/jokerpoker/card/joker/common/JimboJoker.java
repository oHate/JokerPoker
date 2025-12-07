package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class JimboJoker extends JokerCard {

    public JimboJoker() {
        super(JokerType.JIMBO);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        blind.addHandMultiplier(4);
    }

}
