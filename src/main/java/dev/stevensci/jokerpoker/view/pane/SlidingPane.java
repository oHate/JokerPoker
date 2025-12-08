package dev.stevensci.jokerpoker.view.pane;

import dev.stevensci.jokerpoker.view.elements.PixelatedBox;
import dev.stevensci.jokerpoker.view.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SlidingPane extends PixelatedBox {

    private static final int SIDEBAR_WIDTH = 264;

    private final PixelatedContentBox root = new PixelatedContentBox(Constant.GRAY);

    private boolean initialized = false;
    private TranslateTransition showTransition;
    private TranslateTransition hideTransition;

    public SlidingPane() {
        super(Constant.BLUE);

        this.root.getLayout().setAlignment(Pos.TOP_CENTER);

        this.showTransition = new TranslateTransition(Duration.millis(200), this);
        this.showTransition.setToY(20);
        this.showTransition.setInterpolator(Interpolator.EASE_BOTH);

        this.hideTransition = new TranslateTransition(Duration.millis(200), this);
        this.hideTransition.setInterpolator(Interpolator.EASE_BOTH);

        getChildren().add(this.root);
        setPrefSize(650, 500);
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

        if (!this.initialized) {
            setTranslateY(getHeight());
            this.hideTransition.setToY(getHeight());
            this.initialized = true;
        } else {
            this.hideTransition.setToY(getHeight());
        }
    }

    public PixelatedContentBox getRoot() {
        return this.root;
    }

    public TranslateTransition getShowTransition() {
        return this.showTransition;
    }

    public TranslateTransition getHideTransition() {
        return this.hideTransition;
    }

}
