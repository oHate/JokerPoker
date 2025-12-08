package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class MisprintJoker extends JokerCard {

    public MisprintJoker() {
        super(JokerType.MISPRINT_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        game.addResultMultiplier((int) (Math.random() * 24));
    }

}
