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

        LinkableRegion actionNode1 = new ActionNode(visualSourcePane, "jump");
        LinkableRegion actionNode2 = new ActionNode(visualSourcePane, "go");
        LinkableRegion actionNode3 = new ActionNode(visualSourcePane, "turn left");

        LinkableRegion controlNode1 = new UnaryControl(visualSourcePane, "loop");
        LinkableRegion controlNode2 = new UnaryControl(visualSourcePane, "while");
        
        visualSourcePane.getChildren().addAll(actionNode1, actionNode2, actionNode3);
        visualSourcePane.getChildren().addAll(controlNode1, controlNode2);

    }

    public void setData() {
    }
}
