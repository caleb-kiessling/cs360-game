package application.core;

import java.io.IOException;
import java.util.List;
//needed to be added to allow gameloop to change the level/questions in gameController
public class LevelControl {
	private DataParser parser;
	private int lvlNum;
	private int questionCount;
	private Question currentQuestion;
	private List<Level> levels;
	
	public LevelControl() {
		try {
			this.parser=new DataParser("levels.txt","playerdata.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("File Not Found");
			e.printStackTrace();
		}
		lvlNum=1;
		questionCount=0;
		levels=parser.getLevels();
	}
	
	public Question getCurrentQuestion() {
		currentQuestion=levels.get(lvlNum).getQuestions().get(questionCount);
		return currentQuestion;
	}
	public void nextQuestion() {
		questionCount++;
	}
	public void loadLevel(int lvlNum){
    questionCount=0;
    this.lvlNum=lvlNum;
    
	}
}
