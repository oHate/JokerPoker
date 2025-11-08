package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.Card;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.meta.CardEdition;

public abstract class JokerCard extends Card {

    public JokerCard(CardEdition edition) {
        super(edition);
    }

    public JokerCard() {
        super(CardEdition.BASE);
    }

    public void onPreHandScore(Blind blind) {

    }

    public void onCardScore(Blind blind, PlayingCard card) {

    }

    public void onHandEffect(Blind blind, PlayingCard card) {

    }

    public void onPostHandScore(Blind blind) {

    }

}
