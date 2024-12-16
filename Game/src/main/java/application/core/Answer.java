package application.core;

// Represents an answer with text and correctness bool
public class Answer {
    private String text; // The text of the answer
    private boolean isCorrect; // Whether the answer is correct

    // Constructor to initialize an answer
    public Answer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    // Getter for the answer text
    public String getText() {
        return this.text;
    }

    // Getter to check if the answer is correct
    public boolean isCorrect() {
        return this.isCorrect;
    }
}
