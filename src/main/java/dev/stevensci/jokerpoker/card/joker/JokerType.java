package dev.stevensci.jokerpoker.card.joker;

import dev.stevensci.jokerpoker.card.joker.common.*;

import java.util.function.Supplier;

public enum JokerType {
    JIMBO("Jimbo", "+4 Mult", JokerRarity.COMMON, 2, JimboJoker::new),
    GREEDY("Greedy", "Played cards with Diamond suit give +3 Mult when scored", JokerRarity.COMMON, 5, GreedyJoker::new),
    LUSTY("Lusty", "Played cards with Heart suit give +3 Mult when scored", JokerRarity.COMMON, 5, LustyJoker::new),
    WRATHFUL("Wrathful", "Played cards with Spade suit give +3 Mult when scored", JokerRarity.COMMON, 5, WrathfulJoker::new),
    GLUTTONOUS("Gluttonous", "Played cards with Club suit give +3 Mult when scored", JokerRarity.COMMON, 5, GluttonousJoker::new),

    JOLLY("Jolly", "+8 Mult if played hand contains a Pair", JokerRarity.COMMON, 3, JollyJoker::new),
    ZANY("Zany", "+12 Mult if played hand contains a Three of a Kind", JokerRarity.COMMON, 4, ZanyJoker::new),
    MAD("Mad", "+10 Mult if played hand contains a Two Pair", JokerRarity.COMMON, 4, MadJoker::new),
    CRAZY("Crazy", "+12 Mult if played hand contains a Straight", JokerRarity.COMMON, 4, CrazyJoker::new),
    DROLL("Droll", "+10 Mult if played hand contains a Flush", JokerRarity.COMMON, 4, DrollJoker::new),

    SLY("Sly", "+50 Chips if played hand contains a Pair", JokerRarity.COMMON, 3, SlyJoker::new),
    WILY("Wily", "+100 Chips if played hand contains a Three of a Kind", JokerRarity.COMMON, 4, WilyJoker::new),
    CLEVER("Clever", "+80 Chips if played hand contains a Two Pair", JokerRarity.COMMON, 4, CleverJoker::new),
    DEVIOUS("Devious", "+100 Chips if played hand contains a Straight", JokerRarity.COMMON, 4, DeviousJoker::new),
    CRAFTY("Crafty", "+80 Chips if played hand contains a Flush", JokerRarity.COMMON, 4, CraftyJoker::new);

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

    // TODO
    public int getSpriteRow() {
        return ordinal() / 5;
    }

    // TODO
    public int getSpriteColumn() {
        return ordinal() % 5;
    }

}
