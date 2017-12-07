/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.coreMove;

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
public class HookOld extends Region implements HookQueryable, RawSizeComputable {

    // inner
    public static class HookEvent extends Event {

        public static final EventType<HookEvent> OVER = new EventType<>("LINKABLE_OVER_HOOK");
        public static final EventType<HookEvent> OUT = new EventType<>("LINKABLE_OUT_HOOK");
        public static final EventType<HookEvent> HANG = new EventType<>("LINKABLE_HANG");
        public static final EventType<HookEvent> RELEASE = new EventType<>("LINKABLE_RELEASE");

        private final HookOld hook;
        private final LinkableRegion region;

        public HookEvent(HookOld hook, LinkableRegion region, EventType<? extends HookEvent> eventType) {
            super(hook, null, eventType);
            this.hook = hook;
            this.region = region;
        }

        public HookOld getHook() {
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
    private final HookOld.Direction direction;
    private LinkableRegion parent;
    private final Bounds hookBoundsInLocal;
    private AcceptationType acceptType;

    // constructor
    public HookOld(LinkableRegion parent, Direction direction) {
        this.direction = direction;
        this.parent = parent;
        this.hookBoundsInLocal = new BoundingBox(0, 0, 1, 1);
        acceptType = AcceptationType.ALL;
    }

    public HookOld(LinkableRegion parent, Direction direction, Bounds hookBoundsInLocal) {
        this.direction = direction;
        this.parent = parent;
        this.hookBoundsInLocal = hookBoundsInLocal;
        acceptType = AcceptationType.ALL;
    }

    // accessor
    public void setParent(LinkableRegion parent) {
        this.parent = parent;
    }

    public LinkableRegion getParentLink() {
        return parent;
    }

    public Direction getDirection() {
        return direction;
    }

    public AcceptationType getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(AcceptationType acceptType) {
        this.acceptType = acceptType;
    }

    public Bounds getHookBoundsInLocal() {
        return hookBoundsInLocal;
    }

    public void setChild(LinkableRegion child) {
        getChildren().clear();
        getChildren().add(child);
        fireEvent(new HookEvent(this, child, HookEvent.HANG));
    }

    public LinkableRegion getChild() {
        if (hasChild()) {
            return (LinkableRegion) getChildren().get(0);
        }
        return null;
    }

    public boolean hasChild() {
        return getChildren().size() == 1;
    }

    @Override
    public double getRawHeight() {
        if (hasChild()) {
            return getChild().computeRawHeight();
        }
        return 0;
    }

    @Override
    public double getRawWidth() {
        if (hasChild()) {
            return getChild().computeRawWidth();
        }
        return 0;
    }

    // method
    public void removeChild() {
        LinkableRegion released = getChild();
        getChildren().clear();
        fireEvent(new HookEvent(this, released, HookEvent.RELEASE));
    }

    // depth first search
    @Override
    public HookOld queryHookIntersection(HoldableRegion query) {
        if (hasChild()) {

            // chain of responcibility
            HookOld found = getChild().queryHookIntersection(query);
            if (found != null) {
                return found;
            }
        }

        if (query.isIntersectHook(this) && getAcceptType().match(query.getTypeOf())) {
            return this;
        }

        return null;
    }

    // event
    public final void setOnOverEvent(EventHandler<HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(HookEvent.OVER, handler);
    }

    public final void setOnOutEvent(EventHandler<HookEvent> handler) {
        getParentLink().getPlaceHolder().activateHookHandeling();
        addEventHandler(HookEvent.OUT, handler);
    }

    public final void setOnHangEvent(EventHandler<HookEvent> handler) {
        addEventHandler(HookEvent.HANG, handler);
    }

    public final void setOnReleaseEvent(EventHandler<HookEvent> handler) {
        addEventHandler(HookEvent.RELEASE, handler);
    }
}
