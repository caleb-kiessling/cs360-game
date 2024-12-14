package application.entities;

import application.core.Answer;
import application.core.GameObject;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Asteroid implements GameObject{
private Circle circle;
private Label label;
private StackPane stackpane;
private Answer answer;
private double startx;
private double starty;

public Asteroid(Answer answer) {
	circle= new Circle(50);
	label= new Label(answer.getText());
	label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
	stackpane=new StackPane();
	stackpane.getChildren().addAll(circle,label);
	this.answer=answer;
	
}
public StackPane getVisual() {
	return stackpane;
}
@Override
public void update(double deltaTime) {
	
}
public void setXY(double x, double y) {
	this.stackpane.setScaleX(x);
	this.stackpane.setScaleY(y);

}
public void setStartxy(double x, double y) {
	this.startx=x;
	this.starty=y;
}
public void setLayoutXY(double x, double y) {
	this.stackpane.setLayoutX(x);
	this.stackpane.setLayoutY(y);
}
public double getStartx() {
	return startx;
}
public double getStarty() {
	return starty;
	
}
public Answer getAnswer() {
	return this.answer;
}
@Override
public Bounds getBoundInParent() {
	
	return stackpane.getBoundsInParent();
}

}
