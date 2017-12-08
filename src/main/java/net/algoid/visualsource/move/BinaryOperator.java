/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.move;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.algoid.visualsource.coreMove.AbstractVisualSource;
import net.algoid.visualsource.coreMove.AcceptationType;
import net.algoid.visualsource.coreMove.HookOld;
import net.algoid.visualsource.coreMove.HookOld.HookEvent;

/**
 *
 * @author cyann
 */
public class BinaryOperator extends AbstractNonTerminalNode {

    private final Rectangle shape;
    private final Text text;
    private HookOld leftHook, rightHook;
    private double textWidth = 0;

    public BinaryOperator(AbstractVisualSource placeHolder, String name, String symbol) {
        super(placeHolder, name, EXPRESSION_DRAG_BOUNDS, EXPRESSION_HOLD_BOUNDS);
        shape = new Rectangle(UNIT * 4, UNIT);
        shape.setArcHeight(UNIT);
        shape.setArcWidth(UNIT);
        text = new Text(symbol);
    }

    @Override
    protected AcceptationType getAcceptationType() {
        return AbstractNonTerminalNode.EXPRESSION;
    }

    private void applyLayout() {
        double width = getRawWidth();
        shape.setWidth(width);

        double leftWidth = leftHook.getRawWidth();
        text.setLayoutX(UNIT * 0.5 + leftWidth + UNIT * 0.375);
        rightHook.setLayoutX(leftWidth + UNIT * 1.5 + textWidth);

    }

    @Override
    protected Node getGraphic() {
        text.getStyleClass().add("in-text");
        text.setTextOrigin(VPos.CENTER);
        text.setY(UNIT / 2 - 1);
        text.applyCss();
        textWidth = text.getLayoutBounds().getWidth();

        shape.getStyleClass().add("in");
        shape.getStyleClass().add("in-operator");
        shape.getStyleClass().add(String.format("in-operator-%s", getName().replace(" ", "-")));

        return new Group(shape, text);
    }

    private AbstractTerminalNode createDummy() {
        return new BooleanNode(placeHolder, true);
    }

    @Override
    protected void initializeLayout() {
        leftHook = createExpressionHook(UNIT * 0.5, UNIT * 0.5);
        rightHook = createExpressionHook(UNIT * 2, UNIT * 0.5);

        leftHook.setChild(createDummy());
        rightHook.setChild(createDummy());

        leftHook.setOnHangEvent(this::leftHook_onHangEvent);
        leftHook.setOnReleaseEvent(this::leftHook_onReleaseEvent);

        rightHook.setOnHangEvent(this::rightHook_onHangEvent);
        rightHook.setOnReleaseEvent(this::rightHook_onReleaseEvent);

        Platform.runLater(this::applyLayout);
    }

    @Override
    public double getRawHeight() {
        return UNIT;
    }

    @Override
    public double getRawWidth() {
        double width = UNIT * 2 + textWidth;
        width += leftHook.getRawWidth();
        width += rightHook.getRawWidth();

        return width;
    }

    // event management
    protected void leftHook_onHangEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

    protected void leftHook_onReleaseEvent(HookEvent event) {
        if (!event.getHook().hasChild()) {
            event.getHook().setChild(createDummy());
        }
        Platform.runLater(this::applyLayout);
    }

    protected void rightHook_onHangEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

    protected void rightHook_onReleaseEvent(HookEvent event) {
        if (!event.getHook().hasChild()) {
            event.getHook().setChild(createDummy());
        }
        Platform.runLater(this::applyLayout);
    }

}
