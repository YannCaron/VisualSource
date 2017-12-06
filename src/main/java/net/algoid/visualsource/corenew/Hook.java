/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.corenew;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author cyann
 */
public class Hook extends Region {

    private final HoldType type;
    private final Node tip = new Rectangle(25, 25, Color.GRAY);
    private final Bounds hitBounds;

    public Hook(HoldType type, double width, double height) {
        this.type = type;
        this.hitBounds = new BoundingBox(0, 0, width, height);

        getChildren().add(tip);
    }

    public void setChild(Node node) {
        getChildren().clear();
        getChildren().add(node);
        node.relocate(0, 0);
    }

    public void removeChild() {
        getChildren().clear();
        getChildren().add(tip);
    }

    public boolean isHit(double x, double y) {
        return hitBounds.contains(this.sceneToLocal(x, y));
    }

}
