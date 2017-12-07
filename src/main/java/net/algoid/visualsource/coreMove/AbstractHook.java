/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.coreMove;

import javafx.event.EventHandler;
import javafx.scene.layout.Region;
import net.algoid.visualsource.coreMove.HookOld.Direction;

/**
 *
 * @author cyann
 */
public abstract class AbstractHook extends Region implements HookQueryable {

    public abstract LinkableRegion getChild();

    public abstract Direction getDirection();

    public abstract LinkableRegion getParentLink();

    public abstract boolean hasChild();

    // depth first search
    @Override
    public abstract HookOld queryHookIntersection(HoldableRegion query);

    // method
    public abstract void removeChild();

    public abstract void setChild(LinkableRegion child);

    // accessor
    public abstract void setParent(LinkableRegion parent);

    // event
    public void setOnOverEvent(EventHandler<HookOld.HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(HookOld.HookEvent.OVER, handler);
    }

    public void setOnOutEvent(EventHandler<HookOld.HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(HookOld.HookEvent.OUT, handler);
    }

    public void setOnHangEvent(EventHandler<HookOld.HookEvent> handler) {
        addEventHandler(HookOld.HookEvent.HANG, handler);
    }

    public void setOnReleaseEvent(EventHandler<HookOld.HookEvent> handler) {
        addEventHandler(HookOld.HookEvent.RELEASE, handler);
    }
}
