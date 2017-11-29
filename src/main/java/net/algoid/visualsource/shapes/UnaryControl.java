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
public class UnaryControl extends InstructionNode {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 45;
    public static final int BORDER = 15;

    private final String name;

    public UnaryControl(VisualSourcePlaceHolder placeHolder, String name, double x, double y) {
        super(placeHolder, WIDTH, HEIGHT);
        this.name = name;

        setLayoutX(x);
        setLayoutY(y);

        Text text = new Text(name);
        text.getStyleClass().add("in-text");
        text.setX(50);
        text.setTextOrigin(VPos.CENTER);
        text.setY(HEIGHT / 2 - 1);

        SVGPath shape = new SVGPath();
        shape.setContent(
                String.format("m 0,0 0,%3$d 15,0 "
                        + "a 7.5,7.5 0 0 0 7,5 "
                        + "a 7.5,7.5 0 0 0 7,-5 "
                        + "L 75,%3$d 75,%4$d 44.5,%4$d "
                        + "a -7.5,7.5 0 0 1 -7, 5 "
                        + "a -7.5,7.5 0 0 1 -7, -5 "
                        + "L 15,%4$d 15,45 30.4375,45 "
                        + "c 1.058408,2.993636 3.887273,4.996372 7.0625,5 3.17451,-0.0045 6.002379,-2.007039 7.060547,-5 "
                        + "L 140,45 140,0 29.560547,0 "
                        + "A 7.5,7.5 0 0 1 22.5,5 "
                        + "A 7.5,7.5 0 0 1 15,0  Z",
                        WIDTH, HEIGHT, 210, 210 - BORDER));
        shape.getStyleClass().add("in-control");
        shape.getStyleClass().add(String.format("in-control-%s", name.replace(" ", "-")));

        setViewShape(new Group(shape, text));

        addSnap(new SnapRegion(this, SnapRegion.Type.INSTRUCTION) {
            @Override
            public Shape getAreaShape() {
                Circle shape = new Circle(22, -2, 2);
                shape.getStyleClass().add("snap-instruction");
                return shape;
            }
        }, 15, HEIGHT, false);

        addSnap(new SnapRegion(this, SnapRegion.Type.INSTRUCTION) {
            @Override
            public Shape getAreaShape() {
                Circle shape = new Circle(22, -2, 2);
                shape.getStyleClass().add("snap-instruction");
                return shape;
            }
        }, 0, HEIGHT * 2 + BORDER, false);

    }

}
