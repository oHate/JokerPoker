package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class SlyJoker extends JokerCard {

    public SlyJoker() {
        super(JokerType.SLY);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isPair()) {
            blind.addHandChips(50);
        }
    }

}