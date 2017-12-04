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

        LinkableRegion operatorNode1 = new BinaryOperator(visualSourcePane, "plus", "+");
        operatorNode1.relocate(10, 210);
        LinkableRegion operatorNode2 = new BinaryOperator(visualSourcePane, "minus", "-");
        operatorNode2.relocate(110, 210);
        LinkableRegion operatorNode3 = new BinaryOperator(visualSourcePane, "multiplication", "x");
        operatorNode3.relocate(210, 210);
        LinkableRegion operatorNode4 = new BinaryOperator(visualSourcePane, "division", "÷");
        operatorNode4.relocate(310, 210);
        LinkableRegion operatorNode5 = new BinaryOperator(visualSourcePane, "modulo", "mod");
        operatorNode5.relocate(410, 210);
        
        visualSourcePane.getChildren().addAll(actionNode1, actionNode2, actionNode3, actionNode4, actionNode5);
        visualSourcePane.getChildren().addAll(controlNode1, controlNode2, controlNode3, controlNode4, controlNode5);
        visualSourcePane.getChildren().addAll(operatorNode1, operatorNode2, operatorNode3, operatorNode4, operatorNode5);

    }

    public void setData() {
    }
}
