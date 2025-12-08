package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class HalfJoker extends JokerCard {

    public HalfJoker() {
        super(JokerType.HALF_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getSelectedCards().size() <= 3) {
            game.addResultMultiplier(20);
        }
    }

}
