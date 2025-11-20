package dev.stevensci.jokerpoker;

import dev.stevensci.jokerpoker.view.Draggable;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class DragManager {

    private static final double DRAG_THRESHOLD = 10;

    private final Pane overlay;

    private final EventHandler<MouseEvent> mousePressedHandler;
    private final EventHandler<MouseEvent> mouseDraggedHandler;
    private final EventHandler<MouseEvent> mouseReleasedHandler;

    private Scene scene;

    private Node node;
    private Pane parent;
    private Region placeholder;

    private double offsetX;
    private double offsetY;

    private double pressX;
    private double pressY;

    private boolean locked;
    private boolean dragging;
    private boolean dragStarted;

    public DragManager(Pane overlay) {
        this.overlay = overlay;
        this.mousePressedHandler = this::mousePressedHandler;
        this.mouseDraggedHandler = this::handleMouseDragged;
        this.mouseReleasedHandler = this::handleMouseReleased;
    }

    public void install(Scene scene) {
        if (this.scene != null) {
            throw new IllegalStateException("Handlers already installed on scene");
        }

        this.scene = scene;
        this.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, this.mousePressedHandler);
        this.scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, this.mouseDraggedHandler);
        this.scene.addEventHandler(MouseEvent.MOUSE_RELEASED, this.mouseReleasedHandler);
    }

    public void uninstall() {
        this.scene.removeEventHandler(MouseEvent.MOUSE_PRESSED, this.mousePressedHandler);
        this.scene.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this.mouseDraggedHandler);
        this.scene.removeEventHandler(MouseEvent.MOUSE_RELEASED, this.mouseReleasedHandler);
        this.scene = null;
    }

    private void mousePressedHandler(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        if (this.locked) return;

        Node draggableNode = findDraggableAncestor(event.getPickResult().getIntersectedNode());
        if (draggableNode == null || !(draggableNode.getParent() instanceof Pane parent)) {
            return;
        }

        this.node = draggableNode;
        this.parent = parent;

        this.pressX = event.getSceneX();
        this.pressY = event.getSceneY();

        Bounds sceneBounds = this.node.localToScene(this.node.getBoundsInLocal());

        this.offsetX = this.pressX - sceneBounds.getMinX();
        this.offsetY = this.pressY - sceneBounds.getMinY();
    }

    public void handleMouseDragged(MouseEvent event) {
        if (this.locked) return;
        if (this.node == null) return;

        double deltaX = event.getSceneX() - this.pressX;
        double deltaY = event.getSceneY() - this.pressY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (!this.dragStarted && distance < DRAG_THRESHOLD) {
            return;
        }

        if (!this.dragStarted) {
            this.dragStarted = true;
            this.dragging = true;

            Bounds nodeBounds = this.node.localToScene(this.node.getBoundsInLocal());

            this.placeholder = createPlaceholder(nodeBounds);

            this.parent.getChildren().set(this.parent.getChildren().indexOf(this.node), this.placeholder);
            this.parent.layout();

            this.overlay.getChildren().add(this.node);

            ScaleTransition scale = new ScaleTransition(Duration.millis(200), this.node);
            scale.setToX(1.2);
            scale.setToY(1.2);
            scale.play();
        }

        if (this.dragging) {
            updateDragPosition(event);
        }
    }

    private Node findDraggableAncestor(Node start) {
        Node current = start;

        while (current != null) {
            if (current instanceof Draggable) {
                return current;
            }

            current = current.getParent();
        }

        return null;
    }

    private void updateDragPosition(MouseEvent event) {
        this.node.relocate(event.getSceneX() - this.offsetX, event.getSceneY() - this.offsetY);
        updatePlaceholderPosition();
    }

    private void handleMouseReleased(MouseEvent event) {
        if (this.locked) return;
        if (!this.dragging) return;

        this.locked = true;

        Bounds bounds = this.placeholder.localToScene(this.placeholder.getBoundsInLocal());

        Timeline move = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(this.node.layoutXProperty(), bounds.getMinX(), Interpolator.EASE_BOTH),
                        new KeyValue(this.node.layoutYProperty(), bounds.getMinY(), Interpolator.EASE_BOTH)
                )
        );

        ScaleTransition scale = new ScaleTransition(Duration.millis(200), this.node);
        scale.setToX(1);
        scale.setToY(1);

        FadeTransition fade = new FadeTransition(Duration.millis(200), this.placeholder);
        fade.setToValue(0);

        ParallelTransition anim = new ParallelTransition(move, scale, fade);

        anim.setOnFinished(e -> {
            this.overlay.getChildren().remove(this.node);

            int targetIndex = this.parent.getChildren().indexOf(this.placeholder);
            this.parent.getChildren().set(targetIndex, this.node);

            this.node = null;
            this.parent = null;
            this.placeholder = null;

            this.dragging = false;
            this.dragStarted = false;

            this.locked = false;
        });

        anim.play();
        event.consume();
    }

    private void updatePlaceholderPosition() {
        if (this.locked) return;
        if (this.placeholder == null || this.parent == null) return;
        if (this.parent.getChildren().size() <= 1) return;

        Bounds sceneBounds = this.node.localToScene(this.node.getBoundsInLocal());
        double draggedCenterX = sceneBounds.getMinX() + this.node.getLayoutBounds().getWidth() / 2.0;

        List<Node> children = new ArrayList<>(this.parent.getChildren());
        children.remove(this.placeholder);

        if (children.isEmpty()) return;

        int insertIndex = 0;

        for (Node child : children) {
            Bounds childBounds = child.localToScene(child.getBoundsInLocal());
            double childCenterX = childBounds.getMinX() + child.getLayoutBounds().getWidth() / 2.0;

            if (draggedCenterX < childCenterX) {
                break;
            }

            insertIndex++;
        }

        int currentIndex = this.parent.getChildren().indexOf(this.placeholder);

        if (currentIndex == insertIndex) {
            return;
        }

        this.parent.getChildren().remove(this.placeholder);
        this.parent.getChildren().add(insertIndex, this.placeholder);
    }

    private Region createPlaceholder(Bounds bounds) {
        Region placeholder = new Region();

        placeholder.setPrefSize(bounds.getWidth(), bounds.getHeight());
        placeholder.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        placeholder.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        placeholder.setBackground(new Background(
                new BackgroundFill(Color.color(1, 1, 1, 0.2), CornerRadii.EMPTY, Insets.EMPTY)
        ));

        placeholder.setBorder(new Border(new BorderStroke(
                Color.WHITE,
                BorderStrokeStyle.DASHED,
                CornerRadii.EMPTY,
                new BorderWidths(2)
        )));

        return placeholder;
    }

}
