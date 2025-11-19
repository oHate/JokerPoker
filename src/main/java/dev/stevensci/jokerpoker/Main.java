package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        JokerPokerGame game = new JokerPokerGame();
        GameView gameView = new GameView(game.getBlind());

        Scene scene = new Scene(gameView, 1200, 700);

        DragManager dragManager = new DragManager(gameView.getOverlayPane());
        dragManager.install(scene);

        stage.setTitle("Joker Poker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}