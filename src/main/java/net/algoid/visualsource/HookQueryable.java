/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

/**
 *
 * @author cyann
 */
public interface HookQueryable {

    // Depth first search
    Hook queryHookIntersection(HangableRegion query);

}
