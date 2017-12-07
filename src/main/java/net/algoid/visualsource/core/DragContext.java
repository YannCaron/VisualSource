/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import java.io.Serializable;
import javafx.scene.input.DataFormat;

/**
 *
 * @author cyann
 */
public class DragContext implements Serializable {

    public static final DataFormat HANDLE_FORMAT = new DataFormat("HANDLE_FORMAT");

    private final double offsetX;
    private final double offserY;

    public DragContext(double offsetX, double offserY) {
        this.offsetX = offsetX;
        this.offserY = offserY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffserY() {
        return offserY;
    }

    @Override
    public String toString() {
        return "DragContext{" + "offsetX=" + offsetX + ", offserY=" + offserY + '}';
    }

}
