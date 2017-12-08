/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import net.algoid.visualsource.core.HandleRegion;
import net.algoid.visualsource.core.HoldType;

/**
 *
 * @author cyann
 */
public abstract class AbstractSyntaxNode extends HandleRegion {

    private final String name;

    public AbstractSyntaxNode(String name, HoldType holdType) {
        super(holdType);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
