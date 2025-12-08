package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.card.Card;
import dev.stevensci.jokerpoker.card.PlayingCard;
import javafx.scene.Node;

public abstract class JokerCard extends Card {

    private final JokerType type;

    public JokerCard(JokerType type) {
        this.type = type;
    }

    public void onPreHandScore(GameModel game) {

    }

    public void onCardScore(GameModel game, PlayingCard card) {

    }

    public void onPostHandScore(GameModel game) {

    }

    @Override
    public Node createView() {
        return Constant.JOKERS_SPRITESHEET.getView(this.type.getSpriteRow(), this.type.getSpriteColumn());
    }

    public JokerType getType() {
        return this.type;
    }

}
