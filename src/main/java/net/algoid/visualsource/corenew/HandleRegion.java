/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.corenew;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 *
 * @author cyann
 */
public abstract class HandleRegion extends Region {
    
    private final HoldType holdType;
    
    public HandleRegion(HoldType holdType) {
        this.getChildren().add(getGraphic());
        this.holdType = holdType;
        
        Platform.runLater(this::applyLayout);
    }
    
    public abstract HandleRegion createNew();
    
    public abstract Node getGraphic();
    
    public abstract void applyLayout();
    
    public Hook createHook(HoldType type) {
        Hook hook = new Hook(type, 25, 25);
        getChildren().add(hook);
        
        new DropManager(hook);
        
        return hook;
    }
}
