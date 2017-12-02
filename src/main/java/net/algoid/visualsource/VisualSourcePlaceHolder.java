/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.algoid.visualsource.shapes.InstructionNode;
import net.algoid.visualsource.shapes.SnapRegion;

/**
 *
 * @author cyann
 */
public class VisualSourcePlaceHolder extends Pane implements HookQueryable {

    private boolean anyHookHandeled;

    public VisualSourcePlaceHolder() {
        this.setPickOnBounds(true);
        initialize();
        anyHookHandeled = true;
    }

    public VisualSourcePlaceHolder(Node... children) {
        super(children);
        initialize();
    }

    private final void initialize() {
        getStylesheets().add(getClass().getResource("/styles/visual-source.css").toExternalForm());
    }

    // property
    public boolean hasAnyHookHandeled() {
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
