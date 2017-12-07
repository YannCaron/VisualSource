/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Circle;
import net.algoid.visualsource.coreMove.AbstractVisualSource;
import net.algoid.visualsource.coreMove.AcceptationType;
import net.algoid.visualsource.coreMove.AssociatedHook;
import net.algoid.visualsource.coreMove.HoldableRegion;
import net.algoid.visualsource.coreMove.HookOld;

/**
 *
 * @author cyann
 */
public abstract class AbstractNonTerminalNode extends HoldableRegion implements Constants {

    // constant
    public static final Bounds INSTRUCTION_HOLD_BOUNDS = new BoundingBox(0, 0, UNIT * 3, UNIT * 0.3);
    public static final Bounds INSTRUCTION_DRAG_BOUNDS = new BoundingBox(0, 0, UNIT * 4, UNIT);
    public static final Bounds EXPRESSION_DRAG_BOUNDS = new BoundingBox(0, 0, UNIT * 2, UNIT);
    public static final Bounds EXPRESSION_HOLD_BOUNDS = new BoundingBox(0, 0, UNIT * 2, UNIT);

    public static final Effect DRAG_EFFECT = new GaussianBlur(5);
    public static final double OVER_OPACITY = 0.5;

    // attribute
    private final String name;

    // constructor
    public AbstractNonTerminalNode(AbstractVisualSource placeHolder, String name, Bounds dragBounds, Bounds holdBounds) {
        super(placeHolder, dragBounds, holdBounds);
        this.name = name;
        this.setTypeOf(getAcceptationType());

        this.setOnOverEvent(this::this_onOver);
        this.setOnOutEvent(this::this_onOut);
        this.setOnDragStartedEvent(this::this_onDragStarted);
        this.setOnDragStoppedEvent(this::this_onDragStopped);
    }

    // accessor
    public String getName() {
        return name;
    }

    // abstract
    protected abstract AcceptationType getAcceptationType();

    // method
    public HookOld createInstructionHook(boolean linkable) {
        Circle shape = new Circle(22, -2, 4);
        getChildren().add(shape);
        shape.getStyleClass().add("hook-tip");

        HookOld hook = new AssociatedHook(this, shape, HookOld.Direction.vertical) {
            @Override
            protected void applyOverEffect(Node tip) {
                tip.setVisible(true);
            }

            @Override
            protected void applyOutEffect(Node tip) {
                tip.setVisible(false);
            }
        };

        hook.setAcceptType(INSTRUCTION);

        addHook(hook, linkable);
        shape.toFront();
        return hook;
    }

    public HookOld createExpressionHook(double x, double unit) {
        Circle shape = new Circle(unit, unit, 4);
        getChildren().add(shape);
        shape.getStyleClass().add("hook-tip");

        HookOld hook = new AssociatedHook(this, shape, HookOld.Direction.horizontal) {
            @Override
            protected void applyOverEffect(Node tip) {
                tip.setVisible(true);
            }

            @Override
            protected void applyOutEffect(Node tip) {
                tip.setVisible(false);
            }
        };
        hook.setAcceptType(EXPRESSION);

        addHook(hook, false);
        hook.relocate(x, 0);
        shape.toFront();
        return hook;
    }

    // event management
    protected void this_onOver(LinkableRegionEvent event) {
        setOpacity(OVER_OPACITY);
    }

    protected void this_onOut(LinkableRegionEvent event) {
        setOpacity(1);
    }

    protected void this_onDragStarted(LinkableRegionEvent event) {
        setEffect(DRAG_EFFECT);
    }

    protected void this_onDragStopped(LinkableRegionEvent event) {
        setEffect(null);
    }

}
