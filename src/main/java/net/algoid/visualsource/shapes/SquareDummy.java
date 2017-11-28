/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
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

        addSnap(new SnapRegion(this, Type.EXPRESSION) {
            @Override
            public Shape getAreaShape() {
                Line shape = new Line(0, 0, 0, 50);
                shape.setStroke(Color.WHITE);
                shape.setStrokeWidth(10);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                return shape;
            }
        }, 100, 0, true);
        addSnap(new SnapRegion(this, Type.INSTRUCTION) {
            @Override
            public Shape getAreaShape() {
                Line shape = new Line(0, 0, 50, 0);
                shape.setStroke(Color.WHITE);
                shape.setStrokeWidth(10);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                return shape;
            }
        }, 0, 100, true);

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
