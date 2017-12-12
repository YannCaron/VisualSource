/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.scene.Node;
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

    public AbstractSyntaxNode(AbstractSyntaxNode cloned) {
        super(cloned);
        this.name = cloned.name;
    }

    public void applyTextStyle(Node node) {
        node.getStyleClass().add(String.format("text"));
        node.getStyleClass().add(String.format("%s-text", this.getClass().getSimpleName()));
        node.applyCss();
    }

    public void applyShapeStyle(Node node) {
        node.getStyleClass().add(AbstractSyntaxNode.class.getSimpleName());
        node.getStyleClass().add(this.getClass().getSimpleName());
        node.getStyleClass().add(String.format("%s-%s", this.getClass().getSimpleName(), getName().replace(" ", "-")));
        node.applyCss();
    }

    public String getName() {
        return name;
    }

}
