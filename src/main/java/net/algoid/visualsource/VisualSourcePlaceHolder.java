/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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

    public Pane queryRegionIntersecton(Node except) {
        return queryRegionIntersecton(except, this);
    }

    public Pane queryRegionIntersecton(Node query, Node node) {
        if (node instanceof Pane) {
            Pane pane = (Pane) node;
            for (Node child : pane.getChildren()) {
                if (child != query) {
                    if (child instanceof SnapPane) {
                        Bounds queryInScene = query.localToScene(query.getBoundsInLocal());
                        Bounds childInScene = child.localToScene(new BoundingBox(child.getBoundsInLocal().getMinX(), child.getBoundsInLocal().getMinY(), 10, 10));

                        if (queryInScene.intersects(childInScene)) {
                            return (Pane) child;
                        } else {
                            Pane found = queryRegionIntersecton(query, child);
                            if (found != null) {
                                return found;
                            }
                        }

                    } else {
                        Pane found = queryRegionIntersecton(query, child);
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
