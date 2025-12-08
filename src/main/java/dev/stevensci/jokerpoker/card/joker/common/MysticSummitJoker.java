package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class MysticSummitJoker extends JokerCard {

    public MysticSummitJoker() {
        super(JokerType.MYSTIC_SUMMIT_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getDiscards().get() <= 0) {
            game.addResultMultiplier(15);
        }
    }

}
