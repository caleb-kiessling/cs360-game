package application.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameDataParser {
	private List<Level> levels;
	
	public GameDataParser(String path) throws IOException {
		this.levels = this.parseData(path);
	}
	
	public List<Level> getLevels() {
        return levels;
    }

    // New method to get the level by index
    public Level getLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            throw new IndexOutOfBoundsException("Level index out of bounds.");
        }
        return levels.get(index);
    }

    // New method to get the number of levels
    public int getLevelCount() {
        return levels.size();
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
}
