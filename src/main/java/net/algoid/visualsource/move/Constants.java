/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.move;

import net.algoid.visualsource.coreMove.AcceptationType;

/**
 *
 * @author cyann
 */
public interface Constants {

    // acceptation type enum
    public static final AcceptationType INSTRUCTION = new AcceptationType("INSTRUCTION");
    public static final AcceptationType EXPRESSION = new AcceptationType("EXPRESSION");

    public static final double UNIT = 35;
    public static final double BORDER = 15;

}
