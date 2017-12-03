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
import net.algoid.visualsource.core.AbstractVisualSource;
import net.algoid.visualsource.core.Hook;
import net.algoid.visualsource.core.Hook.HookEvent;
import static net.algoid.visualsource.shapes.Constants.BORDER;
import static net.algoid.visualsource.shapes.Constants.HEIGHT;

/**
 *
 * @author cyann
 */
public class UnaryControl extends AbstractInstructionNode {

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
    private Hook instructionHook, contentHook;

    public UnaryControl(AbstractVisualSource placeHolder, String name) {
        super(placeHolder, name);
        shape = new SVGPath();
        text = new Text(name);
    }

    private void applyLayout() {
        double width = text.getLayoutBounds().getWidth() + BORDER * 4;
        double height = getRawHeight();
        shape.setContent(String.format(SVG_FORMAT, width, HEIGHT, height, height - BORDER));

        if (instructionHook != null) {
            instructionHook.setLayoutY(height);
        }
    }

    @Override
    protected Node getGraphic() {
        text.getStyleClass().add("in-text");
        text.setX(BORDER * 2);
        text.setTextOrigin(VPos.CENTER);
        text.setY(HEIGHT / 2 - 1);
        text.applyCss();

        shape.getStyleClass().add("in");
        shape.getStyleClass().add("in-control");
        shape.getStyleClass().add(String.format("in-control-%s", getName().replace(" ", "-")));

        applyLayout();

        return new Group(shape, text);
    }

    @Override
    protected void initializeLayout() {
        instructionHook = createInstructionHook(true);
        contentHook = createInstructionHook(false);
        contentHook.relocate(15, HEIGHT);

        contentHook.setOnHangEvent(this::contentHook_onHangEvent);
        contentHook.setOnReleaseEvent(this::contentHook_onReleaseEvent);
    }

    @Override
    protected double getRawHeight() {
        double height = HEIGHT + BORDER;
        if (contentHook != null && contentHook.hasChild()) {
            height += contentHook.getChild().computeRawHeight();
        }

        return height;
    }

    // event management
    protected void contentHook_onHangEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

    protected void contentHook_onReleaseEvent(HookEvent event) {
        Platform.runLater(this::applyLayout);
    }

}
