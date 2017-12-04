/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import net.algoid.visualsource.core.AbstractVisualSource;
import net.algoid.visualsource.core.AcceptationType;
import net.algoid.visualsource.core.Hook;

/**
 *
 * @author cyann
 */
public class ActionNode extends AbstractNonTerminalNode {
    
    public static final String SVG_FORMAT
            = "m 0,0 0,%2$f 15,0 "
            + "a 7.5,7.5 0 0 0 7,5 7.5,7.5 0 0 0 7,-5 "
            + "L %1$f,%2$f %1$f,0 30,0 "
            + "A 7.5,7.5 0 0 1 22.5,5 7.5,7.5 0 0 1 15,0 "
            + "L 0,0 Z";

    private final Text text;
    
    public ActionNode(AbstractVisualSource placeHolder, String name) {
        super(placeHolder, name, INSTRUCTION_BOUNDS);
        text = new Text(getName());
    }

    @Override
    protected AcceptationType getAcceptationType() {
        return AbstractNonTerminalNode.INSTRUCTION;
    }
    
    @Override
    protected Node getGraphic() {
        text.getStyleClass().add("in-text");
        text.setX(BORDER);
        text.setTextOrigin(VPos.CENTER);
        text.setY(UNIT / 2 - 1);

        SVGPath shape = new SVGPath();
        shape.setContent(String.format(SVG_FORMAT, getRawWidth(), UNIT));
        shape.getStyleClass().add("in");
        shape.getStyleClass().add("in-action");
        shape.getStyleClass().add(String.format("in-action-%s", getName().replace(" ", "-")));

        return new Group(shape, text);
    }

    @Override
    protected void initializeLayout() {
        Hook instructionHook = createInstructionHook(true);
        instructionHook.setLayoutY(UNIT);
        
    }

    @Override
    public double getRawHeight() {
        return UNIT;
    }

    @Override
    public double getRawWidth() {
        return text.getLayoutBounds().getWidth() + BORDER * 3;
    }

    
}
