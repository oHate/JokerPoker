package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.controller.GameController;
import dev.stevensci.jokerpoker.model.GameModel;
import dev.stevensci.jokerpoker.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private GameModel model;
    private GameView view;
    private GameController controller;

    @Override
    public void start(Stage stage) throws Exception {
        this.model = new GameModel();

        this.view = new GameView(stage);

        this.controller = new GameController(this.model, this.view);
        this.controller.initialize();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}