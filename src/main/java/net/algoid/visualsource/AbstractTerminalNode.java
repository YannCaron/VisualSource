/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import net.algoid.visualsource.core.AbstractVisualSource;
import net.algoid.visualsource.core.LinkableRegion;

/**
 *
 * @author cyann
 */
public abstract class AbstractTerminalNode extends LinkableRegion implements Constants {

    public AbstractTerminalNode(AbstractVisualSource placeHolder) {
        super(placeHolder);
    }
    
}
