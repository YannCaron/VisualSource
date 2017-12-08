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
    private double textWidth = 0;

    public ActionNode(String name) {
        super(name, INSTRUCTION);
        this.textView = new Text(name);
        shape = new SVGPath();
    }

    @Override
    public HandleRegion newInstance() {
        HandleRegion newInstance = new ActionNode(getName()).applyDragManager(TransferMode.MOVE);

        Hook instructionHook = new InstructionHook(getRawWidth());
        newInstance.addLinkedHook(instructionHook);
        instructionHook.relocate(0, getRawHeight());

        return newInstance;
    }

    @Override
    public Node createView() {
        textView.getStyleClass().add(String.format("text"));
        textView.getStyleClass().add(String.format("%s-text", this.getClass().getSimpleName()));
        textView.setX(BORDER);
        textView.setTextOrigin(VPos.CENTER);
        textView.setY(UNIT / 2 - 1);
        textView.applyCss();
        textWidth = textView.getLayoutBounds().getWidth();

        shape.setContent(String.format(SVG_FORMAT, getRawWidth(), UNIT));
        shape.getStyleClass().add(AbstractSyntaxNode.class.getSimpleName());
        shape.getStyleClass().add(this.getClass().getSimpleName());
        shape.getStyleClass().add(String.format("%s-%s", this.getClass().getSimpleName(), getName().replace(" ", "-")));

        return new Group(shape, textView);
    }

    @Override
    public void applyLayout() {
    }

    @Override
    public double getRawHeight() {
        return UNIT;
    }

    @Override
    public double getRawWidth() {
        return textWidth + BORDER * 4;
    }
}
