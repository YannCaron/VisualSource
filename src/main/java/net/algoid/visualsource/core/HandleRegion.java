/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public abstract class HandleRegion extends Region {

    // attribut
    private final HoldType holdType;
    private final Bounds initialBoundsInLocal;
    private final Map<HoldType, Hook> linkedHooks;

    // constructor
    public HandleRegion(HoldType holdType) {
        linkedHooks = new HashMap<>();

        Node graphic = createGraphic();
        initialBoundsInLocal = graphic.getBoundsInLocal();
        this.getChildren().add(graphic);
        this.holdType = holdType;

        Platform.runLater(this::applyLayout);
    }

    // abstract
    public abstract HandleRegion newInstance();

    public abstract Node createGraphic();

    public abstract void applyLayout();

    // accessor
    public HoldType getHoldType() {
        return holdType;
    }

    public Bounds getInitialBoundsInLocal() {
        return initialBoundsInLocal;
    }

    private Hook findHook(HoldType holdType) {
        return linkedHooks.get(holdType);
    }

    public Hook getHookTail(HoldType holdType) {
        Hook hook = findHook(holdType);
        if (hook != null) {
            HandleRegion child = hook.getChild();
            if (child != null) {
                return child.getHookTail(holdType);
            }
        }
        return hook;
    }

    // method
    public void addHook(Hook hook) {
        getChildren().add(hook);
    }

    public void addLinkedHook(Hook hook) {
        addHook(hook);
        linkedHooks.put(hook.getHoldType(), hook);
    }

    public void registerHookForLayout(Hook hook) {
        hook.setOnHangEvent(this::any_onAnyHookEvent);
        hook.setOnReleaseEvent(this::any_onAnyHookEvent);
    }

    // event handling
    private void any_onAnyHookEvent(Hook.HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

}
