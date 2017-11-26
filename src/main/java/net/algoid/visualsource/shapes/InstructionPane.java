/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

/**
 *
 * @author cyann
 */
public class InstructionPane extends Pane {

    private final Bounds anchorBounds;
    private final List<SnapPane> snaps;

    public InstructionPane(double width, double height) {
        Bounds local = getBoundsInLocal();
        anchorBounds = new BoundingBox(local.getMinX(), local.getMinY(), width, height);
        snaps = new ArrayList<>();
    }

    public final void createSnap(SnapPane.Type type, double x, double y) {
        SnapPane args = new SnapPane(type);
        getChildren().add(args);
        args.setLayoutX(x);
        args.setLayoutY(y);
        snaps.add(args);
    }

    public SnapPane findSnapOfType(SnapPane.Type type) {
        for (SnapPane snap : snaps) {
            if (type == snap.getType()) {
                return snap;
            }
        }
        return null;
    }

    public Bounds getAnchorBoundsInLocal() {
        return anchorBounds;
    }

}
