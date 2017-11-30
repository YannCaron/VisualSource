/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import net.algoid.visualsource.VisualSourcePlaceHolder;

/**
 *
 * @author cyann
 */
public class Action extends InstructionNode implements Constants {

    private final String name;

    public Action(VisualSourcePlaceHolder placeHolder, String name, double x, double y) {
        super(placeHolder);
        this.name = name;

        setLayoutX(x);
        setLayoutY(y);

        Text text = new Text(name);
        text.getStyleClass().add("in-text");
        text.setX(50);
        text.setTextOrigin(VPos.CENTER);
        text.setY(HEIGHT / 2 - 1);

        SVGPath shape = new SVGPath();
        shape.setContent(String.format("m 0,0 0,%2$d 15,0 "
                + "a 7.5,7.5 0 0 0 7,5 7.5,7.5 0 0 0 7,-5 "
                + "L %1$d,%2$d %1$d,0 30,0 "
                + "A 7.5,7.5 0 0 1 22.5,5 7.5,7.5 0 0 1 15,0 "
                + "L 0,0 Z", INSTRUCTION_WIDTH, HEIGHT));
        shape.getStyleClass().add("in");
        shape.getStyleClass().add("in-action");
        shape.getStyleClass().add(String.format("in-action-%s", name.replace(" ", "-")));
        setViewShape(new Group(shape, text));

        addSnap(new SnapRegion(this, SnapRegion.Type.INSTRUCTION) {
            @Override
            public Shape getAreaShape() {
                Circle shape = new Circle(22, -2, 2);
                shape.getStyleClass().add("snap-tip");
                return shape;
            }
        }, 0, HEIGHT, true);
    }

    @Override
    public int getViewHeight() {
        return HEIGHT;
    }

}
