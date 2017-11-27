/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.algoid.visualsource.shapes.InstructionNode;
import net.algoid.visualsource.shapes.SnapRegion;

/**
 *
 * @author cyann
 */
public class VisualSourcePlaceHolder extends Pane {

    public VisualSourcePlaceHolder() {
        this.setPickOnBounds(true);
    }

    public VisualSourcePlaceHolder(Node... children) {
        super(children);
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

}
