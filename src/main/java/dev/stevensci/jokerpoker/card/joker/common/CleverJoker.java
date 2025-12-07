package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class CleverJoker extends JokerCard {

    public CleverJoker() {
        super(JokerType.CLEVER);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isTwoPair()) {
            blind.addHandChips(80);
        }
    }

}