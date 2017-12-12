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
    private InstructionHook controlHook;
    private InstructionHook instructionHook;

    public ControlNode(String name) {
        super(name, INSTRUCTION);
        this.textView = new Text(name);
        shape = new SVGPath();
        this.applyDragManager(TransferMode.COPY);
    }

    public ControlNode(ControlNode cloned) {
        super(cloned);
        this.textView = new Text(cloned.getName());
        shape = new SVGPath();
        this.applyDragManager(TransferMode.MOVE);
    }

    @Override
    public HandleRegion newInstance() {
        return new ControlNode(this);
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
    public void createHooks() {
        controlHook = new InstructionHook();
        addHook(controlHook);
        registerHookForLayout(controlHook);

        instructionHook = new InstructionHook();
        addLinkedHook(instructionHook);
    }

    @Override
    public void applyLayout() {
        double width = getRawWidth();
        double height = getRawHeight();
        shape.setContent(String.format(SVG_FORMAT, width, UNIT, height, height - BORDER));

        if (controlHook != null) {
            controlHook.relocate(BORDER, UNIT);
            controlHook.setTipWidth(width - BORDER);
        }

        if (instructionHook != null) {
            instructionHook.setLayoutY(height);
            instructionHook.setTipWidth(width);
        }
    }

    @Override
    public double getRawHeight() {
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
