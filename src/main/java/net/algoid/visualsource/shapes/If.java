/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.algoid.visualsource.VisualSourcePlaceHolder;
import net.algoid.visualsource.shapes.SnapRegion.Type;

/**
 *
 * @author cyann
 */
public class If extends InstructionPane {

    private static final double MOVE_EFFECT_DISPLACE = 4.0;
    private static final double MOVE_EFFECT_BLUR = 5.0;

    private final VisualSourcePlaceHolder placeHolder;
    private final Canvas canvas;

    private final DropShadow moveShadow;

    private double currentDeltaX = 0;
    private double currentDeltaY = 0;

    public If(VisualSourcePlaceHolder placeHolder, double x, double y) {
        super(100, 100);

        this.placeHolder = placeHolder;

        setId("if" + new Random().nextInt(Integer.MAX_VALUE));
        setLayoutX(x);
        setLayoutY(y);

        canvas = new Canvas(100, 100);
        getChildren().add(canvas);

        createSnap(Type.EXPRESSION, 100, 0, true);
        createSnap(Type.INSTRUCTION, 0, 100, true);

        moveShadow = new DropShadow(MOVE_EFFECT_BLUR, MOVE_EFFECT_DISPLACE, MOVE_EFFECT_DISPLACE, Color.GRAY);

        // event management
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                this_onMousePresser(event);
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                this_onMouseDragged(event);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                this_onMouseReleased(event);
            }
        });

        boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                this_boundInParentChanged(observable, oldValue, newValue);
            }
        });
    }

    protected void this_onMousePresser(MouseEvent event) {
        currentDeltaX = event.getX();
        currentDeltaY = event.getY();

        if (getParent() instanceof SnapRegion) {
            ((SnapRegion) getParent()).removeInstruction();
            placeHolder.getChildren().add(this);
            this_onMouseDragged(event);
        }

        setLayoutX(getLayoutX() - MOVE_EFFECT_DISPLACE);
        setLayoutY(getLayoutY() - MOVE_EFFECT_DISPLACE);

        this.toFront();
        this.setEffect(moveShadow);
        event.consume();

    }

    private double bound(double v, double size, double max) {
        if (v < MOVE_EFFECT_BLUR) {
            return MOVE_EFFECT_BLUR;
        }
        if (v + size >= max) {
            return max - size;
        }

        return v;
    }

    protected void this_onMouseDragged(MouseEvent event) {
        Point2D pt = placeHolder.sceneToLocal(event.getSceneX(), event.getSceneY());
        double x = pt.getX() - currentDeltaX;
        double y = pt.getY() - currentDeltaY;

        x = bound(x, getBoundsInLocal().getWidth(), getParent().getBoundsInLocal().getWidth());
        y = bound(y, getBoundsInLocal().getHeight(), getParent().getBoundsInLocal().getHeight());

        setLayoutX(x);
        setLayoutY(y);
    }

    protected void this_onMouseReleased(MouseEvent event) {
        this.setEffect(null);
        setLayoutX(getLayoutX() + MOVE_EFFECT_DISPLACE);
        setLayoutY(getLayoutY() + MOVE_EFFECT_DISPLACE);

        SnapRegion snap = placeHolder.queryRegionIntersecton(this);

        // snap
        if (snap != null) {
            setLayoutX(0);
            setLayoutY(0);

            if (snap.containsInstruction()) {
                InstructionPane existingChild = snap.getInstruction();
                Bounds childBounds = snap.getParent().localToParent(existingChild.getBoundsInLocal());
                snap.removeInstruction();

                SnapRegion localSnap = findSnapOfType(snap.getType());
                if (localSnap != null) {
                    localSnap.setInstruction(existingChild);
                } else {
                    placeHolder.getChildren().add(existingChild);
                    existingChild.setLayoutX(childBounds.getMinX() + childBounds.getWidth() / 2);
                    existingChild.setLayoutY(childBounds.getMinY() + childBounds.getHeight() / 2);
                }
            }
            snap.setInstruction(this);
        }
    }

    protected void this_boundInParentChanged(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
        // TODO Check here collisions
    }

    private Color color = Color.color(Math.random(), Math.random(), Math.random());

    @Override
    protected void layoutChildren() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, 100, 100);
        gc.setFill(color);
        gc.fillRect(0, 0, 100, 100);
    }
}
