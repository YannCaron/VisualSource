/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.algoid.visualsource.shapes.Action;
import net.algoid.visualsource.shapes.UnaryControl;

/**
 * FXML Controller class
 *
 * @author cyann
 */
public class MainController implements Initializable {

    @FXML
    VisualSourcePlaceHolder visualSourcePlaceHolder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Action action1 = new Action(visualSourcePlaceHolder, "move", 10, 10);
        Action action2 = new Action(visualSourcePlaceHolder, "turn left", 250, 10);
        Action action3 = new Action(visualSourcePlaceHolder, "turn right", 500, 10);
        Action action4 = new Action(visualSourcePlaceHolder, "jump", 10, 100);
        Action action5 = new Action(visualSourcePlaceHolder, "move", 250, 100);

        visualSourcePlaceHolder.getChildren().addAll(action1, action2, action3, action4, action5);

        UnaryControl control1 = new UnaryControl(visualSourcePlaceHolder, "loop", 10, 200);
        UnaryControl control2 = new UnaryControl(visualSourcePlaceHolder, "while", 250, 200);

        visualSourcePlaceHolder.getChildren().addAll(control1, control2);

        HangableRegion region1 = new HangableRegion(visualSourcePlaceHolder, new BoundingBox(0, 0, 50, 50)) {
        };
        region1.getChildren().add(new Rectangle(100, 100, Color.ANTIQUEWHITE));
        Hook hook = region1.addHook(Hook.Direction.horizontal, 100, 0, true);
        hook.setOnOverEvent((Hook.HookEvent event) -> {
            System.out.println("Over " + event);
        });
        hook.setOnOutEvent((Hook.HookEvent event) -> {
            System.out.println("Out " + event);
        });
        region1.setOnOverEvent((LinkableRegion.LinkableRegionEvent event) -> {
            event.getLinkableRegion().setOpacity(0.75);
        });
        region1.setOnOutEvent((LinkableRegion.LinkableRegionEvent event) -> {
            event.getLinkableRegion().setOpacity(1);
        });

        region1.setOnDragStartedEvent((LinkableRegion.LinkableRegionEvent event) -> {
            System.out.println("Drag started " + event);
        });
        region1.setOnDragStoppedEvent((LinkableRegion.LinkableRegionEvent event) -> {
            System.out.println("Drag stopped " + event);
        });

        HangableRegion region2 = new HangableRegion(visualSourcePlaceHolder, new BoundingBox(0, 0, 50, 50)) {

        };
        region2.getChildren().add(new Rectangle(75, 75, Color.GRAY));
        region2.addHook(Hook.Direction.horizontal, 75, 0, true);

        HangableRegion region3 = new HangableRegion(visualSourcePlaceHolder, new BoundingBox(0, 0, 50, 50)) {

        };
        region3.getChildren().add(new Rectangle(50, 50, Color.ALICEBLUE));
        region3.addHook(Hook.Direction.horizontal, 50, 0, true);

        visualSourcePlaceHolder.getChildren().addAll(region1, region2, region3);

    }

    public void setData() {
    }
}
