package dev.stevensci.jokerpoker.card;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.meta.CardEdition;

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

    public void triggerCardEdition(Blind blind) {
        switch (getEdition()) {
            case POLYCHROME -> blind.multiplyHandMultiplier(1.5f);
            case FOIL -> blind.addHandChips(50);
            case HOLOGRAPHIC -> blind.addHandMultiplier(10);
        }
    }

}
