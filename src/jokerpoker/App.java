package jokerpoker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private final BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.root, 500, 500);

        stage.setTitle("Joker Poker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
