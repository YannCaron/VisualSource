/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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

    // constructor
    public InstructionNode(VisualSourcePlaceHolder placeHolder, double width, double height) {
        this.placeHolder = placeHolder;
        chainableSnap = new ArrayList<>();

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

    // method
    protected final void createSnap(SnapRegion.Type type, double x, double y, boolean chainable) {
        SnapRegion args = new SnapRegion(this, type);
        getChildren().add(args);
        args.setLayoutX(x);
        args.setLayoutY(y);

        if (chainable) {
            chainableSnap.add(args);
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

    private void applyMoveEffect() {
        setEffect(moveEffect);
    }

    private void removeMoveEffect() {
        setEffect(null);
    }

    private double bound(double v, double size, double max) {
        if (v < 0) {
            return 0;
        }
        if (v + size >= max) {
            return max - size;
        }

        return v;
    }

    protected void this_onMouseDragged(MouseEvent event) {
        Point2D pt = placeHolder.sceneToLocal(event.getSceneX(), event.getSceneY());
        double x = pt.getX() - currentDeltaX;
        double y = pt.getY() - currentDeltaY;

        x = bound(x, getBoundsInLocal().getWidth(), getParent().getBoundsInLocal().getWidth());
        y = bound(y, getBoundsInLocal().getHeight(), getParent().getBoundsInLocal().getHeight());

        setLayoutX(x);
        setLayoutY(y);

        SnapRegion snap = placeHolder.queryRegionIntersection(this);
        if (snap != null) {
            setOpacity(0.75);
        } else {
            setOpacity(1);
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
