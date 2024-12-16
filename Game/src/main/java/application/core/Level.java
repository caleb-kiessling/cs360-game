package application.core;

import java.util.ArrayList;

// Represents a level in the game
public class Level {
    private double speed; // Speed of the level
    private String topic; // Topic of the level
    private int levelNumber; // Level number
    private ArrayList<Question> questions; // List of questions in the level

    // Constructor to create a level with a number
    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.questions = new ArrayList<>();
    }

    // Set the speed of the level
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Get the speed of the level
    public double getSpeed() {
        return this.speed;
    }

    // Set the topic of the level
    public void setTopic(String topic) {
        this.topic = topic;
    }

    // Get the topic of the level
    public String getTopic() {
        return this.topic;
    }

    // Get the level number
    public int getLevelNumber() {
        return levelNumber;
    }

    // Add a question to the level
    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Get a question by its index
    public Question getQuestion(int index) {
        return questions.get(index);
    }

    // Get all questions in the level
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
