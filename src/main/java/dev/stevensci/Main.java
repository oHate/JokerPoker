package dev.stevensci;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(new Label("Hello World!"));

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Joker Poker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}