/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.coreMove;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author cyann
 */
public abstract class AbstractVisualSource extends Pane implements HookQueryable {

    private boolean anyHookHandeled;

    public AbstractVisualSource() {
        this.setPickOnBounds(true);
        anyHookHandeled = true;

        Platform.runLater(this::initialize);
    }

    // abstract
    protected abstract void initialize();

    // property
    boolean hasAnyHookHandeled() {
        return anyHookHandeled;
    }

    void activateHookHandeling() {
        anyHookHandeled = true;
    }

    // depth first search
    @Override
    public HookOld queryHookIntersection(HoldableRegion query) {
        for (Node child : getChildren()) {
            if (child != query && child instanceof HoldableRegion) {
                HookOld found = ((HoldableRegion) child).queryHookIntersection(query);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

}
