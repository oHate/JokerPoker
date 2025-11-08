package dev.stevensci.jokerpoker.card.joker.common;
import dev.stevensci.jokerpoker.blind.Blind;
import dev.stevensci.jokerpoker.card.joker.JokerCard;

public class JimboJoker extends JokerCard {

    @Override
    public void onPostHandScore(Blind blind) {
        blind.addHandMultiplier(4);
    }

}
