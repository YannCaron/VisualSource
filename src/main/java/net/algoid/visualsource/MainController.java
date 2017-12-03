/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import net.algoid.visualsource.core.AbstractVisualSource;
import net.algoid.visualsource.core.LinkableRegion;
import net.algoid.visualsource.core.Hook;
import net.algoid.visualsource.core.HoldableRegion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import net.algoid.visualsource.core.AssociatedHook;
import net.algoid.visualsource.shapes.Action;
import net.algoid.visualsource.shapes.UnaryControl;

/**
 * FXML Controller class
 *
 * @author cyann
 */
public class MainController implements Initializable {

    @FXML
    AbstractVisualSource visualSourcePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Action action1 = new Action(visualSourcePane, "move", 10, 10);
        Action action2 = new Action(visualSourcePane, "turn left", 250, 10);
        Action action3 = new Action(visualSourcePane, "turn right", 500, 10);
        Action action4 = new Action(visualSourcePane, "jump", 10, 100);
        Action action5 = new Action(visualSourcePane, "move", 250, 100);

        visualSourcePane.getChildren().addAll(action1, action2, action3, action4, action5);

        UnaryControl control1 = new UnaryControl(visualSourcePane, "loop", 10, 200);
        UnaryControl control2 = new UnaryControl(visualSourcePane, "while", 250, 200);

        visualSourcePane.getChildren().addAll(control1, control2);

        HoldableRegion region1 = new HoldableRegion(visualSourcePane, new BoundingBox(0, 0, 50, 50)) {
            @Override
            protected Node getGraphic() {
                return new Rectangle(100, 100, Color.BLUEVIOLET);
            }

            @Override
            protected void initializeLayout() {
                Node hookTip = new Circle(5, Color.WHITE);
                getChildren().add(hookTip);

                Hook hook = new AssociatedHook(this, hookTip, Hook.Direction.horizontal) {
                    @Override
                    protected void applyOverEffect(Node tip) {
                        tip.setVisible(true);
                    }

                    @Override
                    protected void applyOutEffect(Node tip) {
                        tip.setVisible(false);
                    }
                };

                addHook(hook, 100, 0, true);
                hook.setOnOverEvent((Hook.HookEvent event) -> {
                    System.out.println("Over1 " + event);
                });
                hook.setOnOutEvent((Hook.HookEvent event) -> {
                    System.out.println("Out1 " + event);
                });

            }
        };
//        region1.getChildren().add(new Rectangle(100, 100, Color.ANTIQUEWHITE));
        region1.setOnOverEvent((LinkableRegion.LinkableRegionEvent event) -> {
            event.getLinkableRegion().setOpacity(0.75);
        });
        region1.setOnOutEvent((LinkableRegion.LinkableRegionEvent event) -> {
            event.getLinkableRegion().setOpacity(1);
        });

        HoldableRegion region2 = new HoldableRegion(visualSourcePane, new BoundingBox(0, 0, 50, 50)) {
            @Override
            protected Node getGraphic() {
                return new Rectangle(100, 100, Color.BEIGE);
            }

            @Override
            protected void initializeLayout() {
                Hook hook = addHook(Hook.Direction.horizontal, 100, 0, true);
                hook.setOnOverEvent((Hook.HookEvent event) -> {
                    System.out.println("Over2 " + event);
                });
                hook.setOnOutEvent((Hook.HookEvent event) -> {
                    System.out.println("Out2 " + event);
                });
            }
        };

        HoldableRegion region3 = new HoldableRegion(visualSourcePane, new BoundingBox(0, 0, 50, 50)) {
            @Override
            protected Node getGraphic() {
                return new Rectangle(100, 100, Color.BLANCHEDALMOND);
            }

            @Override
            protected void initializeLayout() {
                addHook(Hook.Direction.horizontal, 100, 0, true);
            }
        };

        visualSourcePane.getChildren().addAll(region1, region2, region3);

    }

    public void setData() {
    }
}
