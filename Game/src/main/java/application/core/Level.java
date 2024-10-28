package application.core;

import java.util.ArrayList;
import java.util.List;

public class Level {
	//private String topic;
    private int levelNumber;
    private List<Question> questions;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.questions = new ArrayList<>();
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
