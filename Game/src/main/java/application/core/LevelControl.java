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
	private int wins;
	
	public LevelControl() {
		try {
			this.parser=new DataParser("levels.txt","playerdata.txt");
			wins=parser.getWins();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("File Not Found");
			e.printStackTrace();
		}
		lvlNum=0;
		questionCount=0;
		levels=parser.getLevels();
	}
	
	public Question getCurrentQuestion() {
		currentQuestion=levels.get(lvlNum).getQuestions().get(questionCount);
		return currentQuestion;
	}
	public int getQuestionCount() {
		return questionCount;
	}
	public Level getCurrentLevel(){
		return levels.get(lvlNum);
	}
	public void nextQuestion() {
		questionCount++;
		
	}
	public boolean hasNextQuestion() {
		if(questionCount==levels.get(lvlNum).getQuestions().size()-1) {
			return false;
		}else {
			return true;
		}
	}
	public void loadLevel(int lvlNum){
    questionCount=0;
    this.lvlNum=lvlNum;
	}
	public void loadNextLevel() {
		lvlNum++;
		loadLevel(lvlNum);
	}
	public void setPlayerData( int destroyed, int losses, int correct) {
		try {
			parser.setCorrect(correct);
			parser.setDestroyed(destroyed);
			parser.setLosses(losses);
			parser.setWins(this.wins);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
