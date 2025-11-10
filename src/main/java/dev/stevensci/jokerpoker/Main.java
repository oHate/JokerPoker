package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.view.GamePane;
import dev.stevensci.jokerpoker.view.SidebarPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        root.setLeft(new SidebarPane(BlindType.SMALL, 1200));

        StackPane gamePane = new StackPane();
        gamePane.getChildren().add(new GamePane());
        root.setCenter(gamePane);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/pokertable.jpg"));

        BackgroundImage background = new BackgroundImage(backgroundImage, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, 
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        gamePane.setBackground(new Background(background));
        


        Scene scene = new Scene(root, 1000, 700);

        stage.setTitle("Joker Poker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}