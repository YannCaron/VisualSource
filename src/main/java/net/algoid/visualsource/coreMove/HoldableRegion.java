/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.coreMove;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import static net.algoid.visualsource.coreMove.HookOld.HookEvent.OUT;

/**
 *
 * @author cyann
 */
public abstract class HoldableRegion extends DraggableRegion {

    // attribute
    private final Bounds holdBoundsInLocal;
    private HookOld previousHook = null;

    // constructor
    public HoldableRegion(AbstractVisualSource placeHolder, Bounds dragBounds, Bounds holdBounds) {
        super(placeHolder, dragBounds);
        this.holdBoundsInLocal = holdBounds;
    }

    // property
    public boolean isIntersectHook(HookOld hook) {
        Bounds queryInScene = this.localToScene(this.holdBoundsInLocal);
        Bounds thisInScene = hook.localToScene(hook.getHookBoundsInLocal());
        return thisInScene.intersects(queryInScene);
    }


    // event handling
    @Override
    protected void this_onMouseDragged(MouseEvent event) {
            super.this_onMouseDragged(event);
            if (placeHolder.hasAnyHookHandeled()) {
                HookOld hook = placeHolder.queryHookIntersection(this);
                if (hook != previousHook) {
                    if (previousHook != null) {
                        previousHook.fireEvent(new HookOld.HookEvent(previousHook, this, OUT));
                    }

                    if (hook != null) {
                        this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.OVER));
                        hook.fireEvent(new HookOld.HookEvent(hook, this, HookOld.HookEvent.OVER));
                    } else if (previousHook != null) {
                        this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.OUT));
                    }

                    previousHook = hook;
                }
            }
    }

    @Override
    protected void this_onMouseReleased(MouseEvent event) {

            super.this_onMouseReleased(event);

            HookOld hook = placeHolder.queryHookIntersection(this);

            if (hook != null) {
                relocate(0, 0);

                if (hook.hasChild()) {
                    shiftChild(hook);
                }

                hook.setChild(this);
                getHead().toFront();
            }

            if (placeHolder.hasAnyHookHandeled()) {

                this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.OUT));

                if (previousHook != null) {
                    previousHook.fireEvent(new HookOld.HookEvent(previousHook, this, HookOld.HookEvent.OUT));
                }
            }
    }

    private void shiftChild(HookOld hook) {
        LinkableRegion existingChild = hook.getChild();
        Bounds childBounds = hook.getParentLink().localToParent(existingChild.getBoundsInLocal());
        hook.removeChild();

        HookOld tailHook = findChainableHook(hook.getDirection());
        if (tailHook != null) {
            tailHook.setChild(existingChild);
        } else if (existingChild instanceof DraggableRegion) {
            placeHolder.getChildren().add(existingChild);
            existingChild.setLayoutX(childBounds.getMinX() + childBounds.getWidth() / 2);
            existingChild.setLayoutY(childBounds.getMinY() + childBounds.getHeight() / 2);
        }

    }
}
