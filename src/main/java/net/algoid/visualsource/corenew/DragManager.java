/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.corenew;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import net.algoid.visualsource.VisualSourcePane;

/**
 *
 * @author cyann
 */
public class DragManager {

    private final HandleRegion node;
    private final TransferMode transferMode;

    public DragManager(HandleRegion node, TransferMode transferMode) {
        this.node = node;
        this.transferMode = transferMode;

        node.setOnDragDetected(this::node_onDragDetected);
        node.setOnDragDone((event) -> {
            System.out.println(event.getDragboard().getContent(DragContext.HANDLE_FORMAT));
        });
    }

    protected void node_onDragDetected(MouseEvent event) {
        Dragboard db = node.startDragAndDrop(transferMode);

        db.setDragView(node.snapshot(null, null));
        db.setDragViewOffsetX(event.getX());
        db.setDragViewOffsetY(event.getY());

        ClipboardContent content = new ClipboardContent();
        content.put(DragContext.HANDLE_FORMAT, new DragContext(event.getX(), event.getY()));
        db.setContent(content);

        if (transferMode == TransferMode.MOVE) {
            if (node.getParent() instanceof VisualSourcePane) {
                VisualSourcePane parent = (VisualSourcePane) node.getParent();
                parent.getChildren().remove(node);
            } else if (node.getParent() instanceof Hook) {
                Hook hook = (Hook) node.getParent();
                hook.removeChild();
            }

        }

        event.consume();
    }

}
