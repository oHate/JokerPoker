package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class WilyJoker extends JokerCard {

    public WilyJoker() {
        super(JokerType.WILY_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isThreeOfAKind()) {
            game.addResultChips(100);
        }
    }

}