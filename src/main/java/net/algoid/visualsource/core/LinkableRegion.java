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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author cyann
 */
public abstract class LinkableRegion extends Region implements HookQueryable {

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
    private final Map<Hook.Direction, Hook> chainableHook;
    private final List<Hook> hooks;

    // constructor
    public LinkableRegion(AbstractVisualSource placeHolder) {
        this.placeHolder = placeHolder;
        chainableHook = new HashMap<>();
        hooks = new ArrayList<>();

        visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("Visibility changed !");
            }
        });
        
        //getChildren().add(new Rectangle(100, 100, Color.ANTIQUEWHITE));
        graphic = new Pane();
        getChildren().add(graphic);
        
        Platform.runLater(this::applyGraphic);
    }
    
    // private
    private void applyGraphic() {
        graphic.getChildren().clear();
        graphic.getChildren().add(getGraphic());
    }
    
    // abstract
    protected abstract Node getGraphic();

    // property
    // chain of responsibility
    public LinkableRegion getHead() {
        if (getParent() instanceof Hook) {
            return ((Hook) getParent()).getParentLink().getHead();
        }
        return this;
    }

    public AbstractVisualSource getPlaceHolder() {
        return placeHolder;
    }

    public final Hook addHook(Hook.Direction direction, double x, double y, boolean chainable) {
        Hook hook = new Hook(this, direction);
        getChildren().add(hook);
        hooks.add(hook);
        hook.setLayoutX(x);
        hook.setLayoutY(y);

        if (chainable) {
            chainableHook.put(hook.getDirection(), hook);
        }
        
        hook.toBack();
        return hook;
    }

    // method
    @Override
    public Hook queryHookIntersection(HangableRegion query) {
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
        Hook hook = chainableHook.get(direction);

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
