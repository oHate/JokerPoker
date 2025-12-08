package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class JollyJoker extends JokerCard {

    public JollyJoker() {
        super(JokerType.JOLLY_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isPair()) {
            game.addResultMultiplier(8);
        }
    }

}