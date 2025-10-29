package jokerpoker.card;

public abstract class Card {

    private CardEdition edition;

    public Card(CardEdition edition) {
        this.edition = edition;
    }

    public CardEdition getEdition() {
        return this.edition;
    }

    public void setEdition(CardEdition edition) {
        this.edition = edition;
    }

}
