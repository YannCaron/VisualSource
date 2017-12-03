/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 *
 * @author cyann
 */
public abstract class AssociatedHook extends Hook {

    private final Node tip;
    private final double offsetX;
    private final double offsetY;

    // constructor
    public AssociatedHook(LinkableRegion parent, Node tip, Direction direction) {
        super(parent, direction, tip.getBoundsInLocal());
        this.tip = tip;
        offsetX = getLayoutX() - tip.getLayoutX();
        offsetY = getLayoutY() - tip.getLayoutY();

        this.setOnOverEvent(this::this_onOver);
        this.setOnOutEvent(this::this_onOut);
        layoutXProperty().addListener(this::this_onLayoutXChanged);
        layoutYProperty().addListener(this::this_onLayoutYChanged);

        Platform.runLater(() -> {
            applyOutEffect(tip);
        });
    }

    // abstract
    protected abstract void applyOverEffect(Node tip);

    protected abstract void applyOutEffect(Node tip);

    // event management
    protected void this_onOver(HookEvent event) {
        if (event.getHook() == this) {
            applyOverEffect(tip);
            event.consume();
        }
    }

    protected void this_onOut(HookEvent event) {
        if (event.getHook() == this) {
            applyOutEffect(tip);
            event.consume();
        }
    }

    protected void this_onLayoutXChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        tip.setLayoutX(newValue.doubleValue() + offsetX);
    }

    protected void this_onLayoutYChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        tip.setLayoutY(newValue.doubleValue() + offsetY);
    }

}
