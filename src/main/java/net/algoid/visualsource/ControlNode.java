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
import net.algoid.visualsource.core.Hook;
import static net.algoid.visualsource.move.Constants.BORDER;
import static net.algoid.visualsource.move.Constants.UNIT;

/**
 *
 * @author cyann
 */
public class ControlNode extends AbstractSyntaxNode implements Constants {

    public static final String SVG_FORMAT
            = "m 0,0 0,%3$f 15,0 "
            + "a 7.5,7.5 0 0 0 7,5 "
            + "a 7.5,7.5 0 0 0 7,-5 "
            + "L 75,%3$f 75,%4$f 44.5,%4$f "
            + "a -7.5,7.5 0 0 1 -7, 5 "
            + "a -7.5,7.5 0 0 1 -7, -5 "
            + "L 15,%4$f 15,%2$f 30.5,%2$f "
            + "a 7.5,7.5 0 0 0 7,5 "
            + "a 7.5,7.5 0 0 0 7,-5 "
            + "L %1$f,%2$f %1$f,0 29.5,0 "
            + "A 7.5,7.5 0 0 1 22.5,5 "
            + "A 7.5,7.5 0 0 1 15,0  Z";

    private final Text textView;
    private final SVGPath shape;
    Hook controlHook;

    public ControlNode(String name) {
        super(name, INSTRUCTION);
        this.textView = new Text(name);
        shape = new SVGPath();
    }

    @Override
    public HandleRegion newInstance() {
        ControlNode newInstance = new ControlNode(getName());
        newInstance.applyDragManager(TransferMode.MOVE);

        newInstance.controlHook = new InstructionHook(getRawWidth() - BORDER);
        newInstance.addHook(newInstance.controlHook);
        newInstance.registerHookForLayout(newInstance.controlHook);
        newInstance.controlHook.relocate(BORDER, UNIT);

        Hook instructionHook = new InstructionHook(75);
        newInstance.addLinkedHook(instructionHook);
        instructionHook.relocate(0, getRawHeight());

        return newInstance;
    }

    @Override
    public Node createView() {
        textView.setTextOrigin(VPos.CENTER);
        textView.setX(BORDER);
        textView.setY(UNIT / 2 - 1);
        applyTextStyle(textView);

        applyShapeStyle(shape);
        return new Group(shape, textView);
    }

    @Override
    public void applyLayout() {
        double width = getRawWidth();
        double height = getRawHeight();
        shape.setContent(String.format(SVG_FORMAT, width, UNIT, height, height - BORDER));
    }

    @Override
    public double getRawHeight() {
        System.out.println(controlHook);
        double height = UNIT + BORDER;
        if (controlHook != null) {
            height += controlHook.getRawHeight();
        }
        return height;
    }

    @Override
    public double getRawWidth() {
        return textView.getLayoutBounds().getWidth() + BORDER * 2;
    }
}
