package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.PlayingCard;
import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedButton;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.util.SortMode;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GamePane extends StackPane {

    private final HBox cardArea;

    private HBox jokerArea;
    private Label jokerCountLabel;

    private PixelatedButton playHandButton;
    private PixelatedButton discardButton;
    private PixelatedButton sortRankButton;
    private PixelatedButton sortSuitButton;

    public GamePane() {
        setPadding(Constant.PADDING_INSETS);

        BorderPane root = new BorderPane();

        Node bottomNode = getBottomNode();
        setAlignment(bottomNode, Pos.CENTER);
        root.setBottom(bottomNode);

        this.cardArea = new HBox(Constant.SPACING);
        this.cardArea.setFillHeight(false);
        this.cardArea.setAlignment(Pos.CENTER);

        setAlignment(this.cardArea, Pos.CENTER);
        root.setCenter(this.cardArea);

        root.setTop(getTopNode());

        BackgroundImage background = new BackgroundImage(Constant.BACKGROUND_IMAGE,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        setBackground(new Background(background));
        getChildren().add(root);
    }

    public void reset() {
        this.cardArea.getChildren().clear();
    }

    public List<PlayingCardView> addCards(List<PlayingCard> cards) {
        List<PlayingCardView> views = new ArrayList<>();

        for (PlayingCard card : cards) {
            views.add(new PlayingCardView(card));
        }

        this.cardArea.getChildren().addAll(views);

        return views;
    }

    public void sortCards(SortMode mode) {
        FXCollections.sort(this.cardArea.getChildren(), (a, b) -> {
            if (!(a instanceof PlayingCardView viewA) || (!(b instanceof PlayingCardView viewB))) return 0;
            return mode.getComparator().compare(viewA.getCard(), viewB.getCard());
        });
    }

    public void discard() {
        this.cardArea.getChildren().removeIf(node -> {
            if (!(node instanceof PlayingCardView playingCardView)) return false;
            return playingCardView.isSelected();
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

        return container;
    }

    public Node getTopNode() {
        VBox layout = new VBox(Constant.SPACING);

        this.jokerArea = new HBox(Constant.SPACING);
        this.jokerArea.setAlignment(Pos.CENTER);

        this.jokerCountLabel = new Label("0/5", Color.WHITE, Constant.GRAY.darker());

        layout.getChildren().addAll(new PixelatedContentBox(Constant.GRAY, this.jokerArea) {{
            setMinHeight(128);
        }}, this.jokerCountLabel);

        return layout;
    }

    public HBox getJokerArea() {
        return this.jokerArea;
    }

    public Label getJokerCountLabel() {
        return this.jokerCountLabel;
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
