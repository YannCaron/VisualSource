/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author cyann
 */
public class If extends Parent {

    private static final double MOVE_EFFECT_DISPLACE = 2.0;
    private static final double MOVE_EFFECT_BLUR = 5.0;

    private final Canvas canvas;
    private final VBox block;

    private final DropShadow moveShadow;

    private double currentDeltaX = 0;
    private double currentDeltaY = 0;

    public If() {
        setId("rectangle-item");

        canvas = new Canvas(100, 100);
        getChildren().add(canvas);

        block = new VBox();
        getChildren().add(block);
        block.setLayoutX(100);
        block.setLayoutY(0);
        block.getChildren().add(new Button("Button test"));

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
        currentDeltaX = event.getSceneX() - getLayoutX();
        currentDeltaY = event.getSceneY() - getLayoutY();
        this.setEffect(moveShadow);
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
    }
    
    protected void this_boundInParentChanged(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
        // TODO Check here collisions
    }

    @Override
    protected void layoutChildren() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, 100, 100);
        gc.setFill(Color.DARKGOLDENROD);
        gc.fillRect(0, 0, 100, 100);
    }
}
