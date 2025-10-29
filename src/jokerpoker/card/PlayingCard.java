package jokerpoker.card;

public class PlayingCard extends Card implements Comparable<PlayingCard> {

    private CardRank rank;
    private CardSuit suit;

    private CardEnhancement enhancement = CardEnhancement.NONE;
    private CardSeal seal = CardSeal.NONE;

    public PlayingCard(CardSuit suit, CardRank rank) {
        super(CardEdition.BASE);

        this.suit = suit;
        this.rank = rank;
    }

    private CardRank getRank() {
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

    // Low to High (ACE, TWO, THREE, etc...)
    @Override
    public int compareTo(PlayingCard o) {
        return Integer.compare(this.rank.ordinal(), o.getRank().ordinal());
    }

}
