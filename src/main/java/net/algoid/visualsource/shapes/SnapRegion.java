/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import javafx.geometry.Bounds;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public class SnapRegion extends Region {

    public enum Type {
        EXPRESSION, INSTRUCTION
    }

    public static final double SNAP_AREA_SIZE = 10;
    private final Type type;
    private InstructionPane instruction;

    public SnapRegion(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setInstruction(InstructionPane instruction) {
        this.instruction = instruction;
        getChildren().clear();
        getChildren().add(instruction);
    }

    public InstructionPane getInstruction() {
        return instruction;
    }

    public void removeInstruction() {
        instruction = null;
        getChildren().clear();
    }

    public boolean containsInstruction() {
        return instruction != null;
    }

    // depth first search
    public SnapRegion queryRegionIntersecton(InstructionPane query) {
        if (containsInstruction()) {
            
            // chain of responcibility
            SnapRegion found = getInstruction().queryRegionIntersecton(query);
            if (found != null) {
                return found;
            }
        }

        if (intersectsInstruction(query)) {
            return this;
        }

        return null;
    }

    public boolean intersectsInstruction(InstructionPane query) {
        Bounds queryInScene = query.localToScene(query.getAnchorBoundsInLocal());
        Bounds thisInScene = localToScene(getBoundsInLocal());

        return thisInScene.intersects(queryInScene);
    }

}
