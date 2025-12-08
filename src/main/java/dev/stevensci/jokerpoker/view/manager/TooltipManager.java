package dev.stevensci.jokerpoker.view.manager;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerRarity;
import dev.stevensci.jokerpoker.view.elements.Label;
import dev.stevensci.jokerpoker.view.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.util.TextUtil;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TooltipManager {

    private final Pane overlay;
    private PixelatedContentBox tooltip;
    private Node activeNode;

    public TooltipManager(Pane overlay) {
        this.overlay = overlay;
        createTooltipShell();
    }

    private void createTooltipShell() {
        this.tooltip = new PixelatedContentBox(Color.WHITE);
        this.tooltip.setPadding(new Insets(2, 6, 6, 2));
        this.tooltip.getChildren().removeAll(tooltip.getShadowRectangles());
        this.tooltip.getLayout().setAlignment(Pos.CENTER);

        this.tooltip.setMouseTransparent(true);
        this.tooltip.setVisible(false);

        overlay.getChildren().add(tooltip);
    }

    public void attach(Node node, JokerCard card) {
        node.setOnMouseEntered(e -> showTooltip(node, card));
        node.setOnMouseExited(e -> hideTooltip(node));
    }

    private void showTooltip(Node node, JokerCard card) {
        this.activeNode = node;

        PixelatedContentBox descriptionBox = new PixelatedContentBox(Color.WHITE);
        descriptionBox.setMinSize(100, 50);

        for (String text : TextUtil.wrap(card.getType().getDescription(), 20)) {
            descriptionBox.getLayout().getChildren().add(
                    new Label(text, Constant.FONT_24, Color.BLACK, Color.WHITE)
            );
        }

        JokerRarity rarity = card.getType().getRarity();

        PixelatedContentBox content = new PixelatedContentBox(
                Constant.DARK_GRAY,
                new Label(card.getType().getName(), Constant.FONT_24, Color.WHITE, Constant.DARK_GRAY.darker()),
                new Label("$".repeat(card.getType().getCost()), Constant.FONT_24, Constant.YELLOW, Constant.DARK_GRAY.darker()),
                descriptionBox,
                new PixelatedContentBox(
                        rarity.getColor(),
                        new Label(rarity.getDisplay(), Constant.FONT_24, Color.WHITE, rarity.getColor().darker())
                )
        );

        this.tooltip.getLayout().getChildren().setAll(content);
        this.tooltip.autosize();
        this.tooltip.setVisible(true);

        updateTooltipPosition();

        node.layoutBoundsProperty().addListener(_ -> updateTooltipPosition());
        node.localToSceneTransformProperty().addListener(_ -> updateTooltipPosition());
    }

    public void hideTooltip(Node node) {
        if (node == this.activeNode) {
            this.tooltip.setVisible(false);
            this.activeNode = null;
        }
    }

    private void updateTooltipPosition() {
        if (this.tooltip == null || this.activeNode == null || this.overlay.getScene() == null) {
            return;
        }

        Bounds bounds = this.activeNode.localToScene(this.activeNode.getBoundsInLocal());

        double nodeCenterX = bounds.getMinX() + bounds.getWidth() / 2.0;
        double tooltipX = nodeCenterX - this.tooltip.getWidth() / 2.0;
        double tooltipY = bounds.getMaxY() + 5;

        this.tooltip.relocate(tooltipX, tooltipY);
    }

}
