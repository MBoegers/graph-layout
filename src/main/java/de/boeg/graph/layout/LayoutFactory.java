package de.boeg.graph.layout;

import de.boeg.graph.layout.domain.Edge;
import de.boeg.graph.layout.domain.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Factory to get a graph layout generator instance via configuration
 *
 * @see GraphLayout
 */
public class LayoutFactory {
    private final List<Node> nodeList;
    private final List<Edge> edgeList;
    private int height;
    private int width;
    private LayoutType type;
    private int defaultEjectFactor;
    private int defaultSmallDistEjectFactor;
    private int defaultCondenseFactor;
    private int maxDeltaX;
    private int maxDeltaY;

    /**
     * None means uncinfigured
     * Force will cause a {@linkplain ForceDirectedLayout}
     */
    enum LayoutType {NONE, FORCE}

    private LayoutFactory() {
        height = 0;
        width = 0;
        nodeList = new ArrayList<Node>();
        edgeList = new ArrayList<Edge>();
        type = LayoutType.NONE;
        defaultEjectFactor = 6;
        defaultSmallDistEjectFactor = 5;
        defaultCondenseFactor = 3;
        maxDeltaX = 4;
        maxDeltaY = 3;
    }

    /**
     * Generates a new factory instance
     */
    public static LayoutFactory newInstance() {
        return new LayoutFactory();
    }

    /**
     * The build layout generator will use the force layout algorithm
     */
    public LayoutFactory forced() {
        type = LayoutType.FORCE;
        return this;
    }

    /**
     * Configures the height of the layout result for the algorithm
     *
     * @param height abstract value
     */
    public LayoutFactory height(int height) {
        this.height = height;
        return this;
    }

    /**
     * Configures the width of the layout result for the algorithm
     *
     * @param width abstract value
     */
    public LayoutFactory width(int width) {
        this.width = width;
        return this;
    }

    /**
     * Configures the algorithm to consider this nodes
     *
     * @param nodes can be null/empty
     */
    public LayoutFactory nodes(Collection<Node> nodes) {
        this.nodeList.addAll(nodes);
        return this;
    }

    /**
     * Configures the algorithm to consider this edges
     *
     * @param edges can be null/empty
     */
    public LayoutFactory edges(Collection<Edge> edges) {
        this.edgeList.addAll(edges);
        return this;
    }

    /**
     * Configures the eject factors of the nodes
     * Needed for Forced layout type.
     * @param ejectFactor value that is normally used
     * @param smallDistEjectFactor value that is used when the distance is small
     */
    public LayoutFactory withEjectFactors(int ejectFactor, int smallDistEjectFactor) {
        this.defaultEjectFactor = ejectFactor;
        this.defaultSmallDistEjectFactor = smallDistEjectFactor;
        return this;
    }

    /**
     * Configures how big the delta of each direction is allowed to be
     * @param deltaX may x change
     * @param deltaY max y change
     */
    public LayoutFactory withMaxDeltas(int deltaX, int deltaY) {
        this.maxDeltaX = deltaX;
        this.maxDeltaY = deltaY;
        return this;
    }

    /**
     * Configures the condense factor for the edge based layout step.
     * Needed for Forced layout type
     * @param condenseFactor value for the nodes
     */
    public LayoutFactory withCondenseFactor(int condenseFactor) {
        this.defaultCondenseFactor = condenseFactor;
        return this;
    }

    /**
     * Adds one node to the collection of considered nodes
     *
     * @param node can be null
     */
    public LayoutFactory withNNode(Node node) {
        if (node != null) {
            this.nodeList.add(node);
        }
        return this;
    }

    /**
     * Adds one edge to the collection of considered edges
     *
     * @param edge can be null
     */
    public LayoutFactory withEdges(Edge edge) {
        if (edge != null) {
            this.edgeList.add(edge);
        }
        return this;
    }

    /**
     * Composes the layout generator based on the configuration
     */
    public GraphLayout build() {
        switch (type) {
            case FORCE:
                return new ForceDirectedLayout(nodeList, edgeList, height, width, defaultEjectFactor, defaultSmallDistEjectFactor, defaultCondenseFactor, maxDeltaX, maxDeltaY);
            case NONE:
            default:
                throw new IllegalStateException("Type of layout need to be set");
        }
    }
}

