/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.shapes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Transform;

/**
 *
 * @author cyann
 */
public class SnapPane extends AnchorPane {

    public static final double SNAP_AREA_SIZE = 10;
    private final Bounds snapBoundsInLocal;
    
    // TODO JIRA #1
    
    public SnapPane() {
        snapBoundsInLocal = new BoundingBox(getBoundsInLocal().getMinX(), getBoundsInLocal().getMinY(), SNAP_AREA_SIZE, SNAP_AREA_SIZE);
        
        localToSceneTransformProperty().addListener(new ChangeListener<Transform>() {
            @Override
            public void changed(ObservableValue<? extends Transform> observable, Transform oldValue, Transform newValue) {
                //snapBoundsInLocal.
            }
        });

    }

}
