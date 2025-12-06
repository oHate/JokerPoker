package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.blind.BlindType;
import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedButton;
import dev.stevensci.jokerpoker.util.Constant;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.List;

public class ContinuePane extends SlidingPane {

    private final GridPane cashEntries = new GridPane();
    private final PixelatedButton continueButton;

    public ContinuePane() {
        this.continueButton = new PixelatedButton(
                new Label("", Color.WHITE, Constant.YELLOW.darker()),
                Constant.YELLOW
        );

        this.continueButton.setPrefHeight(75);

        this.cashEntries.getColumnConstraints().addAll(
                new ColumnConstraints(){{
                    setHalignment(HPos.LEFT);
                    setHgrow(Priority.ALWAYS);
                }},
                new ColumnConstraints(){{
                    setHalignment(HPos.RIGHT);
                    setHgrow(Priority.NEVER);
                }}
        );

        getRoot().getLayout().getChildren().addAll(
                this.continueButton,
                this.cashEntries
        );
    }

    public void setCashEntries(List<Pair<String, Integer>> breakdown) {
        this.cashEntries.getChildren().clear();

        int cashTotal = 0;

        for (int i = 0; i < breakdown.size(); i++) {
            Pair<String, Integer> entry = breakdown.get(i);

            int cashMade = entry.getValue();
            cashTotal += cashMade;

            this.cashEntries.add(new Label(entry.getKey(), Color.WHITE, Constant.GRAY.darker()), 0, i);
            this.cashEntries.add(new Label("$".repeat(cashMade), Constant.YELLOW, Constant.GRAY.darker()), 1, i);
        }

        this.continueButton.getLabel().setText("Cash Out: $" + cashTotal);
    }

    public PixelatedButton getContinueButton() {
        return this.continueButton;
    }

}
