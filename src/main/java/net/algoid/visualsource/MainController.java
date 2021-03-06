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
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author cyann
 */
public class MainController implements Initializable {

    @FXML
    VBox container;

    @FXML
    VisualSourcePane visualSourcePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        container.getChildren().add(new ActionNode("Jump"));
        container.getChildren().add(new ActionNode("Go foreward"));
        container.getChildren().add(new ActionNode("Turn left"));
        container.getChildren().add(new ActionNode("Turn right"));

        container.getChildren().add(new ControlNode("Loop"));
        container.getChildren().add(new ControlNode("While"));
        container.getChildren().add(new ControlNode("If"));

    }

    public void setData() {
    }
}
