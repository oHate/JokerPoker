package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class BannerJoker extends JokerCard {

    public BannerJoker() {
        super(JokerType.BANNER_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        for (int i = 0; i < game.getDiscards().get(); i++) {
            game.addResultChips(30);
        }
    }

}
