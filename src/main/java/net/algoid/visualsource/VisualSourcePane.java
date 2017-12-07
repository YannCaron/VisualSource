/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import net.algoid.visualsource.core.DropPane;

/**
 *
 * @author cyann
 */
public class VisualSourcePane extends DropPane {

    @Override
    protected void initializeLayout() {
        getStylesheets().add(getClass().getResource("/styles/visual-source.css").toExternalForm());
    }

}
