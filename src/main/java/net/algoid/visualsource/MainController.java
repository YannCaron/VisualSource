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
import net.algoid.visualsource.shapes.SquareDummy;

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
        SquareDummy shape1 = new SquareDummy(visualSourcePlaceHolder, 10, 10);
        SquareDummy shape2 = new SquareDummy(visualSourcePlaceHolder, 200, 10);
        SquareDummy shape3 = new SquareDummy(visualSourcePlaceHolder, 400, 10);
        SquareDummy shape4 = new SquareDummy(visualSourcePlaceHolder, 10, 200);
        SquareDummy shape5 = new SquareDummy(visualSourcePlaceHolder, 200, 200);
        visualSourcePlaceHolder.getChildren().addAll(shape1, shape2, shape3, shape4, shape5);
    }

    public void setData() {
    }
}
