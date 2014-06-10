package game;

import java.util.LinkedList;

import round.Round;

public class Game {
	
	private static int ID=0;
	private static LinkedList<Game> gamesList = new LinkedList<Game>();
	
	private int gameID;
	private String category;
	
	private Round[] rounds;
	private int idActiveRound;
	
	private ActiveGamer creator;
	private LinkedList<ActiveGamer> listGamer = new LinkedList<ActiveGamer>();
	private int nbrMaxGamers;
	private boolean isTeamGame;
	
	public Game(int nbrGamers, int nbRounds, String category,boolean isTeamGame, ActiveGamer c) {
		gameID= ++ID;
		rounds = new Round[nbRounds];
		creator = c;
		idActiveRound = 0;
		this.category=category;
		this.nbrMaxGamers=nbrGamers;
		this.isTeamGame=isTeamGame;
		
		gamesList.add(this);
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

	
	public int getGameID(){
		return gameID;
	}
	
	public int getNbrRounds(){
		return rounds.length;
	}
	
	public String getCategory(){
		return category;
	}

	public static LinkedList<Game> getGamesList() {
		return gamesList;
	}

	public Round[] getRounds() {
		return rounds;
	}

	public int getIdActiveRound() {
		return idActiveRound;
	}

	public LinkedList<ActiveGamer> getListGamer() {
		return listGamer;
	}

	public int getNbrMaxGamers() {
		return nbrMaxGamers;
	}

	public boolean isTeamGame() {
		return isTeamGame;
	}
	
	
	
}
