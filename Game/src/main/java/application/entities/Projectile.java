package application.entities;

import application.core.GameObject;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Projectile extends GameObject {
	public static int HEIGHT = 6;
	public static int WIDTH = 3;
	public static Color COLOR = Color.RED;
	
    private double speed = 500;

    public Projectile(double x, double y) {
    	this.node = new Rectangle(WIDTH, HEIGHT, COLOR);
    	this.node.setBlendMode(BlendMode.DIFFERENCE);
    	
    	this.node.setLayoutX(x);
        this.node.setLayoutY(y);
    }

    @Override
    public void update(double deltaTime) {
        this.node.setLayoutY(node.getLayoutY() - speed * deltaTime);
    }
}