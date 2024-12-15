package application.core;

import java.util.ArrayList;

public class Question {
	private int speed;
    private String text;
    private ArrayList<Answer> answers;

    public Question(String text) {
        this.text = text;
        this.answers = new ArrayList<>();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public void setSpeed(int speed) {
    	this.speed = speed;
    }
    
    public int getSpeed() {
    	return this.speed;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public int getAnswerCount() {
        return this.answers.size();
    }

    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }
}
