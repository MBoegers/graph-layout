package de.boeg.graph.layout.domain;

public abstract class Node<T> {
    private double x;
    private double y;

    public abstract T getIdentifier();

    public Node() {
        x = 0d;
        y = 0d;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && this.getIdentifier().equals(((Node)obj).getIdentifier());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

    @Override
    public String toString() {
        return "[".concat(getIdentifier().toString()).concat("]: x=")
                .concat(Double.toString(x)).concat(", y=").concat(Double.toString(y));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
