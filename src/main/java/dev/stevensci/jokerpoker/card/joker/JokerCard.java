package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.Constant;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.Card;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.meta.CardEdition;
import javafx.scene.Node;

public abstract class JokerCard extends Card {

    private final int spriteRow;
    private final int spriteCol;

    public JokerCard(int spriteRow, int spriteCol, CardEdition edition) {
        super(edition);
        this.spriteRow = spriteRow;
        this.spriteCol = spriteCol;
    }

    public JokerCard(int spriteRow, int spriteCol) {
        this(spriteRow, spriteCol, CardEdition.BASE);
    }

    public void onPreHandScore(Blind blind) {

    }

    public void onCardScore(Blind blind, PlayingCard card) {

    }

    public void onHandEffect(Blind blind, PlayingCard card) {

    }

    public void onPostHandScore(Blind blind) {

    }

    @Override
    public Node createView() {
        return Constant.JOKERS_SPRITESHEET.getView(this.spriteRow, this.spriteCol);
    }

}
