package dev.stevensci.jokerpoker.card.joker.common;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.blind.HandType;
import dev.stevensci.jokerpoker.card.joker.JokerCard;


public class CrazyJoker extends JokerCard {

    public CrazyJoker() {
        super(0, 0);
    }

    @Override
    public void onPostHandScore(Blind blind) {
        // Check to see if all selected cards are is a Handtype pair
        // Needs to add sub hand types for logic to work properly
        if (blind.getResult().getHandType() == HandType.STRAIGHT) {

            blind.addHandMultiplier(12);

        }
    }

}