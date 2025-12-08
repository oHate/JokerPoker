package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class DeviousJoker extends JokerCard {

    public DeviousJoker() {
        super(JokerType.DEVIOUS_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isStraight()) {
            game.addResultChips(100);
        }
    }

}