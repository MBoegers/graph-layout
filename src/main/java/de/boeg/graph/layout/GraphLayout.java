package de.boeg.graph.layout;

import de.boeg.graph.layout.domain.Node;

import java.util.Collection;

/**
 * Represents a layout generator for graphs, which does the main layout magic
 * derived from https://github.com/chaangliu/ForceDirectedLayout/blob/master/java/CollisionGenerator.java
 */
public interface GraphLayout<T> {
    /**
     * Does <b>one</b> layout step, depends in the algorithms how much steps are needed
     */
    void layout();

    /**
     * Get back the nodes, x and y position may be updated by the algorithm
     * @return layout nodes
     */
    Collection<? extends Node<T>> getNodes();
}

