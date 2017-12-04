/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public abstract class LinkableRegion extends Region implements HookQueryable, RawSizeComputable {

    // inner
    public static class LinkableRegionEvent extends Event {

        public static final EventType<LinkableRegionEvent> OVER = new EventType<>("THIS_OVER_HOOK");
        public static final EventType<LinkableRegionEvent> OUT = new EventType<>("THIS_OUT_HOOK");
        public static final EventType<LinkableRegionEvent> DRAG_STARTED = new EventType<>("DRAG_STARTED");
        public static final EventType<LinkableRegionEvent> DRAG_STOPPED = new EventType<>("DRAG_STOPPED");

        private final LinkableRegion linkableRegion;

        public LinkableRegionEvent(LinkableRegion linkableRegion, EventType<? extends LinkableRegionEvent> eventType) {
            super(linkableRegion, null, eventType);
            this.linkableRegion = linkableRegion;
        }

        public LinkableRegion getLinkableRegion() {
            return linkableRegion;
        }

    }

    // attribut
    private final Pane graphic;
    protected final AbstractVisualSource placeHolder;
    private final Map<Hook.Direction, Hook> linkableHook;
    private final List<Hook> hooks;
    private AcceptationType typeOf;

    // constructor
    public LinkableRegion(AbstractVisualSource placeHolder) {
        this.placeHolder = placeHolder;
        linkableHook = new HashMap<>();
        hooks = new ArrayList<>();
        typeOf = AcceptationType.ALL;

        graphic = new Pane();
        getChildren().add(graphic);

        Platform.runLater(this::invalidate);
        Platform.runLater(this::initializeLayout);
    }

    // private
    public void invalidate() {
        graphic.getChildren().clear();
        graphic.getChildren().add(getGraphic());
    }

    // property
    public AcceptationType getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(AcceptationType ofType) {
        this.typeOf = ofType;
    }

    // abstract
    protected abstract Node getGraphic();

    protected abstract void initializeLayout();

    // property
    public AbstractVisualSource getPlaceHolder() {
        return placeHolder;
    }

    // chain of responsibility
    public LinkableRegion getHead() {
        if (getParent() instanceof Hook) {
            return ((Hook) getParent()).getParentLink().getHead();
        }
        return this;
    }

    // chain of responsibility
    public double computeRawHeight() {
        double height = getRawHeight();

        Hook linkHook = linkableHook.get(Hook.Direction.vertical);
        if (linkHook != null && linkHook.hasChild()) {
            height += linkHook.getChild().computeRawHeight();
        }

        return height;
    }

    public double computeRawWidth() {
        double width = getRawWidth();

        Hook linkHook = linkableHook.get(Hook.Direction.horizontal);
        if (linkHook != null && linkHook.hasChild()) {
            width += linkHook.getChild().computeRawWidth();
        }

        return width;
    }

    // method
    public final Hook addHook(Hook.Direction direction, boolean chainable) {
        Hook hook = new Hook(this, direction);
        addHook(hook, chainable);
        return hook;
    }

    public final void addHook(Hook hook, boolean linkable) {
        hook.setParent(this);
        getChildren().add(hook);
        hooks.add(hook);

        if (linkable) {
            linkableHook.put(hook.getDirection(), hook);
        }

        hook.toFront();
    }

    @Override
    public Hook queryHookIntersection(HoldableRegion query) {
        // Depth first search
        for (Node child : getChildren()) {
            if (child instanceof Hook) {
                Hook found = ((Hook) child).queryHookIntersection(query);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    public Hook findChainableHook(Hook.Direction direction) {
        // hash tree depth first search
        Hook hook = linkableHook.get(direction);

        if (hook != null && hook.hasChild()) {
            return hook.getChild().findChainableHook(direction);
        }

        return hook;
    }

    // event
    public void setOnOverEvent(EventHandler<LinkableRegionEvent> handler) {
        addEventHandler(LinkableRegionEvent.OVER, handler);
    }

    public void setOnOutEvent(EventHandler<LinkableRegionEvent> handler) {
        addEventHandler(LinkableRegionEvent.OUT, handler);
    }

    public void setOnDragStartedEvent(EventHandler<LinkableRegionEvent> handler) {
        addEventHandler(LinkableRegionEvent.DRAG_STARTED, handler);
    }

    public void setOnDragStoppedEvent(EventHandler<LinkableRegionEvent> handler) {
        addEventHandler(LinkableRegionEvent.DRAG_STOPPED, handler);
    }
}
