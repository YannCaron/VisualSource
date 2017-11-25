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
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.algoid.visualsource.VisualSourcePlaceHolder;

/**
 *
 * @author cyann
 */
public class If extends Pane {

    private static final double MOVE_EFFECT_DISPLACE = 2.0;
    private static final double MOVE_EFFECT_BLUR = 5.0;

    private final VisualSourcePlaceHolder placeHolder;
    private final Canvas canvas;

    private final DropShadow moveShadow;

    private double currentDeltaX = 0;
    private double currentDeltaY = 0;

    private final SnapPane args;
    private final SnapPane block;

    public If(VisualSourcePlaceHolder placeHolder, double x, double y) {
        this.placeHolder = placeHolder;
        setId("if" + new Random().nextInt(Integer.MAX_VALUE));

        setLayoutX(x);
        setLayoutY(y);

        canvas = new Canvas(100, 100);
        getChildren().add(canvas);

        args = new SnapPane();
        getChildren().add(args);
        args.setLayoutX(100);
        args.setLayoutY(0);

        block = new SnapPane();
        getChildren().add(block);
        block.setLayoutX(0);
        block.setLayoutY(100);

        moveShadow = new DropShadow(MOVE_EFFECT_BLUR, MOVE_EFFECT_DISPLACE * 2, MOVE_EFFECT_DISPLACE * 2, Color.GRAY);

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
        setLayoutX(getLayoutX() - MOVE_EFFECT_DISPLACE);
        setLayoutY(getLayoutY() - MOVE_EFFECT_DISPLACE);

        if (getParent() instanceof Pane && getParent() != placeHolder) {
            ((Pane) getParent()).getChildren().remove(this);

            placeHolder.getChildren().add(this);

            this_onMouseDragged(event);
        } else {
            currentDeltaX = event.getSceneX() - getLayoutX();
            currentDeltaY = event.getSceneY() - getLayoutY();
        }

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
        double x = event.getSceneX() - currentDeltaX;
        double y = event.getSceneY() - currentDeltaY;

        x = bound(x, getBoundsInLocal().getWidth(), getParent().getBoundsInLocal().getWidth());
        y = bound(y, getBoundsInLocal().getHeight(), getParent().getBoundsInLocal().getHeight());

        setLayoutX(x);
        setLayoutY(y);
    }

    protected void this_onMouseReleased(MouseEvent event) {
        this.setEffect(null);
        setLayoutX(getLayoutX() + MOVE_EFFECT_DISPLACE);
        setLayoutY(getLayoutY() + MOVE_EFFECT_DISPLACE);

        Pane pane = placeHolder.queryRegionIntersecton(this);

        // snap
        if (pane != null && getParent() instanceof Pane) {
            ((Pane) getParent()).getChildren().remove(this);
            setLayoutX(0);
            setLayoutY(0);
            if (!pane.getChildren().isEmpty()) {
                Node existingChild = pane.getChildren().get(0);
                pane.getChildren().remove(existingChild);
                // TODO JIRA #1
                this.args.getChildren().add(existingChild);
            }
            pane.getChildren().add(this);
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
