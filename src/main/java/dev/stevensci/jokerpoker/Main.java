package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GameModel model = new GameModel();
        GameView view = new GameView(stage);
        GameController controller = new GameController(model, view);

        controller.initialize();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}