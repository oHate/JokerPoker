package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class CleverJoker extends JokerCard {

    public CleverJoker() {
        super(JokerType.CLEVER_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isTwoPair()) {
            game.addResultChips(80);
        }
    }

}