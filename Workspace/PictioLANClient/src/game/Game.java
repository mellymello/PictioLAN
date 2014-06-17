package game;

import java.util.LinkedList;

import connection.ChatConnection;

public class Game {
	
	int id_game;
	String category;
	boolean mode; 
	Gamer creator; 
	int nbrMaxGamers;
	LinkedList<Gamer> gamers = new LinkedList<Gamer>();
	int nbrActivesGamers;
	
	int nbrRounds;
	LinkedList<Round> rounds = new LinkedList<Round>();
	
	int indice_round = 0;
	
	public Game(int id, String category, int nbrRounds, boolean mode, String creatorPseudo, int nbrMaxGamers, int nbrActivesGamers) {
		
		id_game = id;
		this.category = category;
		this.mode = mode; 
		creator= new Gamer(creatorPseudo);
		this.nbrMaxGamers = nbrMaxGamers;
		this.nbrRounds = nbrRounds;
		this.nbrActivesGamers = nbrActivesGamers;
		
	}
	
	public Game(int id, String category, int nbrRounds, boolean mode, String creatorPseudo, int nbrMaxGamers) {
		
		id_game = id;
		this.category = category;
		this.mode = mode; 
		creator= new Gamer(creatorPseudo);
		this.nbrMaxGamers = nbrMaxGamers;
		this.nbrRounds = nbrRounds;
		
	}
	
	public int getID() { return id_game; }
	public String getCategory() { return category; }
	public boolean isMode() { return mode;}
	public Gamer getCreator() { return creator; }
	
	public int getNbMaxGamers() { return nbrMaxGamers; }
	public int getNbGamers() { return nbrActivesGamers; }
	
	public LinkedList<Gamer> getListGamers() { return gamers; }
	
	public int getNbRounds() { return nbrRounds; }
	public LinkedList<Round> getRounds() { return rounds; }
	
	public void setID(int id_game) { this.id_game = id_game; }
	public void setCategory(String category) { this.category = category; }
	public void setMode(boolean mode) { this.mode = mode; }
	public void setCreator(Gamer creator) { this.creator = creator; }

	public void setNbMaxGamers(int nbrMaxGamers) {this.nbrMaxGamers = nbrMaxGamers; }
	public void setNbGamers(int nbrActivesGamers) { this.nbrActivesGamers = nbrActivesGamers; }
	
	public void setListGamers(LinkedList<Gamer> gamers) { this.gamers = gamers; }
	
	public void setNbRounds(int nbrRounds) { this.nbrRounds = nbrRounds; }
	public void setRounds(LinkedList<Round> rounds) { this.rounds = rounds; }
	
	public Round getRoundActive(){ return rounds.get(indice_round); }
	
}
