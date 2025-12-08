package dev.stevensci.jokerpoker.view.pane;

import dev.stevensci.jokerpoker.view.elements.Label;
import dev.stevensci.jokerpoker.view.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;

public class GameOverPane extends SlidingPane {


    public GameOverPane() {
        getRoot().getLayout().getChildren().addAll(
                new PixelatedContentBox(Constant.DARK_GRAY, new Label("GAME OVER", Constant.FONT_64, Constant.LIGHT_RED, Constant.DARK_GRAY.darker()))
        );
    }

}
