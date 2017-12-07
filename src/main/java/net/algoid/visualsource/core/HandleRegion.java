/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public abstract class HandleRegion extends Region {

    private final HoldType holdType;
    private final Bounds initialBoundsInLocal;

    public HandleRegion(HoldType holdType) {
        Node graphic = createGraphic();
        initialBoundsInLocal = graphic.getBoundsInLocal();
        this.getChildren().add(graphic);
        this.holdType = holdType;

        Platform.runLater(this::applyLayout);
    }

    public abstract HandleRegion newInstance();

    public abstract Node createGraphic();

    public abstract void applyLayout();

    public Bounds getInitialBoundsInLocal() {
        return initialBoundsInLocal;
    }

    public Hook addHook(Hook hook) {
        getChildren().add(hook);
        return hook;
    }
}
