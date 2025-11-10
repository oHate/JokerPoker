package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.BlindType;
import dev.stevensci.jokerpoker.Constant;
import dev.stevensci.jokerpoker.elements.PixelatedBox;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.elements.PixelatedTextBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.NumberFormat;

public class SidebarPane extends Pane {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    private PixelatedTextBox scoreBox;
    private Text handTypeText;
    private PixelatedTextBox chipsBox;
    private PixelatedTextBox multBox;

    private PixelatedTextBox handsBox;
    private PixelatedTextBox discardsBox;
    private PixelatedTextBox moneyBox;
    private PixelatedTextBox anteBox;
    private PixelatedTextBox roundBox;

    public SidebarPane(BlindType type, int targetScore) {
        VBox root = new VBox(Constant.SPACING);
        root.setPadding(Constant.PADDING_INSETS);

        root.prefHeightProperty().bind(heightProperty());
        root.setBorder(new Border(new BorderStroke(
                Color.TRANSPARENT, type.getSecondaryColor(), Color.TRANSPARENT, type.getSecondaryColor(),
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(4), Insets.EMPTY
        )));
        root.setBackground(Background.fill(Constant.GRAY));

        root.getChildren().addAll(
                getHeaderNode(type, targetScore),
                getRoundScoreNode(),
                getHandTypeNode(),
                getFooterNode()
        );

        getChildren().add(root);
    }

    public Node getHeaderNode(BlindType type, int targetScore) {
        PixelatedContentBox statisticBox = new PixelatedContentBox(
                "Score at Least",
                type.getSecondaryColor(),
                new PixelatedTextBox(NUMBER_FORMAT.format(targetScore), type.getPrimaryColor())
        );

        PixelatedTextBox blindBox = new PixelatedTextBox(type.getDisplay(), type.getPrimaryColor());

        VBox layout = new VBox(Constant.SPACING, blindBox, statisticBox);
        layout.setPadding(Constant.PADDING_INSETS);

        return new StackPane(new PixelatedBox(Constant.DARK_GRAY), layout);
    }

    public Node getFooterNode() {
        this.handsBox = new PixelatedTextBox("0", Constant.LIGHT_BLUE, Constant.GRAY);
        this.discardsBox = new PixelatedTextBox("0", Constant.LIGHT_RED, Constant.GRAY);
        this.moneyBox = new PixelatedTextBox("$0", Constant.LIGHT_YELLOW, Constant.GRAY);
        this.anteBox = new PixelatedTextBox("1/8", Constant.LIGHT_ORANGE, Constant.GRAY);
        this.roundBox = new PixelatedTextBox("1", Constant.LIGHT_ORANGE, Constant.GRAY);

        GridPane layout = new GridPane(Constant.SPACING, Constant.SPACING);
        layout.getColumnConstraints().addAll(Constant.COL_50, Constant.COL_50);

        layout.addRow(0,
                new PixelatedContentBox("Hands", Constant.DARK_GRAY, this.handsBox),
                new PixelatedContentBox("Discards", Constant.DARK_GRAY, this.discardsBox)
        );

        layout.add(
                new PixelatedContentBox("Money", Constant.DARK_GRAY, this.moneyBox),
                0, 1, 2, 1
        );

        layout.addRow(2,
                new PixelatedContentBox("Ante", Constant.DARK_GRAY, this.anteBox),
                new PixelatedContentBox("Round", Constant.DARK_GRAY, this.roundBox)
        );

        return layout;
    }

    public Node getHandTypeNode() {
        GridPane layout = new GridPane(Constant.SPACING, 0);
        layout.getColumnConstraints().addAll(Constant.COL_45, Constant.COL_10, Constant.COL_45);

        this.chipsBox = new PixelatedTextBox("0", Constant.BLUE);
        this.chipsBox.getLayout().setAlignment(Pos.CENTER_RIGHT);

        this.multBox = new PixelatedTextBox("0", Constant.RED);
        this.multBox.getLayout().setAlignment(Pos.CENTER_LEFT);

        layout.addRow(0,
                this.chipsBox,
                Constant.getText("X", Color.WHITE, Constant.DARK_GRAY.darker()),
                this.multBox
        );

        PixelatedContentBox content = new PixelatedContentBox("None", Constant.DARK_GRAY, layout);
        this.handTypeText = content.getText();

        return content;
    }

    private StackPane getRoundScoreNode() {
        PixelatedBox container = new PixelatedBox(Constant.DARK_GRAY);

        this.scoreBox = new PixelatedTextBox("0", Constant.GRAY);
        HBox.setHgrow(this.scoreBox, Priority.ALWAYS);

        HBox layout = new HBox(Constant.SPACING, Constant.getText("Score", Color.WHITE, Constant.DARK_GRAY.darker()), this.scoreBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(Constant.PADDING_INSETS);

        return new StackPane(container, layout);
    }

    public PixelatedTextBox getScoreBox() {
        return scoreBox;
    }

    public Text getHandTypeText() {
        return handTypeText;
    }

    public PixelatedTextBox getChipsBox() {
        return chipsBox;
    }

    public PixelatedTextBox getMultBox() {
        return multBox;
    }

    public PixelatedTextBox getHandsBox() {
        return handsBox;
    }

    public PixelatedTextBox getDiscardsBox() {
        return discardsBox;
    }

    public PixelatedTextBox getMoneyBox() {
        return moneyBox;
    }

    public PixelatedTextBox getAnteBox() {
        return anteBox;
    }

    public PixelatedTextBox getRoundBox() {
        return roundBox;
    }

}
