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

import java.text.NumberFormat;

public class SidebarPane extends Pane {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    private PixelatedContentBox scoreBox;
    private Text handTypeText;
    private PixelatedContentBox chipsBox;
    private PixelatedContentBox multBox;

    private PixelatedContentBox handsBox;
    private PixelatedContentBox discardsBox;
    private PixelatedContentBox moneyBox;
    private PixelatedContentBox anteBox;
    private PixelatedContentBox roundBox;

    public SidebarPane(BlindType type, int targetScore) {
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
                getHeaderNode(type, targetScore),
                getRoundScoreNode(),
                getHandTypeNode(),
                getFooterNode()
        );

        getChildren().add(root);
    }

    public Node getHeaderNode(BlindType type, int targetScore) {
        VBox layout = new VBox(Constant.SPACING,
                new PixelatedContentBox(type.getPrimaryColor(),
                        new Label(type.getDisplay(), Color.WHITE, type.getPrimaryColor().darker())
                ),
                new PixelatedContentBox(Constant.GRAY,
                        new Label("Score at Least", Color.WHITE, type.getPrimaryColor().darker()),
                        new PixelatedContentBox(type.getSecondaryColor(),
                                new Label(NUMBER_FORMAT.format(targetScore), Color.WHITE, type.getSecondaryColor().darker())
                        )
                )
        );

        layout.setPadding(Constant.PADDING_INSETS);

        return new StackPane(new PixelatedBox(Constant.DARK_GRAY), layout);
    }

    public Node getFooterNode() {
        this.handsBox = new PixelatedContentBox(Constant.GRAY, new Label("0", Constant.LIGHT_BLUE, Constant.GRAY.darker()));
        this.discardsBox = new PixelatedContentBox(Constant.GRAY, new Label("0", Constant.LIGHT_RED, Constant.GRAY.darker()));
        this.moneyBox = new PixelatedContentBox(Constant.GRAY, new Label("$0", Constant.LIGHT_YELLOW, Constant.GRAY.darker()));
        this.anteBox = new PixelatedContentBox(Constant.GRAY, new Label("1/8", Constant.LIGHT_ORANGE, Constant.GRAY.darker()));
        this.roundBox = new PixelatedContentBox(Constant.GRAY, new Label("1", Constant.LIGHT_ORANGE, Constant.GRAY.darker()));

        GridPane layout = new GridPane(Constant.SPACING, Constant.SPACING);
        layout.getColumnConstraints().addAll(Constant.COL_50, Constant.COL_50);

        layout.addRow(0,
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Hands", Color.WHITE, Constant.DARK_GRAY.darker()),
                        this.handsBox
                ),
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Discards", Color.WHITE, Constant.DARK_GRAY.darker()),
                        this.discardsBox
                )
        );

        layout.add(
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Money", Color.WHITE, Constant.DARK_GRAY.darker()),
                        this.moneyBox
                ),
                0, 1, 2, 1
        );

        layout.addRow(2,
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Ante", Color.WHITE, Constant.DARK_GRAY.darker()),
                        this.anteBox
                ),
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label("Round", Color.WHITE, Constant.DARK_GRAY.darker()),
                        this.roundBox
                )
        );

        return layout;
    }

    public Node getHandTypeNode() {
        GridPane layout = new GridPane(Constant.SPACING, 0);
        layout.getColumnConstraints().addAll(Constant.COL_45, Constant.COL_10, Constant.COL_45);

        this.chipsBox = new PixelatedContentBox(Constant.BLUE,
                new Label("0", Color.WHITE, Constant.BLUE.darker())
        );
        this.chipsBox.getLayout().setAlignment(Pos.CENTER_RIGHT);

        this.multBox = new PixelatedContentBox(Constant.RED,
                new Label("0", Color.WHITE, Constant.RED.darker())
        );
        this.multBox.getLayout().setAlignment(Pos.CENTER_LEFT);

        layout.addRow(0,
                this.chipsBox,
                new Label("X", Color.WHITE, Constant.DARK_GRAY.darker()),
                this.multBox
        );

        this.handTypeText = new Label("None", Color.WHITE, Constant.DARK_GRAY.darker());

        return new PixelatedContentBox(Constant.DARK_GRAY,
                this.handTypeText,
                layout
        );
    }

    private StackPane getRoundScoreNode() {
        PixelatedBox container = new PixelatedBox(Constant.DARK_GRAY);

        this.scoreBox = new PixelatedContentBox(Constant.GRAY,
                new Label("0", Color.WHITE, Constant.GRAY.darker())
        );

        HBox.setHgrow(this.scoreBox, Priority.ALWAYS);

        HBox layout = new HBox(Constant.SPACING, new Label("Score", Color.WHITE, Constant.DARK_GRAY.darker()), this.scoreBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(Constant.PADDING_INSETS);

        return new StackPane(container, layout);
    }

}
