package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class DrollJoker extends JokerCard {

    public DrollJoker() {
        super(JokerType.DROLL_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isFlush()) {
            game.addResultMultiplier(10);
        }
    }

}