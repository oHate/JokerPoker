package dev.stevensci.jokerpoker.util;

import dev.stevensci.jokerpoker.view.elements.Label;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimationUtil {

    public static Animation buildPopupDiamondAnimation(Pane overlay, Node target, String text) {
        Bounds bounds = target.localToScene(target.getBoundsInLocal());
        Bounds parentBounds = overlay.sceneToLocal(bounds);

        double centerX = parentBounds.getMinX() + parentBounds.getWidth() / 2;
        double aboveY = parentBounds.getMinY() - 45;

        StackPane popup = new StackPane();
        popup.setAlignment(Pos.CENTER);
        popup.setPickOnBounds(false);

        Rectangle diamond = new Rectangle(40, 40);
        diamond.setFill(Constant.BLUE);
        diamond.setRotate(45);

        Label label = new Label(text, Color.WHITE, Constant.BLUE.darker());

        popup.getChildren().addAll(diamond, label);
        popup.setAlignment(Pos.CENTER);

        popup.setLayoutX(centerX - 20);
        popup.setLayoutY(aboveY - 20);

        popup.setOpacity(0);
        popup.setScaleX(0);
        popup.setScaleY(0);

        overlay.getChildren().add(popup);

        ScaleTransition scale = new ScaleTransition(Duration.millis(1000), popup);
        scale.setToX(1.5);
        scale.setToY(1.5);

        FadeTransition fade = new FadeTransition(Duration.millis(250), popup);
        fade.setDelay(Duration.millis(750));
        fade.setFromValue(1);
        fade.setToValue(0);

        ParallelTransition effect = new ParallelTransition(scale, fade);
        effect.setOnFinished(e -> overlay.getChildren().remove(popup));

        return effect;
    }

    public static ParallelTransition buildWiggleAnimation(Node node) {
        Timeline wiggle = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.rotateProperty(), 0)),
                new KeyFrame(Duration.millis(60), new KeyValue(node.rotateProperty(), -8)),
                new KeyFrame(Duration.millis(120), new KeyValue(node.rotateProperty(), 8)),
                new KeyFrame(Duration.millis(180), new KeyValue(node.rotateProperty(), -6)),
                new KeyFrame(Duration.millis(240), new KeyValue(node.rotateProperty(), 6)),
                new KeyFrame(Duration.millis(300), new KeyValue(node.rotateProperty(), 0))
        );

        ScaleTransition scale = new ScaleTransition(Duration.millis(250), node);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.12);
        scale.setToY(1.12);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        return new ParallelTransition(wiggle, scale);
    }


}
