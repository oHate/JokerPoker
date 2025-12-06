package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.joker.JokerCard;
import dev.stevensci.jokerpoker.card.joker.JokerRarity;
import dev.stevensci.jokerpoker.elements.Label;
import dev.stevensci.jokerpoker.elements.PixelatedContentBox;
import dev.stevensci.jokerpoker.util.Constant;
import dev.stevensci.jokerpoker.util.TextUtil;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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
    }

    public void attach(Node node, JokerCard card) {
        node.setOnMouseEntered(e -> showTooltip(node, card));
        node.setOnMouseExited(e -> hideTooltip());
    }

    private void showTooltip(Node node, JokerCard card) {
        if (tooltip != null && tooltip.getParent() == overlay) {
            overlay.getChildren().remove(tooltip);
        }

        activeNode = node;

        PixelatedContentBox descriptionBox = new PixelatedContentBox(Color.WHITE);
        descriptionBox.setMinSize(100, 50);

        for (String text : TextUtil.wrap(card.getType().getDescription(), 20)) {
            descriptionBox.getLayout().getChildren().add(new Label(text, Constant.FONT_24, Color.BLACK, Color.WHITE));
        }

        descriptionBox.getChildren();

        JokerRarity rarity = card.getType().getRarity();

        tooltip = new PixelatedContentBox(Color.WHITE,
                new PixelatedContentBox(Constant.DARK_GRAY,
                        new Label(card.getType().getName(), Constant.FONT_24, Color.WHITE, Constant.DARK_GRAY.darker()),
                        new Label("$".repeat(card.getType().getCost()), Constant.FONT_24, Constant.YELLOW, Constant.DARK_GRAY.darker()),
                        descriptionBox,
                        new PixelatedContentBox(rarity.getColor(), new Label(rarity.getDisplay(), Constant.FONT_24, Color.WHITE, rarity.getColor().darker()))
                )
        );

        tooltip.setPadding(new Insets(2, 6, 6, 2));
        tooltip.getChildren().removeAll(tooltip.getShadowRectangles());
        tooltip.getLayout().setAlignment(Pos.CENTER);

        if (!overlay.getChildren().contains(tooltip)) {
            overlay.getChildren().add(tooltip);
        }

        updateTooltipPosition();

        node.layoutBoundsProperty().addListener((obs, o, n) -> updateTooltipPosition());
        node.localToSceneTransformProperty().addListener((obs, o, n) -> updateTooltipPosition());
    }

    public void hideTooltip() {
        if (tooltip != null) {
            if (tooltip.getParent() == overlay) {
                overlay.getChildren().remove(tooltip);
            }
            tooltip = null;
        }
        activeNode = null;
    }

    private void updateTooltipPosition() {
        if (tooltip == null || activeNode == null || overlay.getScene() == null) {
            return;
        }

        Bounds bounds = this.activeNode.localToScene(this.activeNode.getBoundsInLocal());

        this.tooltip.relocate(bounds.getMinX() + (this.tooltip.getWidth() / 2) - (bounds.getWidth() / 2), bounds.getMaxY() + 5);
    }

}
