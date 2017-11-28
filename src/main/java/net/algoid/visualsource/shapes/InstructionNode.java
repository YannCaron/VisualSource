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
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import net.algoid.visualsource.VisualSourcePlaceHolder;

/**
 *
 * @author cyann
 */
public class InstructionNode extends Region {

    private final VisualSourcePlaceHolder placeHolder;
    private final Canvas canvas;

    private final Effect moveEffect;

    private double currentDeltaX = 0;
    private double currentDeltaY = 0;

    private final List<SnapRegion> chainableSnap;
    private final Bounds initialBoundsInLocal;

    // constructor
    public InstructionNode(VisualSourcePlaceHolder placeHolder, double width, double height) {
        this.placeHolder = placeHolder;
        chainableSnap = new ArrayList<>();
        initialBoundsInLocal = new BoundingBox(0, 0, width, height);

        // create effects
        moveEffect = new GaussianBlur();

        // create canvas
        canvas = new Canvas(100, 100);
        getChildren().add(canvas);

        // event management
        setOnMousePressed(this::this_onMousePresser);

        setOnMouseDragged(this::this_onMouseDragged);

        setOnMouseReleased(this::this_onMouseReleased);

    }

    // accessor
    public Canvas getCanvas() {
        return canvas;
    }

    public Bounds getInitialBoundsInLocal() {
        return initialBoundsInLocal;
    }

    // private
    private void applyMoveEffect() {
        setEffect(moveEffect);
    }

    private void removeMoveEffect() {
        setEffect(null);
        setOpacity(1);
        if (currentRegion != null) {
            currentRegion.hideArea();
        }

    }

    private double limitValue(double v, double size, double max) {
        if (v < 0) {
            return 0;
        }
        if (v + size >= max) {
            return max - size;
        }

        return v;
    }

    // method
    protected final void addSnap(SnapRegion snap, double x, double y, boolean chainable) {
        getChildren().add(snap);
        snap.setLayoutX(x);
        snap.setLayoutY(y);

        if (chainable) {
            chainableSnap.add(snap);
        }
    }

    // depth first search
    public SnapRegion queryRegionIntersection(InstructionNode query) {
        for (Node child : getChildren()) {
            if (child instanceof SnapRegion) {
                SnapRegion found = ((SnapRegion) child).queryRegionIntersection(query);
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

    // chain of responsibility
    public InstructionNode findFirstInstruction() {
        if (getParent() instanceof SnapRegion) {
            return ((SnapRegion) getParent()).getParentInstruction().findFirstInstruction();
        }
        return this;
    }

    // event handling
    protected void this_onMousePresser(MouseEvent event) {
        currentDeltaX = event.getX();
        currentDeltaY = event.getY();

        if (getParent() instanceof SnapRegion) {
            ((SnapRegion) getParent()).removeInstruction();
            placeHolder.getChildren().add(this);
            this_onMouseDragged(event);
        }

        this.toFront();
        applyMoveEffect();
        event.consume();

    }

    private SnapRegion currentRegion;

    protected void this_onMouseDragged(MouseEvent event) {
        Point2D pt = placeHolder.sceneToLocal(event.getSceneX(), event.getSceneY());
        double x = pt.getX() - currentDeltaX;
        double y = pt.getY() - currentDeltaY;

        x = limitValue(x, getBoundsInLocal().getWidth(), placeHolder.getBoundsInLocal().getWidth());
        //y = limitValue(y, getBoundsInLocal().getHeight(), placeHolder.getBoundsInLocal().getHeight());

        setLayoutX(x);
        setLayoutY(y);

        SnapRegion snap = placeHolder.queryRegionIntersection(this);
        if (snap != currentRegion) {
            if (currentRegion != null) {
                currentRegion.hideArea();
            }

            if (snap != null) {
                setOpacity(0.75);
                snap.showArea();
            } else if (currentRegion != null) {
                setOpacity(1);
            }

            currentRegion = snap;
        }
    }

    protected void this_onMouseReleased(MouseEvent event) {
        removeMoveEffect();

        SnapRegion snap = placeHolder.queryRegionIntersection(this);

        // snap
        if (snap != null) {
            setLayoutX(0);
            setLayoutY(0);

            if (snap.containsInstruction()) {
                InstructionNode existingChild = snap.getInstruction();
                Bounds childBounds = snap.getParent().localToParent(existingChild.getBoundsInLocal());
                snap.removeInstruction();

                SnapRegion localSnap = findSnapOfType(snap.getType());
                if (localSnap != null) {
                    localSnap.setInstruction(existingChild);
                } else {
                    placeHolder.getChildren().add(existingChild);
                    existingChild.setLayoutX(childBounds.getMinX() + childBounds.getWidth() / 2);
                    existingChild.setLayoutY(childBounds.getMinY() + childBounds.getHeight() / 2);
                }
            }
            snap.setInstruction(this);
            findFirstInstruction().toFront();
        }
    }

}
