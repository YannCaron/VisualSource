/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import static net.algoid.visualsource.Hook.HookEvent.OUT;

/**
 *
 * @author cyann
 */
public abstract class HangableRegion extends DraggableRegion {

    // attribute
    private final Bounds hangBoundsInLocal;
    private Hook previousHook = null;

    // constructor
    public HangableRegion(VisualSourcePlaceHolder placeHolder, Bounds hangBounds) {
        super(placeHolder);
        this.hangBoundsInLocal = hangBounds;

    }

    // property
    public boolean isIntersectHook(Hook hook) {
        Bounds queryInScene = this.localToScene(this.hangBoundsInLocal);
        Bounds thisInScene = hook.localToScene(hook.getHookBoundsInLocal());
        return thisInScene.intersects(queryInScene);
    }

    // event handling
    @Override
    protected void this_onMouseDragged(MouseEvent event) {
        super.this_onMouseDragged(event);
        if (placeHolder.hasAnyHookHandeled()) {
            Hook hook = placeHolder.queryHookIntersection(this);
            if (hook != previousHook) {
                if (previousHook != null) {
                    previousHook.fireEvent(new Hook.HookEvent(previousHook, OUT));
                }

                if (hook != null) {
                    this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.OVER));
                    hook.fireEvent(new Hook.HookEvent(hook, Hook.HookEvent.OVER));
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

        Hook hook = placeHolder.queryHookIntersection(this);

        if (hook != null) {
            setLayoutX(0);
            setLayoutY(0);

            if (hook.hasChild()) {
                shiftChild(hook);
            }

            hook.setChild(this);
            getHead().toFront();
        }

        if (placeHolder.hasAnyHookHandeled()) {

            this.fireEvent(new LinkableRegionEvent(this, LinkableRegionEvent.OUT));

            if (previousHook != null) {
                previousHook.fireEvent(new Hook.HookEvent(previousHook, Hook.HookEvent.OUT));
            }
        }
    }

    private void shiftChild(Hook hook) {
        LinkableRegion existingChild = hook.getChild();
        Bounds childBounds = hook.getParentLink().localToParent(existingChild.getBoundsInLocal());
        hook.removeChild();

        Hook tailHook = findChainableHook(hook.getDirection());
        if (tailHook != null) {
            tailHook.setChild(existingChild);
        } else {
            placeHolder.getChildren().add(existingChild);
            existingChild.setLayoutX(childBounds.getMinX() + childBounds.getWidth() / 2);
            existingChild.setLayoutY(childBounds.getMinY() + childBounds.getHeight() / 2);
        }

    }
}
