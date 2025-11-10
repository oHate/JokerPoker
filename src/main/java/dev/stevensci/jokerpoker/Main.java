package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.view.GamePane;
import dev.stevensci.jokerpoker.view.SidebarPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        root.setLeft(new SidebarPane(BlindType.SMALL, 1200));
        root.setCenter(new GamePane());

        Scene scene = new Scene(root, 1000, 700);

        stage.setTitle("Joker Poker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}