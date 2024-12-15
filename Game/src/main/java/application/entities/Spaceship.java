package application.entities;

import application.Main;
import application.core.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Spaceship extends GameObject {
	public static int HEIGHT = 30;
	public static int WIDTH = 25;
	
	private boolean isMoving = false;
    private double speed = 175; 
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public Spaceship() {
        double x = (Main.WIDTH / 2) - (WIDTH/2); // Center horizontally
        double y = Main.HEIGHT - ((Main.HEIGHT/3)); // Place near the bottom
        this.node = new ImageView("ship.png");
        
        ((ImageView) this.node).setFitWidth(WIDTH);
        ((ImageView) this.node).setFitHeight(HEIGHT);
        
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    @Override
    public void update(double deltaTime) {
        double horizontalDirection = 0;
        double verticalDirection = 0;

        if (movingLeft) {
            horizontalDirection -= 1;
        }
        if (movingRight) {
            horizontalDirection += 1;
        }

        if (movingUp) {
            verticalDirection -= 1;
        }
        if (movingDown) {
            verticalDirection += 1;
        }
        
        boolean currentlyMoving = horizontalDirection != 0 || verticalDirection != 0;

        // Change the sprite if the moving state changes
        if (currentlyMoving != this.isMoving) {
            this.isMoving = currentlyMoving;
            String sprite = "ship.png";
            if (this.isMoving) {
            	sprite = "active_ship.png";
            }
            ((ImageView) node).setImage(new Image(sprite));
        }
        
        
        // Normalize diagonal movement... otherwise we get faster.
        double magnitude = Math.sqrt(horizontalDirection * horizontalDirection + verticalDirection * verticalDirection);
        if (magnitude > 1) {
            horizontalDirection /= magnitude;
            verticalDirection /= magnitude;
        }

        // Adjust rotation based on horizontal direction
        double targetAngle = horizontalDirection * 10; // -10 degrees for left, +10 degrees for right
        double currentAngle = node.getRotate();
        double rotationSpeed = 200; // Degrees per second for smooth transition
        double angleDifference = targetAngle - currentAngle;
        double newAngle = currentAngle + Math.min(Math.max(angleDifference, -rotationSpeed * deltaTime), rotationSpeed * deltaTime);
        node.setRotate(newAngle);
        
        // Update horizontal movement
        double newX = node.getLayoutX() + (horizontalDirection * speed * deltaTime);
        if (newX >= 0 && newX + getWidth() <= Main.WIDTH) {
            node.setLayoutX(newX);
        }

        // Update vertical movement
        double newY = node.getLayoutY() + (verticalDirection * speed * deltaTime);
        if (newY >= 0 && newY + getHeight() <= Main.HEIGHT) {
            node.setLayoutY(newY);
        }
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}
