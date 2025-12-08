package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class CrazyJoker extends JokerCard {

    public CrazyJoker() {
        super(JokerType.CRAZY_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isStraight()) {
            game.addResultMultiplier(12);
        }
    }

}