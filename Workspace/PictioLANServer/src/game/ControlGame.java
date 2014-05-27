package game;

import java.util.LinkedList;


public class ControlGame {

	public static LinkedList<ControlGame> listActiveGame = new LinkedList<ControlGame>();
	
	static int idGameUnique = 0;
	int idGame;
	
	int nbGamers;
	LinkedList<ActiveGamer> gamers;
	ActiveGamer creator;
	
	boolean modeEquipe;
	
	public ControlGame(ActiveGamer c) {
		idGame = idGameUnique;
		idGame++;
		
		gamers = new LinkedList<ActiveGamer>();
		creator = c;
		
		listActiveGame.add(this);
		System.out.println(listActiveGame.size());
	}
	
	public int getIDGame() {
		return idGame;
	}
	
	public void setNbGamers(int n) {
		nbGamers = n;
	}
	
	public void setModeEquipe(boolean m) {
		modeEquipe = m;
	}
	public boolean isModeEquipe() {
		return modeEquipe;
	}	
	public String creatorPseudo() {
		return creator.getPseudo();
	}
}
