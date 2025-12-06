package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;

public class WilyJoker extends JokerCard {

    public WilyJoker() {
        super(JokerType.WILY);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        if (blind.getResult().getHandType().isThreeOfAKind()) {
            blind.addHandChips(100);
        }
    }

}