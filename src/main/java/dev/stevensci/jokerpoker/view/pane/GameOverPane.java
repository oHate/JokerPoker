package dev.stevensci.jokerpoker.view.pane;

import dev.stevensci.jokerpoker.view.elements.Label;
import dev.stevensci.jokerpoker.view.elements.PixelatedButton;
import dev.stevensci.jokerpoker.view.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GameOverPane extends SlidingPane {

    private final PixelatedButton restartButton;
    private final PixelatedButton exitButton;

    public GameOverPane() {
        setColor(Constant.LIGHT_RED);

        this.restartButton = new PixelatedButton(new Label("Restart", Color.WHITE, Constant.GREEN.darker()), Constant.GREEN);
        this.restartButton.setPrefHeight(75);

        this.exitButton = new PixelatedButton(new Label("Exit", Color.WHITE, Constant.RED.darker()), Constant.RED);
        this.exitButton.setPrefHeight(75);

        GridPane buttonLayout = new GridPane(Constant.SPACING, Constant.SPACING);
        buttonLayout.getColumnConstraints().addAll(Constant.COL_50, Constant.COL_50);
        buttonLayout.addRow(0, this.restartButton, this.exitButton);

        getRoot().getLayout().getChildren().addAll(
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("GAME OVER", Constant.FONT_64, Constant.LIGHT_RED, Constant.DARK_GRAY.darker()),
                        new Label("Would you like to try again?", Color.WHITE, Constant.DARK_GRAY.darker())
                ), new PixelatedContentBox(Constant.DARK_GRAY, buttonLayout)
        );
    }

    public PixelatedButton getRestartButton() {
        return this.restartButton;
    }

    public PixelatedButton getExitButton() {
        return this.exitButton;
    }

}
