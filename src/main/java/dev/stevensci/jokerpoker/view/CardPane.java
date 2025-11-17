package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.Constant;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedBox;
import dev.stevensci.jokerpoker.elements.PixelatedButton;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class CardPane extends BorderPane {

    private final Pane overlayPane;
    private final List<PlayingCard> cards;

    private PixelatedButton playHandButton;
    private PixelatedButton discardButton;
    private PixelatedButton sortRankButton;
    private PixelatedButton sortSuitButton;

    public CardPane(Pane overlayPane, List<PlayingCard> cards) {
        this.overlayPane = overlayPane;
        this.cards = cards;

        setPadding(Constant.PADDING_INSETS);

        Node bottomNode = getBottomNode();
        setAlignment(bottomNode, Pos.CENTER);
        setBottom(bottomNode);

        Node centerNode = getCenterNode();

        setAlignment(centerNode, Pos.CENTER);
        setCenter(centerNode);

        setTop(getHeaderNode());

        BackgroundImage background = new BackgroundImage(Constant.BACKGROUND_IMAGE,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        setBackground(new Background(background));
    }

    public Node getCenterNode() {
        HBox centerNode = new HBox(Constant.SPACING);
        centerNode.setFillHeight(false);

        for (PlayingCard card : cards) {
            DraggableView draggableView = new DraggableView(centerNode, this.overlayPane);
            draggableView.getChildren().add(card.getView());
        }

        centerNode.setAlignment(Pos.CENTER);

        return centerNode;
    }

    public Node getBottomNode() {
        this.playHandButton = new PixelatedButton(
                new Label("Play Hand", Color.WHITE, Constant.BLUE.darker()),
                Constant.BLUE
        );

        this.playHandButton.getLayout().setAlignment(Pos.TOP_CENTER);
        this.playHandButton.setPrefWidth(150);

        this.sortRankButton = new PixelatedButton(
                new Label("Rank", Color.WHITE, Constant.ORANGE.darker()),
                Constant.ORANGE
        );

        this.sortSuitButton = new PixelatedButton(
                new Label("Suit", Color.WHITE, Constant.ORANGE.darker()),
                Constant.ORANGE
        );

        GridPane sortHandGrid = new GridPane(Constant.SPACING, 0);
        sortHandGrid.setHgap(Constant.SPACING);
        sortHandGrid.addRow(0, this.sortRankButton, this.sortSuitButton);

        PixelatedContentBox sortHandBox = new PixelatedContentBox(Constant.GRAY,
                new Label("Sort Hand", Color.WHITE, Constant.GRAY.darker()),
                sortHandGrid
        );

        this.discardButton = new PixelatedButton(
                new Label("Discard", Color.WHITE, Constant.RED.darker()),
                Constant.RED
        );

        this.discardButton.getLayout().setAlignment(Pos.TOP_CENTER);
        this.discardButton.setPrefWidth(150);

        HBox root = new HBox(Constant.SPACING,
                this.playHandButton,
                sortHandBox,
                this.discardButton
        );

        root.setAlignment(Pos.CENTER);

        HBox container = new HBox(new PixelatedContentBox(Constant.DARK_GRAY, root));
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(Region.USE_PREF_SIZE);

        return container;
    }

    public Node getHeaderNode() {
        GridPane layout = new GridPane(Constant.SPACING, Constant.SPACING);

        layout.getColumnConstraints().addAll(Constant.COL_70, Constant.COL_30);

        HBox jokerBox = new HBox(Constant.SPACING);
        jokerBox.setAlignment(Pos.CENTER);

        DraggableView j1 = new DraggableView(jokerBox, overlayPane);
        j1.getChildren().add(Constant.JOKERS_SPRITESHEET.getView(0, 0));

        DraggableView j2 = new DraggableView(jokerBox, overlayPane);
        j2.getChildren().add(Constant.JOKERS_SPRITESHEET.getView(1, 0));

        layout.addRow(0, new PixelatedContentBox(Constant.GRAY, jokerBox), new PixelatedBox(Constant.GRAY));

        Label jokerCount = new Label("2/5", Color.WHITE, Constant.GRAY.darker());
        Label consumableCount = new Label("0/2", Color.WHITE, Constant.GRAY.darker());
        layout.addRow(1, jokerCount, consumableCount);

        GridPane.setHalignment(jokerCount, HPos.LEFT);
        GridPane.setHalignment(consumableCount, HPos.RIGHT);

        return layout;
    }

}
