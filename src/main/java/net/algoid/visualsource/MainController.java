/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import net.algoid.visualsource.core.AbstractVisualSource;
import net.algoid.visualsource.core.LinkableRegion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.algoid.visualsource.corenew.DragManager;
import net.algoid.visualsource.corenew.DropManager;
import net.algoid.visualsource.corenew.HandleRegion;
import net.algoid.visualsource.corenew.HoldType;

/**
 * FXML Controller class
 *
 * @author cyann
 */
public class MainController implements Initializable {

    private static class HandleRectangle extends HandleRegion {

        public HandleRectangle() {
            super(HoldType.ALL);
        }

        @Override
        public HandleRegion createNew() {
            HandleRectangle newInstance = new HandleRectangle();
            net.algoid.visualsource.corenew.Hook hook = newInstance.createHook(HoldType.ALL);
            hook.relocate(150, 0);
            return newInstance;
        }

        @Override
        public Node getGraphic() {
            return new Rectangle(150, 70, Color.color(Math.random(), Math.random(), Math.random()));
        }

        @Override
        public void applyLayout() {
        }
    }

    @FXML
    VBox container;

    @FXML
    AbstractVisualSource visualSourcePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
        Rectangle rect = new Rectangle(150, 50, Color.GOLDENROD);
        rect.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Drag detected");
            Dragboard db = rect.startDragAndDrop(TransferMode.ANY);

            db.setDragView(rect.snapshot(null, null));
            db.setDragViewOffsetX(event.getX());
            db.setDragViewOffsetY(event.getY());

            ClipboardContent content = new ClipboardContent();
            content.putString("drag and drop test");
            db.setContent(content);

            event.consume();
        });

        visualSourcePane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != visualSourcePane
                        && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            }
        });

        visualSourcePane.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != visualSourcePane
                        && event.getDragboard().hasString()) {
                    visualSourcePane.setEffect(new GaussianBlur(5));
                }

                event.consume();
            }
        });

        visualSourcePane.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                visualSourcePane.setEffect(null);

                event.consume();
            }
        });

        visualSourcePane.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                //rect.setText(db.getString());
                System.out.println(db.getString());
                success = true;
                Node r = (Node)event.getGestureSource();
                container.getChildren().remove(r);
                visualSourcePane.getChildren().remove(r);
                Point2D coord = visualSourcePane.sceneToLocal(event.getSceneX() + db.getDragViewOffsetX(), event.getSceneY() + db.getDragViewOffsetY());
                visualSourcePane.getChildren().add(r);
                r.relocate(coord.getX(), coord.getY());
            }
            event.setDropCompleted(success);

            event.consume();
        });

        container.getChildren().add(rect);
         */
        HandleRegion myRegion1 = new HandleRectangle();
        new DragManager(myRegion1, TransferMode.COPY);
        container.getChildren().add(myRegion1);

        new DropManager(visualSourcePane);

        LinkableRegion actionNode1 = new ActionNode(visualSourcePane, "jump");
        actionNode1.relocate(10, 10);
        LinkableRegion actionNode2 = new ActionNode(visualSourcePane, "go");
        actionNode2.relocate(110, 10);
        LinkableRegion actionNode3 = new ActionNode(visualSourcePane, "turn left");
        actionNode3.relocate(210, 10);
        LinkableRegion actionNode4 = new ActionNode(visualSourcePane, "turn right");
        actionNode4.relocate(310, 10);
        LinkableRegion actionNode5 = new ActionNode(visualSourcePane, "draw");
        actionNode5.relocate(410, 10);

        LinkableRegion controlNode1 = new UnaryControl(visualSourcePane, "loop");
        controlNode1.relocate(10, 110);
        LinkableRegion controlNode2 = new UnaryControl(visualSourcePane, "while");
        controlNode2.relocate(110, 110);
        LinkableRegion controlNode3 = new UnaryControl(visualSourcePane, "if");
        controlNode3.relocate(210, 110);
        LinkableRegion controlNode4 = new UnaryControl(visualSourcePane, "when");
        controlNode4.relocate(310, 110);
        LinkableRegion controlNode5 = new UnaryControl(visualSourcePane, "always");
        controlNode5.relocate(410, 110);

        LinkableRegion operatorNode1 = new BinaryOperator(visualSourcePane, "equal", "=");
        operatorNode1.relocate(10, 210);
        LinkableRegion operatorNode2 = new BinaryOperator(visualSourcePane, "no equal", "≠");
        operatorNode2.relocate(110, 210);
        LinkableRegion operatorNode3 = new BinaryOperator(visualSourcePane, "and", "and");
        operatorNode3.relocate(210, 210);
        LinkableRegion operatorNode4 = new BinaryOperator(visualSourcePane, "or", "or");
        operatorNode4.relocate(310, 210);
        LinkableRegion operatorNode5 = new BinaryOperator(visualSourcePane, "less than", "<");
        operatorNode5.relocate(410, 210);

        visualSourcePane.getChildren().addAll(actionNode1, actionNode2, actionNode3, actionNode4, actionNode5);
        visualSourcePane.getChildren().addAll(controlNode1, controlNode2, controlNode3, controlNode4, controlNode5);
        visualSourcePane.getChildren().addAll(operatorNode1, operatorNode2, operatorNode3, operatorNode4, operatorNode5);

    }

    public void setData() {
    }
}
