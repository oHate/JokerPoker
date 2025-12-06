package dev.stevensci.jokerpoker.controller;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.common.*;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ShopController {

    private static final List<Supplier<JokerCard>> JOKER_CARDS = List.of(
            JimboJoker::new, GreedyJoker::new, LustyJoker::new, WrathfulJoker::new, GluttonousJoker::new,
            JollyJoker::new, ZanyJoker::new, MadJoker::new, CrazyJoker::new, DrollJoker::new,
            SlyJoker::new, WilyJoker::new, CleverJoker::new, DeviousJoker::new, CraftyJoker::new
    );

    private final Random random = new Random();

    public JokerCard getRandomJoker() {
        return JOKER_CARDS.get(this.random.nextInt(JOKER_CARDS.size())).get();
    }

}
