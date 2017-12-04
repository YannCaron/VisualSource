/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.event.EventHandler;
import javafx.scene.layout.Region;
import net.algoid.visualsource.core.Hook.Direction;

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
    public abstract Hook queryHookIntersection(HoldableRegion query);

    // method
    public abstract void removeChild();

    public abstract void setChild(LinkableRegion child);

    // accessor
    public abstract void setParent(LinkableRegion parent);

    // event
    public void setOnOverEvent(EventHandler<Hook.HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(Hook.HookEvent.OVER, handler);
    }

    public void setOnOutEvent(EventHandler<Hook.HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(Hook.HookEvent.OUT, handler);
    }

    public void setOnHangEvent(EventHandler<Hook.HookEvent> handler) {
        addEventHandler(Hook.HookEvent.HANG, handler);
    }

    public void setOnReleaseEvent(EventHandler<Hook.HookEvent> handler) {
        addEventHandler(Hook.HookEvent.RELEASE, handler);
    }
}
