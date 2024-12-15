package application.core;

public class Answer {
    private String text;
    private boolean isCorrect;

    public Answer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return this.text;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }
}
