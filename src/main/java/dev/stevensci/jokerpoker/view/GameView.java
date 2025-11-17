package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.BlindType;
import dev.stevensci.jokerpoker.blind.Blind;
import javafx.scene.layout.*;

public class GameView extends StackPane {

    private Blind blind;

    private Pane overlayPane;
    private SidebarPane sidebarPane;
    private CardPane cardPane;

    public GameView(Blind blind) {
        this.blind = blind;

        this.overlayPane = new Pane();
        this.overlayPane.setPickOnBounds(true);
        this.overlayPane.setMouseTransparent(true);

        BorderPane layout = new BorderPane();

        this.sidebarPane = new SidebarPane(BlindType.SMALL, 1);
        layout.setLeft(this.sidebarPane);

        this.cardPane = new CardPane(this.overlayPane, this.blind.getHand());
        layout.setCenter(this.cardPane);

        getChildren().addAll(layout, this.overlayPane);
    }


}
