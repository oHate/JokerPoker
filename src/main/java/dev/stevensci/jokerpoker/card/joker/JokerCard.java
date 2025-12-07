package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.Card;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.meta.CardEdition;
import javafx.scene.Node;

public abstract class JokerCard extends Card {

    private final JokerType type;

    public JokerCard(JokerType type, CardEdition edition) {
        super(edition);
        this.type = type;
    }

    public JokerCard(JokerType type) {
        this(type, CardEdition.BASE);
    }

    public void onPreHandScore(Blind blind) {

    }

    public void onCardScore(Blind blind, PlayingCard card) {

    }

    public void onPostHandScore(Blind blind) {

    }

    @Override
    public Node createView() {
        return Constant.JOKERS_SPRITESHEET.getView(this.type.getSpriteRow(), this.type.getSpriteColumn());
    }

    public JokerType getType() {
        return this.type;
    }

}
