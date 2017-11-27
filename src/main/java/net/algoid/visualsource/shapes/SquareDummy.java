/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.algoid.visualsource.VisualSourcePlaceHolder;
import net.algoid.visualsource.shapes.SnapRegion.Type;

/**
 *
 * @author cyann
 */
public class SquareDummy extends InstructionNode {

    public SquareDummy(VisualSourcePlaceHolder placeHolder, double x, double y) {
        super(placeHolder, 100, 100);

        setId("if" + new Random().nextInt(Integer.MAX_VALUE));
        setLayoutX(x);
        setLayoutY(y);

        createSnap(Type.EXPRESSION, 100, 0, true);
        createSnap(Type.INSTRUCTION, 0, 100, true);

    }

    private Color color = Color.color(Math.random(), Math.random(), Math.random());

    @Override
    protected void layoutChildren() {
        GraphicsContext gc = getCanvas().getGraphicsContext2D();

        gc.clearRect(0, 0, 100, 100);
        gc.setFill(color);
        gc.fillRect(0, 0, 100, 100);
    }
}
