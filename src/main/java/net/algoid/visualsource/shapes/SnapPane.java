/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author cyann
 */
public class SnapPane extends AnchorPane {

    public enum Type {
        EXPRESSION, INSTRUCTION
    }

    public static final double SNAP_AREA_SIZE = 10;
    private final Type type;

    public SnapPane(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Bounds getSnapBoundsInLocal() {
        Bounds local = getBoundsInLocal();
        return new BoundingBox(local.getMinX(), local.getMinY(), SNAP_AREA_SIZE, SNAP_AREA_SIZE);
    }

    public boolean intersectsInstruction(InstructionPane instruction) {
        Bounds instructionInScene = instruction.localToScene(instruction.getAnchorBoundsInLocal());
        Bounds inScene = localToScene(getSnapBoundsInLocal());

        return inScene.intersects(instructionInScene);
    }

}
