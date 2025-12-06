package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class ZanyJoker extends JokerCard {

    public ZanyJoker() {
        super(JokerType.ZANY);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isThreeOfAKind()) {
            blind.addHandMultiplier(12);
        }
    }

}