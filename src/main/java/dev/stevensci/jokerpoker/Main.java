package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.card.meta.CardRank;
import dev.stevensci.jokerpoker.card.meta.CardSuit;
import dev.stevensci.jokerpoker.view.CardPane;
import dev.stevensci.jokerpoker.view.GameView;
import dev.stevensci.jokerpoker.view.SidebarPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Pane overlay = new Pane();
//        overlay.setPickOnBounds(true);
//        overlay.setMouseTransparent(true);
//
//        BorderPane layout = new BorderPane();
//
//        layout.setLeft(new SidebarPane(BlindType.SMALL, 1200));
//
//        StackPane gamePane = new StackPane();
//        gamePane.getChildren().add(new CardPane(
//                overlay,
//                List.of(
//                        new PlayingCard(CardRank.TWO, CardSuit.HEART),
//                        new PlayingCard(CardRank.THREE, CardSuit.HEART),
//                        new PlayingCard(CardRank.FOUR, CardSuit.HEART),
//                        new PlayingCard(CardRank.FIVE, CardSuit.HEART),
//                        new PlayingCard(CardRank.SIX, CardSuit.HEART),
//                        new PlayingCard(CardRank.SEVEN, CardSuit.HEART),
//                        new PlayingCard(CardRank.EIGHT, CardSuit.HEART)
//                )
//        ));
//
//        layout.setCenter(gamePane);
//
//        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/pokertable.jpg"));
//
//        BackgroundImage background = new BackgroundImage(backgroundImage,
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
//        );
//
//        gamePane.setBackground(new Background(background));

        JokerPokerGame game = new JokerPokerGame();

        Scene scene = new Scene(new GameView(game.getBlind()), 1200, 700);

        stage.setTitle("Joker Poker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}