package game;

import java.util.LinkedList;

public class Game {
	
	int idGame; //Fournit par le serveur car unique !
	
	boolean modeEquipe;
	
	Drawing draw;
	Chat chat;
	LinkedList<Round> rounds;
	
	LinkedList<String> gamers;
	
	String pseudoCreator;
	
	public Game(int id, boolean mode, String pseudo) {
		idGame = id;
		modeEquipe = mode;
		pseudoCreator = pseudo;
	}
	
	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public boolean isModeEquipe() {
		return modeEquipe;
	}

	public void setModeEquipe(boolean modeEquipe) {
		this.modeEquipe = modeEquipe;
	}

	public String getPseudoCreator() {
		return pseudoCreator;
	}

	public void setPseudoCreator(String pseudoCreator) {
		this.pseudoCreator = pseudoCreator;
	}

	public void launchGame() {
		draw = new Drawing();
		chat = new Chat();
		rounds = new LinkedList<Round>();
		
		//rounds.add(new Round(0));
	}
	
}
