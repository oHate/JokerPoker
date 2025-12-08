package dev.stevensci.jokerpoker.card;

import dev.stevensci.jokerpoker.util.Constant;
import javafx.scene.Node;

public class PlayingCard extends Card implements Comparable<PlayingCard> {

    private CardRank rank;
    private CardSuit suit;

    public PlayingCard(CardRank rank, CardSuit suit) {
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

    @Override
    public int compareTo(PlayingCard o) {
        return Integer.compare(this.rank.ordinal(), o.getRank().ordinal());
    }

    @Override
    public Node createView() {
        return Constant.CARD_SPRITESHEET.getView(this.suit.ordinal(), this.rank.ordinal());
    }

}
