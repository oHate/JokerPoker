package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.util.SortMode;

import java.util.ArrayList;
import java.util.List;

public class RaisedFistJoker extends JokerCard {

    public RaisedFistJoker() {
        super(JokerType.RAISED_FIST);
    }

    @Override
    public void onPostHandScore(GameModel game) {
        List<PlayingCard> cards = new ArrayList<>(game.getCardsInHand()); // TODO -> filter out selected cards first
        cards.sort(SortMode.RANK.getComparator());
        game.addResultMultiplier(cards.getLast().getRank().getChips() * 2);
    }

}
