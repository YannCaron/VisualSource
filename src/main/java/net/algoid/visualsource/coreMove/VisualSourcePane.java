/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.coreMove;

import net.algoid.visualsource.coreMove.AbstractVisualSource;

/**
 *
 * @author cyann
 */
public class VisualSourcePane extends AbstractVisualSource {

    @Override
    protected void initialize() {
        getStylesheets().add(getClass().getResource("/styles/visual-source.css").toExternalForm());
    }

}
