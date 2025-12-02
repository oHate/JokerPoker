package dev.stevensci.jokerpoker.card;

import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.card.meta.*;
import javafx.scene.Node;

public class PlayingCard extends Card implements Comparable<PlayingCard> {

    private Node node;

    private CardRank rank;
    private CardSuit suit;

    private CardEnhancement enhancement = CardEnhancement.NONE;
    private CardSeal seal = CardSeal.NONE;

    public PlayingCard(CardRank rank, CardSuit suit) {
        super(CardEdition.BASE);

        this.rank = rank;
        this.suit = suit;
    }

    public CardRank getRank() {
        return this.rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return this.suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public CardEnhancement getEnhancement() {
        return this.enhancement;
    }

    public void setEnhancement(CardEnhancement enhancement) {
        this.enhancement = enhancement;
    }

    public CardSeal getSeal() {
        return this.seal;
    }

    public void setSeal(CardSeal seal) {
        this.seal = seal;
    }

    @Override
    public int compareTo(PlayingCard o) {
        return Integer.compare(this.rank.ordinal(), o.getRank().ordinal());
    }

    @Override
    public Node createView() {
        return Constant.CARD_SPRITESHEET.getView(this.rank.ordinal(), this.suit.ordinal());
    }

}
