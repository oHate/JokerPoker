package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.card.joker.common.*;
import dev.stevensci.jokerpoker.card.joker.uncommon.FibonacciJoker;
import dev.stevensci.jokerpoker.card.joker.uncommon.StencilJoker;

import java.util.function.Supplier;

public enum JokerType {
    JIMBO_JOKER("Jimbo Joker", "+4 Mult", JokerRarity.COMMON, 2, JimboJoker::new),
    GREEDY_JOKER("Greedy Joker", "Played cards with Diamond suit give +3 Mult when scored", JokerRarity.COMMON, 5, GreedyJoker::new),
    LUSTY_JOKER("Lusty Joker", "Played cards with Heart suit give +3 Mult when scored", JokerRarity.COMMON, 5, LustyJoker::new),
    WRATHFUL_JOKER("Wrathful Joker", "Played cards with Spade suit give +3 Mult when scored", JokerRarity.COMMON, 5, WrathfulJoker::new),
    GLUTTONOUS_JOKER("Gluttonous Joker", "Played cards with Club suit give +3 Mult when scored", JokerRarity.COMMON, 5, GluttonousJoker::new),

    JOLLY_JOKER("Jolly Joker", "+8 Mult if played hand contains a Pair", JokerRarity.COMMON, 3, JollyJoker::new),
    ZANY_JOKER("Zany Joker", "+12 Mult if played hand contains a Three of a Kind", JokerRarity.COMMON, 4, ZanyJoker::new),
    MAD_JOKER("Mad Joker", "+10 Mult if played hand contains a Two Pair", JokerRarity.COMMON, 4, MadJoker::new),
    CRAZY_JOKER("Crazy Joker", "+12 Mult if played hand contains a Straight", JokerRarity.COMMON, 4, CrazyJoker::new),
    DROLL_JOKER("Droll Joker", "+10 Mult if played hand contains a Flush", JokerRarity.COMMON, 4, DrollJoker::new),

    SLY_JOKER("Sly Joker", "+50 Chips if played hand contains a Pair", JokerRarity.COMMON, 3, SlyJoker::new),
    WILY_JOKER("Wily Joker", "+100 Chips if played hand contains a Three of a Kind", JokerRarity.COMMON, 4, WilyJoker::new),
    CLEVER_JOKER("Clever Joker", "+80 Chips if played hand contains a Two Pair", JokerRarity.COMMON, 4, CleverJoker::new),
    DEVIOUS_JOKER("Devious Joker", "+100 Chips if played hand contains a Straight", JokerRarity.COMMON, 4, DeviousJoker::new),
    CRAFTY_JOKER("Crafty Joker", "+80 Chips if played hand contains a Flush", JokerRarity.COMMON, 4, CraftyJoker::new),

    HALF_JOKER("Half Joker", "+20 Mult if played hand contains 3 or fewer cards", JokerRarity.COMMON, 5, HalfJoker::new),
    STENCIL_JOKER("Stencil", "X1 Mult for each empty Joker slot", JokerRarity.UNCOMMON, 8, StencilJoker::new),
    // FOUR_FINGERS_JOKER
    // MIME_JOKER
    // CREDIT_CARD_JOKER
    // CEREMONIAL_DAGGER_JOKER
    BANNER_JOKER("Banner", "+30 Chips for each remaining discard", JokerRarity.COMMON, 5, BannerJoker::new),
    MYSTIC_SUMMIT_JOKER("Mystic Summit", "+15 Mult when 0 discards remaining", JokerRarity.COMMON, 5, MysticSummitJoker::new),
    // MARBLE_JOKER
    // LOYALTY_CARD_JOKER
    // EIGHT_BALL_JOKER
    MISPRINT_JOKER("Misprint", "+0-23 Mult", JokerRarity.COMMON, 4, MisprintJoker::new),
    // DUSK_JOKER
    RAISED_FIST("Raised Fist", "Adds double the rank of lowest ranked card held in hand to Mult", JokerRarity.COMMON, 5, RaisedFistJoker::new),
    // CHAOS_THE_CLOWN_JOKER
    FIBONACCI_JOKER("Fibonacci", "Each played Ace, 2, 3, 5, or 8 gives +8 Mult when scored", JokerRarity.UNCOMMON, 8, FibonacciJoker::new),
    // STEEL_JOKER
    SCARY_FACE_JOKER("Scary Face", "Played face cards give +30 Chips when scored", JokerRarity.COMMON, 4, ScaryFaceJoker::new),
    ABSTRACT_JOKER("Abstract", "+3 Mult for each Joker card", JokerRarity.COMMON, 4, AbstractJoker::new),

    ;

    private final String name;
    private final String description;
    private final JokerRarity rarity;
    private final int cost;
    private final Supplier<JokerCard> supplier;

    private JokerType(String name, String description, JokerRarity rarity, int cost, Supplier<JokerCard> supplier) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.cost = cost;
        this.supplier = supplier;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public JokerRarity getRarity() {
        return this.rarity;
    }

    public int getCost() {
        return this.cost;
    }

    public Supplier<JokerCard> getSupplier() {
        return this.supplier;
    }

    public int getSpriteRow() {
        return ordinal() / 5;
    }

    public int getSpriteColumn() {
        return ordinal() % 5;
    }

}
