package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class ZanyJoker extends JokerCard {

    public ZanyJoker() {
        super(JokerType.ZANY_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        if (game.getResult().getHandType().isThreeOfAKind()) {
            game.addResultMultiplier(12);
        }
    }

}