package application.core;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String text;
    private List<Answer> answers;

    public Question(String text) {
        this.text = text;
        this.answers = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public int getAnswerCount() {
        return answers.size();
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
