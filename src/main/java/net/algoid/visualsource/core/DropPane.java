/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

/**
 *
 * @author cyann
 */
public abstract class DropPane extends Pane {

    public DropPane() {
        this.setOnDragOver(this::this_onDragOver);
        this.setOnDragDropped(this::this_onDragDropped);

        Platform.runLater(this::initializeLayout);
    }

    protected abstract void initializeLayout();

    // event management
    protected void this_onDragOver(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    protected void this_onDragDropped(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            DragContext context = (DragContext) event.getDragboard().getContent(DragContext.HANDLE_FORMAT);
            HandleRegion region = (HandleRegion) event.getGestureSource();

            if (event.getAcceptedTransferMode() == TransferMode.COPY) {
                region = region.newInstance();
            }
            getChildren().add(region);

            Point2D pt = this.sceneToLocal(
                    event.getSceneX() - context.getOffsetX(),
                    event.getSceneY() - context.getOffserY());
            region.relocate(pt.getX(), pt.getY());

            event.consume();
        }
    }
}
