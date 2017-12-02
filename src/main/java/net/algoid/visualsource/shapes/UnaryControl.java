/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import static java.awt.SystemColor.text;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import net.algoid.visualsource.core.AbstractVisualSource;
import static net.algoid.visualsource.shapes.Constants.BORDER;
import static net.algoid.visualsource.shapes.Constants.HEIGHT;

/**
 *
 * @author cyann
 */
public class UnaryControl extends InstructionNode implements Constants {

    private final String name;

    private final SnapRegion contentSnap;
    private final SnapRegion instructionSnap;
    private final SVGPath shape;

    public UnaryControl(AbstractVisualSource placeHolder, String name, double x, double y) {
        super(placeHolder);
        this.name = name;

        setLayoutX(x);
        setLayoutY(y);

        Text text = new Text(name);
        text.getStyleClass().add("in-text");
        text.setX(BORDER * 2);
        text.setTextOrigin(VPos.CENTER);
        text.setY(HEIGHT / 2 - 1);
        text.applyCss();
        final double width = text.getLayoutBounds().getWidth() + BORDER * 4;

        shape = new SVGPath();
        shape.getStyleClass().add("in");
        shape.getStyleClass().add("in-control");
        shape.getStyleClass().add(String.format("in-control-%s", name.replace(" ", "-")));

        setViewShape(new Group(shape, text));

        contentSnap = new SnapRegion(this, SnapRegion.Type.INSTRUCTION) {
            @Override
            public Shape getAreaShape() {
                Circle shape = new Circle(22, -2, 2);
                shape.getStyleClass().add("snap-tip");
                return shape;
            }
        };

        contentSnap.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        double height = getViewHeight();

                        shape.setContent(String.format("m 0,0 0,%3$f 15,0 "
                                + "a 7.5,7.5 0 0 0 7,5 "
                                + "a 7.5,7.5 0 0 0 7,-5 "
                                + "L 75,%3$f 75,%4$f 44.5,%4$f "
                                + "a -7.5,7.5 0 0 1 -7, 5 "
                                + "a -7.5,7.5 0 0 1 -7, -5 "
                                + "L 15,%4$f 15,%2$f 30.4375,%2$f "
                                + "a 7.5,7.5 0 0 0 7,5 "
                                + "a 7.5,7.5 0 0 0 7,-5 "
                                + "L %1$f,%2$f %1$f,0 29.560547,0 "
                                + "A 7.5,7.5 0 0 1 22.5,5 "
                                + "A 7.5,7.5 0 0 1 15,0  Z",
                                width, HEIGHT, height, height - BORDER));

                        instructionSnap.setLayoutY(height);
                    }
                });
            }
        });

        addSnap(contentSnap, 15, HEIGHT, false);

        instructionSnap = new SnapRegion(this, SnapRegion.Type.INSTRUCTION) {
            @Override
            public Shape getAreaShape() {
                Circle shape = new Circle(22, -2, 2);
                shape.getStyleClass().add("snap-tip");
                return shape;
            }
        };
        addSnap(instructionSnap, 0, HEIGHT * 2 + BORDER, true);

    }

    @Override
    public double getViewHeight() {
        double height = HEIGHT + BORDER;
        if (contentSnap.containsInstruction()) {
            height += contentSnap.getInstruction().getChainHeight(SnapRegion.Type.INSTRUCTION);
        }
        return height;
    }

}
