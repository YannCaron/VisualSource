/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.algoid.visualsource.shapes.InstructionPane;
import net.algoid.visualsource.shapes.SnapPane;

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

    public SnapPane queryRegionIntersecton(InstructionPane query) {
        return queryRegionIntersecton(query, this);
    }

    public SnapPane queryRegionIntersecton(InstructionPane query, Node node) {
        if (node instanceof Pane) {
            Pane pane = (Pane) node;
            for (Node child : pane.getChildren()) {
                if (child != query) {
                    if (child instanceof SnapPane && ((SnapPane) child).intersectsInstruction(query)) {
                        return (SnapPane) child;
                    } else {
                        SnapPane found = queryRegionIntersecton(query, child);
                        if (found != null) {
                            return found;
                        }
                    }
                }

            }
        }
        return null;
    }
}
