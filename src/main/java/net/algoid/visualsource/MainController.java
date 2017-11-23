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
import javafx.scene.control.Button;
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
        If ifShape = new If();
        visualSourcePlaceHolder.getChildren().add(ifShape);
    }

    public void setData() {
    }
}
