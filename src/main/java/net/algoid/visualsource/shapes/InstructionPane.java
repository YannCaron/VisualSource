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
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author cyann
 */
public class InstructionPane extends Pane {

    private final Bounds anchorBounds;
    private final List<SnapRegion> chainableSnap;

    public InstructionPane(double width, double height) {
        Bounds local = getBoundsInLocal();
        anchorBounds = new BoundingBox(local.getMinX(), local.getMinY(), width, height);
        chainableSnap = new ArrayList<>();
    }

    public final void createSnap(SnapRegion.Type type, double x, double y, boolean chainable) {
        SnapRegion args = new SnapRegion(type);
        getChildren().add(args);
        args.setLayoutX(x);
        args.setLayoutY(y);

        if (chainable) {
            chainableSnap.add(args);
        }
    }

    // depth first search
    public SnapRegion queryRegionIntersecton(InstructionPane query) {
        for (Node child : getChildren()) {
            if (child instanceof SnapRegion) {
                SnapRegion found = ((SnapRegion) child).queryRegionIntersecton(query);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    // chain of responsibility
    public SnapRegion findSnapOfType(SnapRegion.Type type) {
        for (SnapRegion snap : chainableSnap) {
            if (type == snap.getType()) {
                if (snap.containsInstruction()) {
                    return snap.getInstruction().findSnapOfType(type);
                }
                return snap;
            }
        }
        return null;
    }

    public Bounds getAnchorBoundsInLocal() {
        return anchorBounds;
    }

}
