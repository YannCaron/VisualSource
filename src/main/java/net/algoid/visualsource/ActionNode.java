/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import net.algoid.visualsource.core.HandleRegion;

/**
 *
 * @author cyann
 */
public class ActionNode extends AbstractSyntaxNode implements Constants {

    public static final String SVG_FORMAT
            = "m 0,0 0,%2$f 15,0 "
            + "a 7.5,7.5 0 0 0 7,5 7.5,7.5 0 0 0 7,-5 "
            + "L %1$f,%2$f %1$f,0 30,0 "
            + "A 7.5,7.5 0 0 1 22.5,5 7.5,7.5 0 0 1 15,0 "
            + "L 0,0 Z";

    private final Text textView;
    private final SVGPath shape;
    private InstructionHook instructionHook;

    public ActionNode(String name) {
        super(name, INSTRUCTION);
        this.textView = new Text(name);
        shape = new SVGPath();
    }

    public ActionNode(ActionNode cloned) {
        super(cloned);
        this.textView = new Text(cloned.getName());
        this.shape = new SVGPath();
        this.applyDragManager(TransferMode.MOVE);
    }

    @Override
    public HandleRegion newInstance() {
        return new ActionNode(this);
    }

    @Override
    public Node createView() {
        textView.setX(BORDER);
        textView.setTextOrigin(VPos.CENTER);
        textView.setY(UNIT / 2 - 1);
        applyTextStyle(textView);

        applyShapeStyle(shape);
        return new Group(shape, textView);
    }

    @Override
    public void createHooks() {
        instructionHook = new InstructionHook();
        addLinkedHook(instructionHook);
    }

    @Override
    public void applyLayout() {
        double width = getRawWidth();
        
        shape.setContent(String.format(SVG_FORMAT, width, UNIT));
        if (instructionHook != null) {
            instructionHook.setTipWidth(width);
            instructionHook.relocate(0, width);
        }

    }

    @Override
    public double getRawHeight() {
        return UNIT;
    }

    @Override
    public double getRawWidth() {
        return textView.getLayoutBounds().getWidth() + BORDER * 2;
    }

}
