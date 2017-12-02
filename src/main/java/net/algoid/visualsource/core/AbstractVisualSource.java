/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.algoid.visualsource.shapes.InstructionNode;
import net.algoid.visualsource.shapes.SnapRegion;

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
    public SnapRegion queryRegionIntersection(InstructionNode query) {
        for (Node child : getChildren()) {
            if (child != query && child instanceof InstructionNode) {
                SnapRegion found = ((InstructionNode) child).queryRegionIntersection(query);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    @Override
    public Hook queryHookIntersection(HangableRegion query) {
        for (Node child : getChildren()) {
            if (child != query && child instanceof HangableRegion) {
                Hook found = ((HangableRegion) child).queryHookIntersection(query);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

}