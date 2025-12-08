package dev.stevensci.jokerpoker.card.joker.uncommon;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;

public class StencilJoker extends JokerCard {

    public StencilJoker() {
        super(JokerType.STENCIL_JOKER);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        int multiplier = GameModel.DEFAULT_JOKER_SIZE - game.getJokers().size();

        for (JokerCard card : game.getJokers()) {
            if (card.getType() == JokerType.STENCIL_JOKER) {
                multiplier++;
            }
        }

        game.multiplyResultMultiplier(multiplier);
    }

}
