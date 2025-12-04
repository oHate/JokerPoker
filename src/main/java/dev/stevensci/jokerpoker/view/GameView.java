package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.blind.BlindType;
import dev.stevensci.jokerpoker.blind.HandType;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameView extends StackPane {

    private final BorderPane layoutPane;
    private final Pane overlayPane;
    private final Scene scene;
    private final DragManager dragManager;

    private ContinuePane continuePane = new ContinuePane();
    private SidebarPane sidebarPane;
    private CardPane gamePane;

    public GameView(Stage stage) {
        this.layoutPane = new BorderPane();

        this.gamePane = new CardPane();
        this.layoutPane.setCenter(this.gamePane);

        this.sidebarPane = new SidebarPane();
        this.layoutPane.setLeft(this.sidebarPane);

        this.overlayPane = new Pane();
        this.overlayPane.setMouseTransparent(true);
        this.gamePane.getChildren().add(this.continuePane);

        getChildren().addAll(this.layoutPane, this.overlayPane);

        this.scene = new Scene(this, 1200, 700);

        this.dragManager = new DragManager(this.overlayPane);
        this.dragManager.install(this.scene);

        stage.setTitle("Joker Poker");
        stage.setScene(this.scene);
        stage.setResizable(false);
    }

    public void updateHandType(HandType type) {
        this.sidebarPane.getHandTypeText().setText(type.getDisplay());
        this.sidebarPane.getChipsLabel().setText(String.valueOf(type.getChips()));
        this.sidebarPane.getMultLabel().setText(String.valueOf(type.getMultiplier()));
    }

    public void updateScore(long score) {
        this.sidebarPane.getScoreLabel().setText(String.valueOf(score));
    }

    public void updateHands(int hands) {
        this.sidebarPane.getHandsLabel()
                .setText(String.valueOf(hands));
    }

    public void updateDiscards(int discards) {
        this.sidebarPane.getDiscardsLabel().setText(String.valueOf(discards));
    }

    public void updateMoney(int money) {
        this.sidebarPane.getMoneyLabel().setText("$" + money);
    }

    public void updateAnte(int ante) {
        this.sidebarPane.getAnteLabel().setText(ante + "/8");
    }

    public void updateRound(int round) {
        this.sidebarPane.getRoundLabel().setText(String.valueOf(round));
    }

    public Pane getOverlayPane() {
        return this.overlayPane;
    }

    public ContinuePane getContinuePane() {
        return this.continuePane;
    }

    public SidebarPane getSidebarPane() {
        return this.sidebarPane;
    }

    public CardPane getGamePane() {
        return this.gamePane;
    }

}
