package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class DeviousJoker extends JokerCard {

    public DeviousJoker() {
        super(JokerType.DEVIOUS);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isStraight()) {
            blind.addHandChips(100);
        }
    }

}