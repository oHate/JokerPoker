package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.view.GameView;
import javafx.scene.layout.Pane;

public class GameController {

    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void initialize() {
        this.view.initializeSidebar(this.model.getBlindType(), this.model.getTargetScore());
        this.view.initializeGamePane(this.model.getBlind().getHand());
    }

    public Pane createGamePane() {
        return null;
    }

}
