/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author cyann
 */
public abstract class DraggableRegion extends LinkableRegion {

    private double currentDeltaX = 0;
    private double currentDeltaY = 0;

    // constructor
    public DraggableRegion(VisualSourcePlaceHolder placeHolder) {
        super(placeHolder);

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
            return phHeight - borderBottom - getWidth();
        }

        return y;
    }

    // abstract
    // event handling
    protected void this_onMousePresser(MouseEvent event) {
        currentDeltaX = event.getX();
        currentDeltaY = event.getY();

        if (getParent() instanceof Hook) {
            ((Hook) getParent()).removeChild();
            placeHolder.getChildren().add(this);
            this_onMouseDragged(event);
        }

        this.toFront();
        this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.DRAG_STARTED));
        event.consume();

    }

    protected void this_onMouseDragged(MouseEvent event) {
        Point2D pt = placeHolder.sceneToLocal(event.getSceneX(), event.getSceneY());
        double x = pt.getX() - currentDeltaX;
        double y = pt.getY() - currentDeltaY;

        setLayoutX(limitX(x));
        setLayoutY(limitY(y));
    }

    protected void this_onMouseReleased(MouseEvent event) {
        this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.DRAG_STOPPED));
    }

}
