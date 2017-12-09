/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public abstract class Hook extends Region implements RawSizeQueryable {

    // inner
    public static class HookEvent extends Event {

        public static final EventType<HookEvent> HANG = new EventType<>("LINKABLE_HANG");
        public static final EventType<HookEvent> RELEASE = new EventType<>("LINKABLE_RELEASE");

        private final Hook hook;
        private final HandleRegion region;

        public HookEvent(Hook hook, HandleRegion region, EventType<? extends HookEvent> eventType) {
            super(hook, null, eventType);
            this.hook = hook;
            this.region = region;
        }

        public Hook getHook() {
            return hook;
        }

        public HandleRegion getRegion() {
            return region;
        }

    }

    private final HoldType holdType;
    private Node tip;
    private final Pane tipContainer;
    private HandleRegion child;

    // constructor    
    public Hook(HoldType holdType) {

        this.holdType = holdType;

        this.tipContainer = new Pane();
        this.getChildren().add(tipContainer);
        tipContainer.toBack();

        Platform.runLater(this::invalidateTip);

        this.setOnDragEntered(this::this_onDragEntered);
        this.setOnDragExited(this::this_onDragExited);

        tipContainer.setOnDragOver(this::tip_onDragOver);
        tipContainer.setOnDragDropped(this::tip_onDragDropped);

        tipContainer.setOnDragEntered(this::tip_onDragEntered);
        tipContainer.setOnDragExited(this::tip_onDragExited);

    }

    //abstract
    protected abstract Node createTip();

    protected void invalidateTip() {
        tip = createTip();
        tipContainer.getChildren().add(tip);
        tipContainer.setPrefSize(tip.getBoundsInLocal().getWidth(), tip.getBoundsInLocal().getHeight());
        hideTip();
    }

    // accessor
    public HoldType getHoldType() {
        return holdType;
    }

    public HandleRegion getChild() {
        return child;
    }

    // methods
    public void showTip() {
        tip.setVisible(true);
    }

    public void hideTip() {
        tip.setVisible(false);
    }

    public void setChild(HandleRegion child) {
        this.getChildren().remove(this.child);
        this.getChildren().add(child);
        child.relocate(0, 0);
        this.child = child;

        fireEvent(new Hook.HookEvent(this, child, Hook.HookEvent.HANG));
    }

    public boolean hasChild() {
        return this.child != null;
    }

    public void removeChild() {
        this.getChildren().remove(child);
        fireEvent(new HookEvent(this, child, HookEvent.RELEASE));
        child = null;
    }

    // implement
    @Override
    public double getRawHeight() {
        double height = 0;
        if (hasChild()) {
            height += child.getRawHeight();
        }
        return height;
    }

    @Override
    public double getRawWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // event management
    protected void this_onDragEntered(DragEvent event) {
        tipContainer.toFront();
    }

    protected void this_onDragExited(DragEvent event) {
        tipContainer.toBack();
    }

    protected void tip_onDragEntered(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {

            HandleRegion region = (HandleRegion) event.getGestureSource();
            if (holdType.accept(region.getHoldType()) || region.getHoldType().accept(holdType)) {
                showTip();
                event.consume();

            }
        }
    }

    protected void tip_onDragExited(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            HandleRegion region = (HandleRegion) event.getGestureSource();
            if (holdType.accept(region.getHoldType()) || region.getHoldType().accept(holdType)) {
                hideTip();
                event.consume();
            }
        }
    }

    protected void tip_onDragOver(DragEvent event) {
        // TODO match holdType
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    protected void tip_onDragDropped(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            HandleRegion region = (HandleRegion) event.getGestureSource();

            if (holdType.accept(region.getHoldType()) || region.getHoldType().accept(holdType)) {

                HandleRegion previous = child;

                if (event.getAcceptedTransferMode() == TransferMode.COPY) {
                    region = region.newInstance();
                }

                setChild(region);

                if (previous != null) {
                    region.getHookTail(holdType).setChild(previous);
                }

                event.consume();
            }
        }
    }

    // event register
    public final void setOnHangEvent(EventHandler<HookEvent> handler) {
        addEventHandler(HookEvent.HANG, handler);
    }

    public final void setOnReleaseEvent(EventHandler<HookEvent> handler) {
        addEventHandler(HookEvent.RELEASE, handler);
    }
}
