/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import net.algoid.visualsource.coreMove.AbstractVisualSource;
import net.algoid.visualsource.coreMove.AcceptationType;
import net.algoid.visualsource.coreMove.HookOld;
import net.algoid.visualsource.coreMove.HookOld.HookEvent;

/**
 *
 * @author cyann
 */
public class UnaryControl extends AbstractNonTerminalNode {

    public static final String SVG_FORMAT
            = "m 0,0 0,%3$f 15,0 "
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
            + "A 7.5,7.5 0 0 1 15,0  Z";

    private final SVGPath shape;
    private final Text text;
    private HookOld expressionHook;
    private HookOld instructionHook, contentHook;
    private double textWidth = 0;

    public UnaryControl(AbstractVisualSource placeHolder, String name) {
        super(placeHolder, name, INSTRUCTION_DRAG_BOUNDS, INSTRUCTION_HOLD_BOUNDS);
        shape = new SVGPath();
        text = new Text(name);
    }

    @Override
    protected AcceptationType getAcceptationType() {
        return AbstractNonTerminalNode.INSTRUCTION;
    }

    private AbstractTerminalNode createDummy() {
        return new BooleanNode(placeHolder, true);
    }

    private void applyLayout() {
        double width = getRawWidth();
        double height = getRawHeight();
        shape.setContent(String.format(SVG_FORMAT, width, UNIT, height, height - BORDER));

        expressionHook.setLayoutX(textWidth + UNIT * 2);

        instructionHook.setLayoutY(height);
    }

    @Override
    protected Node getGraphic() {
        text.getStyleClass().add("in-text");
        text.setX(BORDER * 2);
        text.setTextOrigin(VPos.CENTER);
        text.setY(UNIT / 2 - 1);
        text.applyCss();
        textWidth = text.getLayoutBounds().getWidth();

        shape.getStyleClass().add("in");
        shape.getStyleClass().add("in-control");
        shape.getStyleClass().add(String.format("in-control-%s", getName().replace(" ", "-")));

        return new Group(shape, text);
    }

    @Override
    protected void initializeLayout() {
        expressionHook = createExpressionHook(0, UNIT * 0.5);
        expressionHook.setChild(createDummy());

        instructionHook = createInstructionHook(true);

        contentHook = createInstructionHook(false);
        contentHook.relocate(15, UNIT);

        expressionHook.setOnHangEvent(this::expressionHook_onHangEvent);
        expressionHook.setOnReleaseEvent(this::expressionHook_onReleaseEvent);

        contentHook.setOnHangEvent(this::contentHook_onHangEvent);
        contentHook.setOnReleaseEvent(this::contentHook_onReleaseEvent);

        Platform.runLater(this::applyLayout);
    }

    @Override
    public double getRawHeight() {
        double height = UNIT + BORDER;
        height += contentHook.getRawHeight();

        return height;
    }

    @Override
    public double getRawWidth() {
        double width = textWidth + BORDER * 5.5;
        width += expressionHook.getRawWidth();
        return width;
    }

    // event management
    protected void contentHook_onHangEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

    protected void contentHook_onReleaseEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

    protected void expressionHook_onHangEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

    protected void expressionHook_onReleaseEvent(HookEvent event) {
        if (!event.getHook().hasChild()) {
            event.getHook().setChild(createDummy());
        }
        Platform.runLater(this::applyLayout);
    }

}
