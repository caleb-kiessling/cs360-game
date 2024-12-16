package application.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Handles parsing and saving game data
public class DataParser {
    private int level;
    private int destroyed;
    private int losses;
    private int wins;
    private int correct;
    private int temp;

    private String levelPath; // Path to level data file
    private String playerPath; // Path to player data file

    private List<Level> levels; // List of levels

    // Constructor to load levels and player data
    public DataParser(String level, String player) throws IOException {
        this.levelPath = level;
        this.playerPath = player;

        this.levels = this.parseData(this.levelPath);
        this.loadPlayerData(this.playerPath);
    }

    // Get the list of levels
    public List<Level> getLevels() {
        return levels;
    }

    // Get a level by index
    public Level getLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            throw new IndexOutOfBoundsException("Level index out of bounds.");
        }
        return levels.get(index);
    }

    // Get the total number of levels
    public int getLevelCount() {
        return levels.size();
    }

    // Get destroyed count
    public int getDestroyed() {
        return destroyed;
    }

    // Set destroyed count and update player data
    public void setDestroyed(int destroyed) throws IOException {
        this.destroyed = destroyed;
        updatePlayerData();
    }

    // Get current level
    public int getLevel() {
        return this.level;
    }

    // Get losses count
    public int getLosses() {
        return this.losses;
    }

    // Set losses count and update player data
    public void setLosses(int losses) throws IOException {
        this.losses = losses;
        updatePlayerData();
    }

    // Get wins count
    public int getWins() {
        return this.wins;
    }

    // Set wins count and update player data
    public void setWins(int wins) throws IOException {
        this.wins = wins;
        updatePlayerData();
    }

    // Get correct answers count
    public int getCorrect() {
        return correct;
    }

    // Set correct answers count and update player data
    public void setCorrect(int correct) throws IOException {
        this.correct = correct;
        updatePlayerData();
    }

    // Set current level and update player data
    public void setLevel(int level) throws IOException {
        this.level = level;
        updatePlayerData();
    }

    // Get temp value
    public int getTemp() {
        return temp;
    }

    // Set temp value and update player data
    public void setTemp(int temp) throws IOException {
        this.temp = temp;
        updatePlayerData();
    }

    // Parse level data from file
    private List<Level> parseData(String path) throws IOException {
        List<Level> levels = new ArrayList<>();
        Level current = null;
        Question currentQuestion = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("#Level")) { // Start new level
                    if (current != null) {
                        if (currentQuestion != null) {
                            current.addQuestion(currentQuestion); // Add last question
                            currentQuestion = null;
                        }
                        levels.add(current);
                    }
                    int levelInt = Integer.parseInt(line.substring(7).trim());
                    current = new Level(levelInt);
                } else if (line.startsWith("SPEED:")) { // Set level speed
                    double speed = Double.parseDouble(line.substring(6).trim());
                    if (current != null) {
                        current.setSpeed(speed);
                    }
                } else if (line.startsWith("Q:")) { // Start new question
                    if (currentQuestion != null && current != null) {
                        current.addQuestion(currentQuestion);
                    }
                    String questionText = line.substring(3).trim();
                    currentQuestion = new Question(questionText);
                } else if (line.startsWith("A:")) { // Add answer to question
                    boolean isCorrect = line.contains("[CORRECT]");
                    String answerText = line.replace("[CORRECT]", "").substring(3).trim();
                    if (currentQuestion != null) {
                        currentQuestion.addAnswer(new Answer(answerText, isCorrect));
                    }
                } else if (line.startsWith("CONTENT:")) { // Set topic for level
                    String topic = line.replace("CONTENT:", "").trim();
                    if (current != null) {
                        current.setTopic(topic);
                    }
                } else if (line.startsWith("S:")) { // Set question speed
                    int questionSpeed = Integer.parseInt(line.substring(2).trim());
                    if (currentQuestion != null) {
                        currentQuestion.setSpeed(questionSpeed);
                    }
                }
            }

            // Add the last question and level
            if (currentQuestion != null && current != null) {
                current.addQuestion(currentQuestion);
            }
            if (current != null) {
                levels.add(current);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return levels;
    }

    // Load player data from file
    private void loadPlayerData(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#DESTROYED")) {
                    this.destroyed = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("#LOSSES")) {
                    this.losses = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("#WINS")) {
                    this.wins = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("#CORRECT")) {
                    this.correct = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("#TEMP")) {
                    this.temp = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("#LEVEL:")) {
                    this.level = Integer.parseInt(line.split(":")[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save updated player data to file
    private void updatePlayerData() throws IOException {
        if (this.temp != 1) {
            try (FileWriter writer = new FileWriter(this.playerPath)) {
                writer.write("#PLAYER DATA\n");

                writer.write("#LEVEL: " + this.level + "\n");
                writer.write("#DESTROYED: " + this.destroyed + "\n");
                writer.write("#LOSSES: " + this.losses + "\n");
                writer.write("#WINS: " + this.wins + "\n");
                writer.write("#CORRECT: " + this.correct + "\n");
                writer.write("#TEMP: " + this.temp + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
