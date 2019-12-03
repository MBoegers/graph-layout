package de.boeg.graph.layout.domain;

public abstract class Edge<T> {
    private final T firstNodeIdentifier;
    private final T secondNodeIdentifier;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Edge) {
            Edge edge = (Edge)obj;
            return firstNodeIdentifier.equals(edge.getFirstNodeIdentifier())
                    && secondNodeIdentifier.equals(edge.getSecondNodeIdentifier());
        }
        return false;
    }

    public Edge(Node<T> first, Node<T> second) {
        firstNodeIdentifier = first.getIdentifier();
        secondNodeIdentifier = second.getIdentifier();
    }

    public T getFirstNodeIdentifier() {
        return firstNodeIdentifier;
    }

    public T getSecondNodeIdentifier() {
        return secondNodeIdentifier;
    }
}
