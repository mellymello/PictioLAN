package game;

import java.util.LinkedList;

public class Game {
	

	private String category;
	
	int idGame; //Fournit par le serveur car unique !
	
	boolean modeEquipe;
	
	Drawing draw;
	Chat chat;
	LinkedList<Round> rounds;

	
	String pseudoCreator;
	private int nbrMaxGamers;
	
	
	public Game(int id ,String category, int nbRound,  boolean mode, String pseudo, int nbrMaxGamers) {
		this.idGame=id;
	
		this.category=category;
		modeEquipe = mode;
		pseudoCreator = pseudo;
		rounds = new LinkedList<Round>();
//		gamers  = new LinkedList<String>();
		this.nbrMaxGamers=nbrMaxGamers;
	}
	
//	public LinkedList<String> getGamers() {
//		return gamers;
//	}

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
	
	

	public String getCategory() {
		return category;
	}
	
//	public int getNbrPlayers(){
//		return gamers.size();
//	}

	
	public int getNbrRound(){
		return rounds.size();
	}
	
	public int getNbrMaxGamers() {
		return nbrMaxGamers;
	}

	public void setNbrMaxGamers(int nbrMaxGamers) {
		this.nbrMaxGamers = nbrMaxGamers;
	}

	public void launchGame() {
		draw = new Drawing();
		chat = new Chat();
		rounds = new LinkedList<Round>();
		
		//rounds.add(new Round(0));
	}
	
}
