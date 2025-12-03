package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.util.SortMode;
import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedBox;
import dev.stevensci.jokerpoker.elements.PixelatedButton;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;

public class CardPane extends BorderPane {

    private final HBox cardArea;

    private PixelatedButton playHandButton;
    private PixelatedButton discardButton;
    private PixelatedButton sortRankButton;
    private PixelatedButton sortSuitButton;

    public CardPane() {
        setPadding(Constant.PADDING_INSETS);

        Node bottomNode = getBottomNode();
        setAlignment(bottomNode, Pos.CENTER);
        setBottom(bottomNode);

        this.cardArea = new HBox(Constant.SPACING);
        this.cardArea.setFillHeight(false);
        this.cardArea.setAlignment(Pos.CENTER);

        setAlignment(this.cardArea, Pos.CENTER);
        setCenter(this.cardArea);

        setTop(getTopNode());

        BackgroundImage background = new BackgroundImage(Constant.BACKGROUND_IMAGE,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        setBackground(new Background(background));
    }

    public void reset() {
        this.cardArea.getChildren().clear();
    }

    public List<CardView> addCards(List<PlayingCard> cards) {
        List<CardView> views = new ArrayList<>();

        for (PlayingCard card : cards) {
            views.add(new CardView(card));
        }

        this.cardArea.getChildren().addAll(views);

        return views;
    }

    public void sortCards(SortMode mode) {
        FXCollections.sort(this.cardArea.getChildren(), (a, b) -> {
            if (!(a instanceof CardView viewA) || (!(b instanceof CardView viewB))) return 0;
            return mode.getComparator().compare(viewA.getCard(), viewB.getCard());
        });
    }

    public void discard() {
        this.cardArea.getChildren().removeIf(node -> {
            if (!(node instanceof CardView cardView)) return false;
            return cardView.isSelected();
        });
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

    public Node getTopNode() {
        GridPane layout = new GridPane(Constant.SPACING, Constant.SPACING);

        layout.getColumnConstraints().addAll(Constant.COL_70, Constant.COL_30);

        HBox jokerBox = new HBox(Constant.SPACING);
        jokerBox.setAlignment(Pos.CENTER);

        layout.addRow(0, new PixelatedContentBox(Constant.GRAY, jokerBox), new PixelatedBox(Constant.GRAY));

        Label jokerCount = new Label("2/5", Color.WHITE, Constant.GRAY.darker());
        Label consumableCount = new Label("0/2", Color.WHITE, Constant.GRAY.darker());
        layout.addRow(1, jokerCount, consumableCount);

        GridPane.setHalignment(jokerCount, HPos.LEFT);
        GridPane.setHalignment(consumableCount, HPos.RIGHT);

        return layout;
    }

    public HBox getCardArea() {
        return this.cardArea;
    }

    public PixelatedButton getPlayHandButton() {
        return this.playHandButton;
    }

    public PixelatedButton getDiscardButton() {
        return this.discardButton;
    }

    public PixelatedButton getSortRankButton() {
        return this.sortRankButton;
    }

    public PixelatedButton getSortSuitButton() {
        return this.sortSuitButton;
    }

}
