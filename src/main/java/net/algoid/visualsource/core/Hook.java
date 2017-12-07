/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public abstract class Hook extends Region {

    private final HoldType type;
    private final Node tip;
    private final Pane tipContainer;
    private Node child;

    public Hook(HoldType type) {
        this.type = type;

        this.tipContainer = new Pane();
        this.getChildren().add(tipContainer);

        tipContainer.toFront();

        tip = createTip();
        tipContainer.getChildren().add(tip);
        tipContainer.setPrefSize(tip.getBoundsInLocal().getWidth(), tip.getBoundsInLocal().getHeight());
        hideTip();

        tipContainer.setPickOnBounds(false);
        tipContainer.setOnDragOver(this::tip_onDragOver);
        tipContainer.setOnDragDropped(this::tip_onDragDropped);

        tipContainer.setOnDragEntered(this::tip_onDragEntered);
        tipContainer.setOnDragExited(this::tip_onDragExited);

    }

    protected abstract Node createTip();

    public void showTip() {
        tip.setVisible(true);
    }

    public void hideTip() {
        tip.setVisible(false);
    }

    public void setChild(Node child) {
        removeChild();
        this.getChildren().add(child);
        child.relocate(0, 0);
        tipContainer.toFront();
        this.child = child;
    }

    public void removeChild() {
        this.getChildren().remove(child);
    }

    // event management
    protected void tip_onDragEntered(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            showTip();
            event.consume();
        }
    }

    protected void tip_onDragExited(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            hideTip();
            event.consume();
        }
    }

    protected void tip_onDragOver(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    protected void tip_onDragDropped(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            // DragContext context = (DragContext) event.getDragboard().getContent(DragContext.HANDLE_FORMAT);
            HandleRegion region = (HandleRegion) event.getGestureSource();

            if (event.getAcceptedTransferMode() == TransferMode.COPY) {
                HandleRegion newInstance = region.newInstance();
                setChild(newInstance);
            } else if (event.getAcceptedTransferMode() == TransferMode.MOVE) {
                setChild(region);
            }
            
            event.consume();
        }
    }

}
