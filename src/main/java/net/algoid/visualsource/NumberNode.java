/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.algoid.visualsource.core.AbstractVisualSource;

/**
 *
 * @author cyann
 */
public class NumberNode extends AbstractTerminalNode {

    private double value;
    private final Rectangle shape;
    private final Text text;

    public NumberNode(AbstractVisualSource placeHolder, double value) {
        super(placeHolder);
        this.value = value;
        shape = new Rectangle(UNIT, UNIT);
        shape.setArcHeight(UNIT);
        shape.setArcWidth(UNIT);
        text = new Text(String.valueOf(value));
    }

    @Override
    protected Node getGraphic() {
        text.getStyleClass().add("expression-text");
        text.setX(BORDER * 0.5 - 2);
        text.setTextOrigin(VPos.CENTER);
        text.setY(UNIT / 2);

        shape.getStyleClass().add("expression");
        shape.getStyleClass().add("expression-number");

        return new Group(shape, text);
    }

    @Override
    protected void initializeLayout() {
        applyLayout();
    }

    public void applyLayout() {
        double width = getRawWidth();
        shape.setWidth(width);
    }

    @Override
    public double getRawHeight() {
        return UNIT;
    }

    @Override
    public double getRawWidth() {
        return text.getLayoutBounds().getWidth() + BORDER;
    }

}
