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
import net.algoid.visualsource.shapes.If;

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
        If shape1 = new If(visualSourcePlaceHolder, 10, 10);
        If shape2 = new If(visualSourcePlaceHolder, 200, 10);
        If shape3 = new If(visualSourcePlaceHolder, 400, 10);
        If shape4 = new If(visualSourcePlaceHolder, 10, 200);
        If shape5 = new If(visualSourcePlaceHolder, 200, 200);
        visualSourcePlaceHolder.getChildren().addAll(shape1, shape2, shape3, shape4, shape5);
    }

    public void setData() {
    }
}
