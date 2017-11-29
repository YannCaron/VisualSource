/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.algoid.visualsource.shapes.Action;
import net.algoid.visualsource.shapes.SquareDummy;
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
        UnaryControl control2 = new UnaryControl(visualSourcePlaceHolder, "move", 250, 200);

        visualSourcePlaceHolder.getChildren().addAll(control1, control2);

    }

    public void setData() {
    }
}
