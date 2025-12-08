package dev.stevensci.jokerpoker.view.pane;

import dev.stevensci.jokerpoker.model.BlindType;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.view.elements.Label;
import dev.stevensci.jokerpoker.view.elements.PixelatedBox;
import dev.stevensci.jokerpoker.view.elements.PixelatedContentBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SidebarPane extends Pane {

    private final VBox root = new VBox(Constant.SPACING);
    private final StackPane header = new StackPane();

    private Text scoreLabel;
    private Text handTypeText;
    private Text chipsLabel;
    private Text multLabel;
    private Text handsLabel;
    private Text discardsLabel;
    private Text cashLabel;
    private Text anteLabel;
    private Text roundLabel;

    public SidebarPane() {
        this.root.setPadding(Constant.PADDING_INSETS);
        this.root.prefHeightProperty().bind(heightProperty());
        this.root.setBackground(Background.fill(Constant.GRAY));
        this.root.getChildren().addAll(
                this.header,
                getRoundScoreNode(),
                getHandTypeNode(),
                getFooterNode()
        );

        getChildren().add(this.root);
    }

    public void updateSidebar(BlindType type, long targetScore) {
        VBox layout = new VBox(Constant.SPACING,
                new PixelatedContentBox(type.getPrimaryColor(),
                        new Label(type.getDisplay(), Color.WHITE, type.getPrimaryColor().darker())
                ),
                new PixelatedContentBox(type.getSecondaryColor(),
                        new Label("Score at Least", Color.WHITE, type.getSecondaryColor().darker()),
                        new PixelatedContentBox(type.getPrimaryColor(),
                                new Label(Constant.NUMBER_FORMAT.format(targetScore), Color.WHITE, type.getPrimaryColor().darker())
                        )
                )
        );

        layout.setPadding(Constant.PADDING_INSETS);

        this.header.getChildren().clear();
        this.header.getChildren().addAll(new PixelatedBox(Constant.DARK_GRAY), layout);

        this.root.setBorder(new Border(new BorderStroke(
                Color.TRANSPARENT, type.getSecondaryColor(), Color.TRANSPARENT, type.getSecondaryColor(),
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(Constant.SPACING), Insets.EMPTY
        )));
    }

    private Node getFooterNode() {
        this.handsLabel = new Label("0", Constant.LIGHT_BLUE, Constant.GRAY.darker());
        this.discardsLabel = new Label("0", Constant.LIGHT_RED, Constant.GRAY.darker());
        this.cashLabel = new Label("$0", Constant.LIGHT_YELLOW, Constant.GRAY.darker());
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
                        new Label("Cash", Color.WHITE, Constant.DARK_GRAY.darker()),
                        new PixelatedContentBox(Constant.GRAY, this.cashLabel)
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

    private Node getHandTypeNode() {
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

    public Text getCashLabel() {
        return this.cashLabel;
    }

    public Text getAnteLabel() {
        return this.anteLabel;
    }

    public Text getRoundLabel() {
        return this.roundLabel;
    }

}
