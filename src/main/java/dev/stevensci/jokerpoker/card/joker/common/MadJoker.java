package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class MadJoker extends JokerCard {

    public MadJoker() {
        super(JokerType.MAD_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isTwoPair()) {
            game.addResultMultiplier(10);
        }
    }

}