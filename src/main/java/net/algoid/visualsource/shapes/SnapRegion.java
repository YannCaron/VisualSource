/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import javafx.geometry.Bounds;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author cyann
 */
public abstract class SnapRegion extends Region {

    public enum Type {
        EXPRESSION, INSTRUCTION
    }

    public static final double SNAP_AREA_SIZE = 10;
    private final Type type;
    private final InstructionNode parentInstruction;
    private InstructionNode instruction;
    
    private Shape area;

    public SnapRegion(InstructionNode parent, Type type) {
        this.parentInstruction = parent;
        this.type = type;
        
        area = getAreaShape();
        area.setVisible(false);
        getChildren().add(area);
    }
    
    public abstract Shape getAreaShape();

    public InstructionNode getParentInstruction() {
        return parentInstruction;
    }

    public void showArea() {
        area.setVisible(true);
    }
    
    public void hideArea() {
        area.setVisible(false);
    }
    
    public Type getType() {
        return type;
    }

    public void setInstruction(InstructionNode instruction) {
        getChildren().remove(this.instruction);
        this.instruction = instruction;
        getChildren().add(instruction);
        area.toFront();
    }

    public InstructionNode getInstruction() {
        return instruction;
    }

    public void removeInstruction() {
        getChildren().remove(instruction);
        instruction = null;
    }

    public boolean containsInstruction() {
        return instruction != null;
    }

    // depth first search
    public SnapRegion queryRegionIntersection(InstructionNode query) {
        if (containsInstruction()) {

            // chain of responcibility
            SnapRegion found = getInstruction().queryRegionIntersection(query);
            if (found != null) {
                return found;
            }
        }

        if (intersectsInstruction(query)) {
            return this;
        }

        return null;
    }

    public boolean intersectsInstruction(InstructionNode query) {
        Bounds queryInScene = query.localToScene(query.getInitialBoundsInLocal());
        Bounds thisInScene = localToScene(area.getBoundsInLocal());

        return thisInScene.intersects(queryInScene);
    }

}
