/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public class Hook extends Region implements HookQueryable {

    // inner
    public static class HookEvent extends Event {

        public static final EventType<HookEvent> OVER = new EventType<>("LINKABLE_OVER_HOOK");
        public static final EventType<HookEvent> OUT = new EventType<>("LINKABLE_OUT_HOOK");
        public static final EventType<HookEvent> PUT_IN = new EventType<>("LINKABLE_PUT_IN");
        public static final EventType<HookEvent> PUT_OUT = new EventType<>("LINKABLE_PUT_OUT");

        private final Hook hook;
        private final LinkableRegion region;

        public HookEvent(Hook hook, LinkableRegion region, EventType<? extends HookEvent> eventType) {
            super(hook, null, eventType);
            this.hook = hook;
            this.region = region;
        }

        public Hook getHook() {
            return hook;
        }

        public LinkableRegion getRegion() {
            return region;
        }

    }

    public enum Direction {
        none, horizontal, vertical
    }

    // attribute
    private final Hook.Direction direction;
    private final LinkableRegion parent;
    private final Bounds hookBoundsInLocal;

    // constructor
    public Hook(LinkableRegion parent, Direction direction) {
        this.direction = direction;
        this.parent = parent;
        this.hookBoundsInLocal = new BoundingBox(0, 0, 1, 1);
    }

    // accessor
    public LinkableRegion getParentLink() {
        return parent;
    }

    public Direction getDirection() {
        return direction;
    }

    public Bounds getHookBoundsInLocal() {
        return hookBoundsInLocal;
    }

    public void setChild(LinkableRegion child) {
        getChildren().clear();
        getChildren().add(child);
        fireEvent(new HookEvent(this, child, HookEvent.PUT_IN));
    }

    public LinkableRegion getChild() {
        return (LinkableRegion) getChildren().get(0);
    }

    public boolean hasChild() {
        return getChildren().size() == 1;
    }

    // method
    public void removeChild() {
        fireEvent(new HookEvent(this, getChild(), HookEvent.PUT_OUT));
        getChildren().clear();
    }

    // depth first search
    @Override
    public Hook queryHookIntersection(HangableRegion query) {
        if (hasChild()) {

            // chain of responcibility
            Hook found = getChild().queryHookIntersection(query);
            if (found != null) {
                return found;
            }
        }

        if (query.isIntersectHook(this)) {
            return this;
        }

        return null;
    }

    // event
    public void setOnOverEvent(EventHandler<HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(HookEvent.OVER, handler);
    }

    public void setOnOutEvent(EventHandler<HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(HookEvent.OUT, handler);
    }

}
