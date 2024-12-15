package application.core;

import javafx.scene.Node;

public abstract class GameObject {
    protected Node node;

    public abstract void update(double deltaTime);

    public Node getNode() {
        return node;
    }

    public boolean intersects(GameObject other) {
        return node.localToScene(node.getBoundsInLocal())
                   .intersects(other.getNode().localToScene(other.getNode().getBoundsInLocal()));
    }

    public double getX() {
        return node.getLayoutX();
    }

    public double getY() {
        return node.getLayoutY();
    }

    public double getWidth() {
        return node.getBoundsInParent().getWidth();
    }

    public double getHeight() {
        return node.getBoundsInParent().getHeight();
    }
}