package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.BlindType;
import dev.stevensci.jokerpoker.Constant;
import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedBox;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SidebarPane extends Pane {

    private final BlindType type;
    private final long targetScore;

    private Text scoreLabel;
    private Text handTypeText;
    private Text chipsLabel;
    private Text multLabel;
    private Text handsLabel;
    private Text discardsLabel;
    private Text moneyLabel;
    private Text anteLabel;
    private Text roundLabel;

    public SidebarPane(BlindType type, long targetScore) {
        this.type = type;
        this.targetScore = targetScore;

        VBox root = new VBox(Constant.SPACING);
        root.setPadding(Constant.PADDING_INSETS);

        root.prefHeightProperty().bind(heightProperty());
        root.setBorder(new Border(new BorderStroke(
                Color.TRANSPARENT, type.getSecondaryColor(), Color.TRANSPARENT, type.getSecondaryColor(),
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(Constant.SPACING), Insets.EMPTY
        )));
        root.setBackground(Background.fill(Constant.GRAY));

        root.getChildren().addAll(
                getHeaderNode(),
                getRoundScoreNode(),
                getHandTypeNode(),
                getFooterNode()
        );

        getChildren().add(root);
    }

    public Node getHeaderNode() {
        VBox layout = new VBox(Constant.SPACING,
                new PixelatedContentBox(this.type.getPrimaryColor(),
                        new Label(this.type.getDisplay(), Color.WHITE, this.type.getPrimaryColor().darker())
                ),
                new PixelatedContentBox(type.getSecondaryColor(),
                        new Label("Score at Least", Color.WHITE, this.type.getSecondaryColor().darker()),
                        new PixelatedContentBox(this.type.getPrimaryColor(),
                                new Label(Constant.NUMBER_FORMAT.format(this.targetScore), Color.WHITE, this.type.getPrimaryColor().darker())
                        )
                )
        );

        layout.setPadding(Constant.PADDING_INSETS);

        return new StackPane(new PixelatedBox(Constant.DARK_GRAY), layout);
    }

    public Node getFooterNode() {
        this.handsLabel = new Label("0", Constant.LIGHT_BLUE, Constant.GRAY.darker());
        this.discardsLabel = new Label("0", Constant.LIGHT_RED, Constant.GRAY.darker());
        this.moneyLabel = new Label("$0", Constant.LIGHT_YELLOW, Constant.GRAY.darker());
        this.anteLabel = new Label("1/8", Constant.LIGHT_ORANGE, Constant.GRAY.darker());
        this.roundLabel = new Label("1", Constant.LIGHT_ORANGE, Constant.GRAY.darker());

        GridPane layout = new GridPane(Constant.SPACING, Constant.SPACING);
        layout.getColumnConstraints().addAll(Constant.COL_50, Constant.COL_50);

        layout.addRow(0,
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Hands", Color.WHITE, Constant.DARK_GRAY.darker()),
                        new PixelatedContentBox(Constant.GRAY, this.handsLabel)
                ),
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Discards", Color.WHITE, Constant.DARK_GRAY.darker()),
                        new PixelatedContentBox(Constant.GRAY, this.discardsLabel)
                )
        );

        layout.add(
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Money", Color.WHITE, Constant.DARK_GRAY.darker()),
                        new PixelatedContentBox(Constant.GRAY, this.moneyLabel)
                ),
                0, 1, 2, 1
        );

        layout.addRow(2,
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Ante", Color.WHITE, Constant.DARK_GRAY.darker()),
                        new PixelatedContentBox(Constant.GRAY, this.anteLabel)
                ),
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Round", Color.WHITE, Constant.DARK_GRAY.darker()),
                        new PixelatedContentBox(Constant.GRAY, this.roundLabel)
                )
        );

        return layout;
    }

    public Node getHandTypeNode() {
        GridPane layout = new GridPane(Constant.SPACING, 0);
        layout.getColumnConstraints().addAll(Constant.COL_45, Constant.COL_10, Constant.COL_45);

        this.chipsLabel = new Label("0", Color.WHITE, Constant.BLUE.darker());
        this.multLabel = new Label("0", Color.WHITE, Constant.RED.darker());

        PixelatedContentBox chipsBox = new PixelatedContentBox(Constant.BLUE, this.chipsLabel);
        chipsBox.getLayout().setAlignment(Pos.CENTER_RIGHT);

        PixelatedContentBox multBox = new PixelatedContentBox(Constant.RED, this.multLabel);
        multBox.getLayout().setAlignment(Pos.CENTER_LEFT);

        layout.addRow(0,
                chipsBox,
                new Label("X", Color.WHITE, Constant.DARK_GRAY.darker()),
                multBox
        );

        this.handTypeText = new Label("None", Color.WHITE, Constant.DARK_GRAY.darker());

        return new PixelatedContentBox(Constant.DARK_GRAY,
                this.handTypeText,
                layout
        );
    }

    private StackPane getRoundScoreNode() {
        PixelatedBox container = new PixelatedBox(Constant.DARK_GRAY);

        this.scoreLabel = new Label("0", Color.WHITE, Constant.GRAY.darker());

        PixelatedContentBox scoreBox = new PixelatedContentBox(Constant.GRAY, this.scoreLabel);
        HBox.setHgrow(scoreBox, Priority.ALWAYS);

        HBox layout = new HBox(Constant.SPACING, new Label("Score", Color.WHITE, Constant.DARK_GRAY.darker()), scoreBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(Constant.PADDING_INSETS);

        return new StackPane(container, layout);
    }

    public Text getScoreLabel() {
        return this.scoreLabel;
    }

    public Text getHandTypeText() {
        return this.handTypeText;
    }

    public Text getChipsLabel() {
        return this.chipsLabel;
    }

    public Text getMultLabel() {
        return this.multLabel;
    }

    public Text getHandsLabel() {
        return this.handsLabel;
    }

    public Text getDiscardsLabel() {
        return this.discardsLabel;
    }

    public Text getMoneyLabel() {
        return this.moneyLabel;
    }

    public Text getAnteLabel() {
        return this.anteLabel;
    }

    public Text getRoundLabel() {
        return this.roundLabel;
    }

}
