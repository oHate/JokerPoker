package dev.stevensci.jokerpoker.card.joker.uncommon;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.card.CardRank;
import dev.stevensci.jokerpoker.model.GameModel;

public class FibonacciJoker extends JokerCard {

    public FibonacciJoker() {
        super(JokerType.FIBONACCI_JOKER);
    }

    @Override
    public void onCardScore(GameModel game, PlayingCard card) {
        CardRank rank = card.getRank();

        if (rank == CardRank.ACE ||
                rank == CardRank.TWO ||
                rank == CardRank.THREE ||
                rank == CardRank.FIVE ||
                rank == CardRank.EIGHT) {
            game.addResultMultiplier(8);
        }
    }

}
