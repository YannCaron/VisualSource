/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.coreMove;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 *
 * @author cyann
 */
public abstract class DraggableRegion extends LinkableRegion {

    private final Bounds dragBoundsInLocal;
    private double currentDeltaX = 0;
    private double currentDeltaY = 0;
    private boolean dragAccept;

    // constructor
    public DraggableRegion(AbstractVisualSource placeHolder, Bounds dragBoundsInLocal) {
        super(placeHolder);
        this.dragBoundsInLocal = dragBoundsInLocal;
        this.dragAccept = false;

        // event management
        setOnMousePressed(this::this_onMousePresser);

        setOnMouseDragged(this::this_onMouseDragged);

        setOnMouseReleased(this::this_onMouseReleased);

    }

    private double limitX(double x) {
        double borderLeft = getPlaceHolder().getPadding().getLeft();
        double borderRight = getPlaceHolder().getPadding().getRight();
        double phWidth = getPlaceHolder().getWidth();
        if (x < borderLeft) {
            return borderLeft;
        }
        if (x + getWidth() >= phWidth + borderRight) {
            return phWidth - borderRight - getWidth();
        }

        return x;
    }

    private double limitY(double y) {
        double borderTop = getPlaceHolder().getPadding().getTop();
        double borderBottom = getPlaceHolder().getPadding().getBottom();
        double phHeight = getPlaceHolder().getHeight();
        if (y < borderTop) {
            return borderTop;
        }
        if (y + getHeight() >= phHeight + borderBottom) {
            return phHeight - borderBottom - getHeight();
        }

        return y;
    }

    // abstract
    // event handling
    protected void this_onMousePresser(MouseEvent event) {
        dragAccept = dragBoundsInLocal.contains(event.getX(), event.getY());
        //Shape.intersect(shape1, shape2)

        if (dragAccept) {

            currentDeltaX = event.getX();
            currentDeltaY = event.getY();

            if (getParent() instanceof HookOld) {
                ((HookOld) getParent()).removeChild();
                placeHolder.getChildren().add(this);
                this_onMouseDragged(event);
            }

            this.toFront();
            this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.DRAG_STARTED));
            event.consume();
        }

    }

    protected void this_onMouseDragged(MouseEvent event) {
        if (dragAccept) {
            Point2D pt = placeHolder.sceneToLocal(event.getSceneX(), event.getSceneY());
            double x = pt.getX() - currentDeltaX;
            double y = pt.getY() - currentDeltaY;

            setLayoutX(limitX(x));
            setLayoutY(limitY(y));
        }
    }

    protected void this_onMouseReleased(MouseEvent event) {
        if (dragAccept) {
            this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.DRAG_STOPPED));
        }
    }

}
