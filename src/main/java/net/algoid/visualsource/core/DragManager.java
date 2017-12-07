/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

/**
 *
 * @author cyann
 */
public class DragManager {

    public static final double WIDTH_THRESHOLD = 300;
    public static final double HEIGHT_THRESHOLD = 300;

    private final HandleRegion handleRegion;
    private final TransferMode transferMode;
    private Parent lastParent;

    public static void apply(HandleRegion hendleRegion, TransferMode transferMode) {
        new DragManager(hendleRegion, transferMode);
    }

    private DragManager(HandleRegion hendleRegion, TransferMode transferMode) {
        this.handleRegion = hendleRegion;
        this.transferMode = transferMode;

        hendleRegion.setOnDragDetected(this::node_onDragDetected);
        hendleRegion.setOnDragDone(this::node_onDragDone);
    }

    private static Image snapshotForDragboard(Node node) {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        WritableImage img = node.snapshot(sp, null);
        ImageView imageView = new ImageView(img);
        imageView.setSmooth(true);
        imageView.setCacheHint(CacheHint.QUALITY);
        imageView.setCache(true);
        imageView.setPreserveRatio(true);

        if (imageView.getBoundsInLocal().getWidth() > WIDTH_THRESHOLD) {
            imageView.setFitWidth(WIDTH_THRESHOLD);
        } else if (imageView.getBoundsInLocal().getHeight() > HEIGHT_THRESHOLD) {
            imageView.setFitHeight(HEIGHT_THRESHOLD);
        }

        return imageView.snapshot(sp, null);
    }

    protected void node_onDragDetected(MouseEvent event) {
        if (handleRegion.getInitialBoundsInLocal().contains(event.getX(), event.getY())) {

            Dragboard db = handleRegion.startDragAndDrop(transferMode);
            db.setDragView(snapshotForDragboard(handleRegion), event.getX(), event.getY());

            ClipboardContent content = new ClipboardContent();
            content.put(DragContext.HANDLE_FORMAT, new DragContext(event.getX(), event.getY()));
            db.setContent(content);
            
            lastParent = handleRegion.getParent();
            
            if (transferMode == TransferMode.MOVE) {
                System.out.println(handleRegion.getParent());
                if (handleRegion.getParent() instanceof DropPane) {
                    ((DropPane) handleRegion.getParent()).getChildren().remove(handleRegion);
                } else if (handleRegion.getParent() instanceof Hook) {
                    ((Hook) handleRegion.getParent()).removeChild();
                    System.out.println("remove from hook");
                }

            }

            event.consume();
        }
    }

    protected void node_onDragDone(DragEvent event) {
        if (handleRegion.getParent() == null && lastParent != null) {
            if (lastParent instanceof DropPane) {
                ((DropPane) lastParent).getChildren().add(handleRegion);
            } else if (lastParent instanceof Hook) {
                ((Hook) lastParent).setChild(handleRegion);
            }
        }
    }

}
