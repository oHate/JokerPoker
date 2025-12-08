package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class AbstractJoker extends JokerCard {

    public AbstractJoker() {
        super(JokerType.ABSTRACT_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        game.addResultMultiplier(game.getJokers().size() * 3);
    }

}
