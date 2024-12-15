package application.core;

import java.util.ArrayList;

public class Level {
	private double speed; 
	private String topic;
    private int levelNumber;
    private ArrayList<Question> questions;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.questions = new ArrayList<>();
    }

    public void setSpeed(double speed) {
    	this.speed = speed;
    }
    
    public double getSpeed() {
    	return this.speed;
    }
    
    public void setTopic(String topic) {
    	this.topic = topic;
    }
    
    public String getTopic() {
    	return this.topic;
    }
    
    public int getLevelNumber() {
        return levelNumber;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
