# JokerPoker

A Balatro‑inspired card‑scoring roguelite built in **Java** with **JavaFX**. This entire project is roughly 2,500 lines of code and was created as a final project for CSCI-3331-002 - Object-Oriented Programming taught by Dr. Dipak K Singh.

---

## Contributers

**Collin Stevens**
- UI and graphical elements implementation
- Core gameplay and systems implementation
- Joker implementation

**Hector Landero**
- UML diagram
- Presentation
- Joker implementation

**Michael Ironsmith V**
- UML diagram
- Presentation
- Joker implementation

---

## Showcase Video

https://github.com/user-attachments/assets/44018d8c-9556-4a5d-bd5d-1cb22fb7e4ee

---

## Features

* Fully custom **JavaFX UI**, including:

    * Content box elements
    * Textures loaded from a spritesheet
    * Card tooltips and animations
* Complete **poker hand evaluation system**

    * Detects Straights, Flushes, Full Houses, Two‑Pair, etc.
    * Supports skipping unused cards for scoring
* **Joker system**

    * Unique effects for each joker
    * Effects can trigger before, during, or after card scoring
* **Shop system**

    * Buy, reroll, and manage Joker inventory
    * Currency system
* **Modular architecture**

    * Model / View / Controller pattern
    * UI components depend on each other like `PixelatedBox`, `PixelatedContentBox`, and `PixelatedButton`.

---

## Running the Game

```bash
mvn clean package
java -jar JokerPoker-1.0-SNAPSHOT.jar
```

> If you are executing the project in a development enviroment run the `Bootstrap` class.

---

## Asset Credits

- Poker Table - www.freepik.com
- Font (m6x11) - Daniel Linssen
- Cards - (@GreenNinja2525, @MeirGavish, @MathisMartin31)

### Jokers
1. Joker - (@GreenNinja2525)
2. Greedy Joker - (@GreenNinja2525)
3. Lusty Joker - (@GreenNinja2525)
4. Gluttonous Joker - (@GreenNinja2525)
5. Wrathful Joker - (@GreenNinja2525)
6. Jolly Joker - (@GreenNinja2525)
7. Zany Joker - (@GreenNinja2525, @MeirGavish)
8. Mad Joker - (@GreenNinja2525)
9. Crazy Joker - (@GreenNinja2525)
10. Droll Joker - (@GreenNinja2525)
11. Sly Joker - (@GreenNinja2525)
12. Wily Joker - (@GreenNinja2525, @MeirGavish)
13. Clever Joker - (@GreenNinja2525)
14. Devious Joker - (@GreenNinja2525)
15. Crafty Joker - (@GreenNinja2525)
16. Half Joker - (@GreenNinja2525)
17. Joker Stencil - (@GreenNinja2525)
18. Banner - (@GreenNinja2525)
19. Mystic Summit - (@GreenNinja2525)
20. Misprint - (@GreenNinja2525)
21. Raised Fist - (@MathisMartin31)
22. Fibonacci - (@MeirGavish, a little bit of @GreenNinja2525)
23. Scary Face - (@GreenNinja2525)
24. Abstract Joker - (@NoWhammies10, Tweaked by @GreenNinja2525)