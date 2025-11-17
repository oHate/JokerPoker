package dev.stevensci.jokerpoker.view;

import dev.stevensci.jokerpoker.card.Card;
import javafx.animation.*;
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

public class DraggableView extends StackPane {

    private static final double DRAG_THRESHOLD = 15;

    private double offsetX;
    private double offsetY;
    private Region placeholder;

    private boolean dragging;
    private boolean dragStarted;

    private double pressX;
    private double pressY;

    private final HBox cardBox;
    private final Pane overlayPane;

    private boolean selected;

    private EventHandler<MouseEvent> sceneDragHandler;
    private EventHandler<MouseEvent> sceneReleaseHandler;

    public DraggableView(HBox cardBox, Pane overlayPane) {
        this.overlayPane = overlayPane;
        this.cardBox = cardBox;

        setOnMousePressed(this::handleOnMousePressed);
        setOnMouseDragged(this::handleOnMouseDragged);
        setOnMouseReleased(this::handleOnMouseReleased);

        cardBox.getChildren().add(this);
    }

    public void handleOnMousePressed(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
//        if (this.overlayPane == null || this.cardBox == null) return;
        if (getParent() != this.cardBox) return;

        this.pressX = event.getSceneX();
        this.pressY = event.getSceneY();
        this.dragging = false;
        this.dragStarted = false;
    }

    public void handleOnMouseDragged(MouseEvent event) {
        System.out.println("handle OnMouseDragged");
//        if (this.overlayPane == null || this.cardBox == null) return;
        if (getParent() != this.cardBox && !this.dragStarted) return;

        double deltaX = event.getSceneX() - this.pressX;
        double deltaY = event.getSceneY() - this.pressY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (!this.dragStarted && distance < DRAG_THRESHOLD) {
            return;
        }

        if (!this.dragStarted) {
            this.dragStarted = true;
            this.dragging = true;
            startDragOperation(event);
        }

        if (this.dragging) {
            updateDragPosition(event);
        }
    }

    private void startDragOperation(MouseEvent event) {
        System.out.println("startDragOperation");
        Bounds sceneBounds = localToScene(getBoundsInLocal());

        this.offsetX = event.getSceneX() - sceneBounds.getMinX();
        this.offsetY = event.getSceneY() - sceneBounds.getMinY();
        int originalIndex = this.cardBox.getChildren().indexOf(this);

        boolean wasSelected = this.selected;
        if (wasSelected) {
            setEffect(null);
            setTranslateY(0);
        }

        this.placeholder = createPlaceholder();
        this.cardBox.getChildren().set(originalIndex, this.placeholder);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), this.placeholder);
        fadeIn.setToValue(0.5);
        fadeIn.play();

        Point2D overlayPos = this.overlayPane.sceneToLocal(
                sceneBounds.getMinX(),
                sceneBounds.getMinY()
        );

        this.overlayPane.getChildren().add(this);
        relocate(overlayPos.getX(), overlayPos.getY());

        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(120), this);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);
        scaleUp.play();

        overlayPane.setMouseTransparent(false);

        // Set up scene-level handlers for continuous tracking
        Scene scene = getScene();

        if (scene != null) {
            sceneDragHandler = this::handleSceneDrag;
            sceneReleaseHandler = this::handleSceneRelease;

            scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneDragHandler);
            scene.addEventFilter(MouseEvent.MOUSE_RELEASED, sceneReleaseHandler);
        }
    }

    private void updateDragPosition(MouseEvent event) {
        System.out.println("UpdateDragPosition");
        Point2D overlayPos = this.overlayPane.sceneToLocal(
                event.getSceneX() - this.offsetX,
                event.getSceneY() - this.offsetY
        );

        relocate(overlayPos.getX(), overlayPos.getY());
        updatePlaceholderPosition();
    }

    private void handleSceneDrag(MouseEvent event) {
        updateDragPosition(event);
        event.consume();
    }

    public void handleOnMouseReleased(MouseEvent event) {
        if (this.dragStarted) {
            return;
        }

        this.selected = !this.selected;

        if (this.selected) {
            setTranslateY(-10);
        } else {
            setTranslateY(0);
        }
    }

    private void handleSceneRelease(MouseEvent event) {
        System.out.println("handleSceneRelease");
        if (!this.dragging) return;

        Scene scene = getScene();
        if (scene != null && sceneDragHandler != null && sceneReleaseHandler != null) {
            System.out.println("Scene Release");
            scene.removeEventFilter(MouseEvent.MOUSE_DRAGGED, sceneDragHandler);
            scene.removeEventFilter(MouseEvent.MOUSE_RELEASED, sceneReleaseHandler);
        }

        finishDrag(event);
        event.consume();
    }

    private void finishDrag(MouseEvent event) {
        System.out.println("finishDrag");
        this.dragging = false;
        this.dragStarted = false;

//        if (overlayPane == null || cardBox == null) return;

//        if (this.placeholder == null) {
//            this.overlayPane.getChildren().remove(this);
//            this.overlayPane.setMouseTransparent(true);
//            this.cardBox.getChildren().add(this);
//            return;
//        }

        int targetIndex = this.cardBox.getChildren().indexOf(this.placeholder);

        Bounds placeholderSceneBounds = this.placeholder.localToScene(this.placeholder.getBoundsInLocal());
        Point2D placeholderPosition = this.overlayPane.sceneToLocal(placeholderSceneBounds.getMinX(), placeholderSceneBounds.getMinY());

        Timeline move = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(layoutXProperty(), getLayoutX()),
                        new KeyValue(layoutYProperty(), getLayoutY())
                ),
                new KeyFrame(Duration.millis(180),
                        new KeyValue(layoutXProperty(), placeholderPosition.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(layoutYProperty(), placeholderPosition.getY(), Interpolator.EASE_BOTH) // TODO -> translate -10 if selected since it snaps weird
                )
        );

        ScaleTransition scaleBack = new ScaleTransition(Duration.millis(180), this);
        scaleBack.setToX(1.0);
        scaleBack.setToY(1.0);

        FadeTransition fadePlaceholder = new FadeTransition(Duration.millis(180), this.placeholder);
        fadePlaceholder.setToValue(0.2);

        ParallelTransition anim = new ParallelTransition(move, scaleBack, fadePlaceholder);
        anim.setOnFinished(ev -> {
            this.overlayPane.getChildren().remove(this);
            this.overlayPane.setMouseTransparent(true);

            this.cardBox.getChildren().remove(this.placeholder);
            this.placeholder = null;

            this.cardBox.getChildren().add(Math.max(0, Math.min(targetIndex, this.cardBox.getChildren().size())), this);

            if (this.selected) {
                setTranslateY(-10);
            }
        });

        anim.play();
    }

    // TODO -> translate -10 if selected
    private Region createPlaceholder() {
        Bounds sceneBounds = localToScene(getBoundsInLocal());

        Region placeholder = new Region();
        placeholder.setPrefSize(sceneBounds.getWidth(), sceneBounds.getHeight());
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

        placeholder.setOpacity(0);

        return placeholder;
    }

    private void updatePlaceholderPosition() {
        if (this.placeholder == null) return;
        if (this.cardBox.getChildren().size() <= 1) return;

        Bounds sceneBounds = localToScene(getBoundsInLocal());
        double draggedCenterX = sceneBounds.getMinX() + sceneBounds.getWidth() / 2.0;

        List<Node> children = new ArrayList<>(this.cardBox.getChildren());
        children.remove(this.placeholder);

        if (children.isEmpty()) return;

        int insertIndex = 0;

        for (Node child : children) {
            Bounds childBounds = child.localToScene(child.getBoundsInLocal());
            double childCenterX = childBounds.getMinX() + childBounds.getWidth() / 2.0;

            if (draggedCenterX < childCenterX) {
                break;
            }

            insertIndex++;
        }

        int currentIndex = this.cardBox.getChildren().indexOf(this.placeholder);

        if (currentIndex == insertIndex) {
            return;
        }

        this.cardBox.getChildren().remove(this.placeholder);
        this.cardBox.getChildren().add(insertIndex, this.placeholder);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}