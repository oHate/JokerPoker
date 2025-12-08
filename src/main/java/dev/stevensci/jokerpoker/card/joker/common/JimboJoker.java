package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class JimboJoker extends JokerCard {

    public JimboJoker() {
        super(JokerType.JIMBO_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        game.addResultMultiplier(4);
    }

}
