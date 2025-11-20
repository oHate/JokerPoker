package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.BlindType;
import dev.stevensci.jokerpoker.DragManager;
import dev.stevensci.jokerpoker.blind.HandType;
import dev.stevensci.jokerpoker.card.PlayingCard;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class GameView extends StackPane {

    private final BorderPane layoutPane;
    private final Pane overlayPane;
    private final Scene scene;
    private final DragManager dragManager;

    private SidebarPane sidebarPane;
    private CardPane gamePane;

    public GameView(Stage stage) {
        this.layoutPane = new BorderPane();

        this.overlayPane = new Pane();
        this.overlayPane.setMouseTransparent(true);

        getChildren().addAll(this.layoutPane, this.overlayPane);

        this.scene = new Scene(this, 1200, 700);

        this.dragManager = new DragManager(this.overlayPane);
        this.dragManager.install(this.scene);

        stage.setTitle("Joker Poker");
        stage.setScene(this.scene);
        stage.setResizable(false);
    }

    public void initializeSidebar(BlindType type, long targetScore) {
        this.sidebarPane = new SidebarPane(type, targetScore);
        this.layoutPane.setLeft(this.sidebarPane);
    }

    public void initializeGamePane(List<PlayingCard> hand) {
        this.gamePane = new CardPane(hand);
        layoutPane.setCenter(this.gamePane);
    }

    public void updateHandType(HandType type) {
        this.sidebarPane.getHandTypeText().setText(type.getDisplay());
        this.sidebarPane.getChipsLabel().setText(String.valueOf(type.getChips()));
        this.sidebarPane.getMultLabel().setText(String.valueOf(type.getMultiplier()));
    }

    public void updateHands(int hands) {
        this.sidebarPane.getHandsLabel()
                .setText(String.valueOf(hands));
    }

    public void updateDiscards(int discards) {
        this.sidebarPane.getHandsLabel().setText(String.valueOf(discards));
    }

    public void updateMoney(int money) {
        this.sidebarPane.getMoneyLabel().setText("$" + money);
    }

    public void updateAnte(int ante) {
        this.sidebarPane.getAnteLabel().setText(String.valueOf(ante));
    }

    public void updateRound(int round) {
        this.sidebarPane.getRoundLabel().setText(String.valueOf(round));
    }

}
