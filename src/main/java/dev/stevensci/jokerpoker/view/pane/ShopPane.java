package dev.stevensci.jokerpoker.view.pane;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.view.elements.Label;
import dev.stevensci.jokerpoker.view.elements.PixelatedButton;
import dev.stevensci.jokerpoker.view.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.view.node.CardNode;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ShopPane extends SlidingPane {

    private final HBox jokerArea;

    private final GridPane layout = new GridPane(Constant.SPACING, Constant.SPACING);

    private Label shopLabel;
    private PixelatedContentBox shopHeader;

    private final PixelatedButton nextRoundButton;
    private final PixelatedButton rerollButton;

    public ShopPane() {
        this.nextRoundButton = new PixelatedButton(
                new Label("Next Round", Color.WHITE, Constant.RED.darker()),
                Constant.RED
        );
        this.nextRoundButton.setPrefHeight(75);

        this.rerollButton = new PixelatedButton(
                new Label("Reroll $3", Color.WHITE, Constant.GREEN.darker()),
                Constant.GREEN
        );
        this.rerollButton.setPrefHeight(75);

        this.layout.getColumnConstraints().addAll(
                new ColumnConstraints(){{
                    setHgrow(Priority.NEVER);
                }},
                new ColumnConstraints(){{
                    setHgrow(Priority.ALWAYS);
                }}
        );

        this.shopLabel = new Label("SHOP", Color.WHITE, Constant.BLUE.darker());
        this.shopHeader = new PixelatedContentBox(Constant.BLUE, this.shopLabel);

        this.jokerArea = new HBox(Constant.SPACING);
        this.jokerArea.setAlignment(Pos.CENTER);

        getRoot().getLayout().getChildren().addAll(
                new PixelatedContentBox(Constant.DARK_GRAY, this.shopHeader),
                new HBox(Constant.SPACING,
                        new PixelatedContentBox(Constant.DARK_GRAY, this.nextRoundButton, this.rerollButton),
                        new PixelatedContentBox(Constant.DARK_GRAY, this.jokerArea){{
                            HBox.setHgrow(this, Priority.ALWAYS);
                        }}
                )
        );
    }

    public List<CardNode<JokerCard>> addCards(List<JokerCard> cards) {
        this.jokerArea.getChildren().clear();

        List<CardNode<JokerCard>> views = new ArrayList<>();

        for (JokerCard card : cards) {
            views.add(new CardNode<>(card));
        }

        this.jokerArea.getChildren().addAll(views);

        return views;
    }

    public void removeCard(CardNode<JokerCard> card) {
        this.jokerArea.getChildren().remove(card);
    }

    public void updateHeader(Color color) {
        super.setColor(color);
        this.shopHeader.setColor(color);
        ((DropShadow)this.shopLabel.getEffect()).setColor(color.darker());
    }

    public HBox getJokerArea() {
        return this.jokerArea;
    }

    public PixelatedButton getNextRoundButton() {
        return this.nextRoundButton;
    }

    public PixelatedButton getRerollButton() {
        return this.rerollButton;
    }

}
