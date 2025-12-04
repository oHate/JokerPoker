package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedBox;
import dev.stevensci.jokerpoker.elements.PixelatedButton;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ContinuePane extends PixelatedBox {

    private static final int SIDEBAR_WIDTH = 264;

    private final PixelatedContentBox root = new PixelatedContentBox(Constant.GRAY);
    private final PixelatedButton continueButton;

    public ContinuePane() {
        super(Constant.BLUE);

        this.continueButton = new PixelatedButton(
                new Label("Cash Out: $0", Color.WHITE, Constant.YELLOW.darker()),
                Constant.YELLOW
        );

        this.continueButton.setPrefHeight(75);

        GridPane grid = new GridPane();

        grid.add(new Label("Score at least: 300", Color.WHITE, Constant.GRAY.darker()), 0, 0);
        grid.add(new Label("3 Remaining Hands ($1 each)", Color.WHITE, Constant.GRAY.darker()), 0, 1);

        grid.add(new Label("$", Constant.YELLOW, Constant.GRAY.darker()), 1, 0);
        grid.add(new Label("$$$", Constant.YELLOW, Constant.GRAY.darker()), 1, 1);

        this.root.getLayout().setAlignment(Pos.TOP_CENTER);
        this.root.getLayout().getChildren().addAll(
                this.continueButton,
                grid
        );

        getChildren().add(this.root);
        setPrefSize(500, 450);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        if (!(getParent() instanceof Pane parent)) return;

        double parentWidth = parent.getWidth();
        double parentHeight = parent.getHeight();

        double width = getWidth();
        double height = getHeight();

        setLayoutX((parentWidth - width) / 2 + (SIDEBAR_WIDTH / 2));
        setLayoutY(parentHeight - height);
        setTranslateY(height);
    }

    public void display() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);
        transition.setToY(20);
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.play();

        // TODO -> This will disable mouse clicks but should be reverted after continue is clicked
        getParent().setMouseTransparent(false);
    }

    public void hide() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);
        transition.setToY(getHeight());
        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.play();

        // TODO -> This will disable mouse clicks but should be reverted after continue is clicked
        getParent().setMouseTransparent(true);
    }

    public PixelatedButton getContinueButton() {
        return this.continueButton;
    }

}
