package dev.stevensci.jokerpoker.card.joker.common;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerType;
import dev.stevensci.jokerpoker.card.CardRank;
import dev.stevensci.jokerpoker.model.GameModel;

public class ScaryFaceJoker extends JokerCard {

    public ScaryFaceJoker() {
        super(JokerType.SCARY_FACE_JOKER);
    }

    @Override
    public void onCardScore(GameModel game, PlayingCard card) {
        CardRank rank = card.getRank();

        if (rank == CardRank.KING ||
                rank == CardRank.QUEEN ||
                rank == CardRank.JACK) {
            game.addResultChips(30);
        }
    }

}
