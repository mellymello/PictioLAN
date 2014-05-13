package game;

import java.util.LinkedList;

import round.Round;

public class Game {

	private Round[] rounds;
	private int idActiveRound;
	
	private ActiveGamer creator;
	private LinkedList<ActiveGamer> listGamer = new LinkedList<ActiveGamer>();
	
	public Game(int nbRounds, ActiveGamer c) {
		rounds = new Round[nbRounds];
		creator = c;
		idActiveRound = 0;
	}
	
	public ActiveGamer getCreator() {
		return creator;
	}
	
	public Round getActiveRound() {
		return rounds[idActiveRound];
	}
	
	public void setCreator(ActiveGamer c) {
		creator = c;
	}
	
	public void addGamer(ActiveGamer gamer) {
		listGamer.add(gamer);
	}

	/* TODO
	 * Parcourir la liste et supprimer le gamer de la liste.
	 */
	public void delGamer(ActiveGamer gamer) {
		
	}

}
