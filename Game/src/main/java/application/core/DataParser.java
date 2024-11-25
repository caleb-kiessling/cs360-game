package application.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class contains all data related things of the game.
 */
public class DataParser {
	private int destroyed;
	private int losses;
	private int wins;
	private int correct;
	private int temp;
	
	private String levelPath;
	private String playerPath;
	
	private List<Level> levels;
	
	
	public DataParser(String level, String player) throws IOException {
		this.levelPath = level;
		this.playerPath = player;
		
		this.levels = this.parseData(this.levelPath);
		this.loadPlayerData(this.playerPath);
	}

	
	public List<Level> getLevels() {
        return levels;
    }

    public Level getLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            throw new IndexOutOfBoundsException("Level index out of bounds.");
        }
        return levels.get(index);
    }

    public int getLevelCount() {
        return levels.size();
    }
	
    public int getDestroyed() {
        return destroyed;
    }

    public void setDestroyed(int destroyed) throws IOException {
        this.destroyed = destroyed;
        updatePlayerData();
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) throws IOException {
        this.losses = losses;
        updatePlayerData();
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) throws IOException {
        this.wins = wins;
        updatePlayerData();
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) throws IOException {
        this.correct = correct;
        updatePlayerData();
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) throws IOException {
        this.temp = temp;
        updatePlayerData();
    }
    
	private List<Level> parseData(String path) throws IOException{
		List<Level> levels = new ArrayList<>();
		
		Level current = null;
		Question currentQuestion = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				
				if (line.startsWith("#Level")) {
					if (current != null) {
						levels.add(current);
					}
					
					int levelInt = Integer.parseInt(line.substring(7).trim());
					
					current = new Level(levelInt);
				} else if (line.startsWith("Q:")) {
					
                    if (currentQuestion != null && current != null) {
                    	current.addQuestion(currentQuestion);
                    }
                    
                    String questionText = line.substring(3).trim();
                    
                    currentQuestion = new Question(questionText);
                    
                } else if (line.startsWith("A:")) {
                	
                	boolean isCorrect = line.contains("[CORRECT]");
                    String answerText = line.replace("[CORRECT]", "").substring(3).trim();
                    
                    if (currentQuestion != null) {
                        currentQuestion.addAnswer(new Answer(answerText, isCorrect));
                    }
                }
            }
			
            if (current != null && currentQuestion != null) {
                current.addQuestion(currentQuestion);
                levels.add(current);
            }
		
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return levels;
	}

	private void loadPlayerData(String path) throws IOException{
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            // i could do this better but i spent too much time on trying to scale it.
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
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

	private void updatePlayerData() throws IOException {
        if (this.temp != 1) {
            try (FileWriter writer = new FileWriter(this.playerPath)) {
                writer.write("#PLAYER DATA\n");
                writer.write("#DESTROYED: " + destroyed + "\n");
                writer.write("#LOSSES: " + losses + "\n");
                writer.write("#WINS: " + wins + "\n");
                writer.write("#CORRECT: " + correct + "\n");
                writer.write("#TEMP: " + temp + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
