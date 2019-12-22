package de.boeg.graph.layout.domain;

public abstract class Edge<T> {
    private final T firstNodeIdentifier;
    private final T secondNodeIdentifier;

    private double x1;
    private double x2;
    private double y1;
    private double y2;

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

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
