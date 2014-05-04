package game;

import java.util.LinkedList;

public class Game {

	private Round[] rounds;
	private int idActiveRound;
	
	private Gamer creator;
	private LinkedList<Gamer> listGamer = new LinkedList<Gamer>();
	
	public Game(int nbRounds, Gamer c) {
		rounds = new Round[nbRounds];
		creator = c;
		idActiveRound = 0;
	}
	
	public Gamer getCreator() {
		return creator;
	}
	
	public Round getActiveRound() {
		return rounds[idActiveRound];
	}
	
	public void setCreator(Gamer c) {
		creator = c;
	}
	
	public void addGamer(Gamer gamer) {
		listGamer.add(gamer);
	}

	/* TODO
	 * Parcourir la liste et supprimer le gamer de la liste.
	 */
	public void delGamer(Gamer gamer) {
		
	}

}
