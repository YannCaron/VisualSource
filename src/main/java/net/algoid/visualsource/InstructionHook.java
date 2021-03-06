/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import net.algoid.visualsource.core.Hook;

/**
 *
 * @author cyann
 */
public class InstructionHook extends Hook implements Constants {

    public static final String SVG_FORMAT
            = "m 0,0 15,0 "
            + "a 7.5,7.5 0 0 0 7,5 7.5,7.5 0 0 0 7,-5 "
            + "l %1$f,0";

    private final SVGPath shape;
    private double width;

    public InstructionHook() {
        super(INSTRUCTION);
        shape = new SVGPath();
        width = UNIT * 4;
        setContent();
    }

    private void setContent() {
        shape.setContent(String.format(SVG_FORMAT, width - 30));
    }

    public void setTipWidth(double width) {
        this.width = width;
        setContent();
    }

    @Override
    protected Node createTip() {
        shape.getStyleClass().add(Hook.class.getSimpleName());
        shape.getStyleClass().add(this.getClass().getSimpleName());

        return shape;
    }

}
