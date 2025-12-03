package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedBox;
import dev.stevensci.jokerpoker.elements.PixelatedButton;
import dev.stevensci.jokerpoker.util.Constant;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ContinuePane extends PixelatedBox {

    private final PixelatedBox root = new PixelatedBox(Constant.GRAY);

    private PixelatedButton continueButton;

    public ContinuePane() {
        super(Constant.BLUE);

        GridPane layout = new GridPane();

        this.continueButton = new PixelatedButton(
                new Label("Continue", Color.WHITE, Constant.YELLOW.darker()),
                Constant.YELLOW
        );

        layout.add(this.continueButton, 0, 0);

        this.root.getChildren().add(layout);

        getChildren().add(this.root);

        setPrefSize(500, 500);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        if (!(getParent() instanceof Pane parent)) return;

        double parentWidth = parent.getWidth();
        double parentHeight = parent.getHeight();

        double width = getWidth();
        double height = getHeight();

        setLayoutX((parentWidth - width) / 2);
        setLayoutY(parentHeight - height);
        setTranslateY(height);
    }

    public void display() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);
        transition.setToY(20);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();

        // TODO -> This will disable mouse clicks but should be reverted after continue is clicked
        getParent().setMouseTransparent(false);
    }

    public void hide() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);
        transition.setToY(getHeight());
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();

        // TODO -> This will disable mouse clicks but should be reverted after continue is clicked
        getParent().setMouseTransparent(true);
    }

    public PixelatedButton getContinueButton() {
        return this.continueButton;
    }

}
