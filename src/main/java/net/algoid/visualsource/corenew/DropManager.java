/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.corenew;

import javafx.geometry.Point2D;
import javafx.scene.effect.Glow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import net.algoid.visualsource.VisualSourcePane;

/**
 *
 * @author cyann
 */
public class DropManager {

    private final Region dropRegion;

    public DropManager(Region dropRegion) {
        this.dropRegion = dropRegion;

        dropRegion.setOnDragOver(this::dropRegion_onDragOver);
        dropRegion.setOnDragDropped(this::dropRegion_onDragDropped);

        dropRegion.setOnDragEntered(this::dropRegion_onDragEntered);
        dropRegion.setOnDragExited(this::dropRegion_onDragExited);
    }

    protected void dropRegion_onDragEntered(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            if (dropRegion instanceof Hook) {
                if (((Hook) dropRegion).isHit(event.getSceneX(), event.getSceneY())) {
                    dropRegion.setEffect(new Glow(8));
                    event.consume();
                } else {
                    dropRegion.setEffect(null);
                    event.consume();
                }
            }
        }
    }

    protected void dropRegion_onDragExited(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            if (dropRegion instanceof Hook) {
                dropRegion.setEffect(null);
                event.consume();
            }
        }
    }

    protected void dropRegion_onDragOver(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    protected void dropRegion_onDragDropped(DragEvent event) {
        if (event.getDragboard().hasContent(DragContext.HANDLE_FORMAT)
                && event.getGestureSource() instanceof HandleRegion) {
            DragContext context = (DragContext) event.getDragboard().getContent(DragContext.HANDLE_FORMAT);
            HandleRegion region = (HandleRegion) event.getGestureSource();

            if (dropRegion instanceof Hook && !((Hook) dropRegion).isHit(event.getSceneX(), event.getSceneY())) {
                return;
            }

            if (dropRegion instanceof VisualSourcePane
                    && event.getAcceptedTransferMode() == TransferMode.COPY) {
                dropCopy(event, context, (VisualSourcePane) dropRegion, region);
                event.consume();
            } else if (dropRegion instanceof VisualSourcePane
                    && event.getAcceptedTransferMode() == TransferMode.MOVE) {
                dropMove(event, context, (VisualSourcePane) dropRegion, region);
                event.consume();
            } else if (dropRegion instanceof Hook
                    && event.getAcceptedTransferMode() == TransferMode.COPY) {
                dropCopy(event, context, (Hook) dropRegion, region);
                event.consume();
            } else if (dropRegion instanceof Hook
                    && event.getAcceptedTransferMode() == TransferMode.MOVE) {
                dropMove(event, context, (Hook) dropRegion, region);
                event.consume();
            }
        }
    }

    private void dropCopy(DragEvent event, DragContext context, VisualSourcePane pane, HandleRegion region) {
        HandleRegion newInstance = region.createNew();
        new DragManager(newInstance, TransferMode.MOVE);

        pane.getChildren().add(newInstance);
        relocate(event, context, newInstance);
    }

    private void dropCopy(DragEvent event, DragContext context, Hook hook, HandleRegion region) {
        HandleRegion newInstance = region.createNew();
        new DragManager(newInstance, TransferMode.MOVE);

        hook.setChild(newInstance);
    }

    private void dropMove(DragEvent event, DragContext context, VisualSourcePane pane, HandleRegion region) {

        pane.getChildren().add(region);
        relocate(event, context, region);

    }

    private void dropMove(DragEvent event, DragContext context, Hook hook, HandleRegion region) {

        hook.setChild(region);

    }

    private void relocate(DragEvent event, DragContext context, HandleRegion region) {

        Point2D pt = dropRegion.sceneToLocal(
                event.getSceneX() - context.getOffsetX(),
                event.getSceneY() - context.getOffserY());
        region.relocate(pt.getX(), pt.getY());

    }
}
