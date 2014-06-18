package game;

import java.util.LinkedList;
import java.util.Random;

import server.BDConnection;
import connection.ChatHandler;
import connection.DrawingHandler;

public class Game implements Runnable {

	static LinkedList<Game> game_active = new LinkedList<Game>();
	static int id_generator = 0;
	
	private int nb_max_gamers;
	private LinkedList<Gamer> gamers = new LinkedList<Gamer>();
	private Gamer creator;
	
	private int id_game;
	private boolean isTeamMode;
	private String category;
	
	private boolean isStart = false;
	
	private int nb_max_round;
	private LinkedList<Round> round = new LinkedList<Round>();
	private int indice_round = 0;
	Thread execute_game;
	
	
	public Game() {
		id_game = id_generator++;
	}
	
	public Game(int nbrGamers, int nbRound, String category, boolean mode, Gamer gamer) {
		id_game = id_generator++;
		nb_max_gamers = nbrGamers;
		nb_max_round= nbRound;
		this.category = category;
		isTeamMode = mode;
		creator = gamer;
	}
	
	public boolean isStarting() {
		return isStart;
	}
	
	public void setStateGame(boolean b) {
		isStart = b;
	}
	
	public int getID() { 
		return id_game;
	}
	
	public Gamer getCreator() {
		return creator;
	}
	
	public boolean isTeamGame() {
		return isTeamMode;
	}

	public int getNbrRounds() {
		return round.size();
	}
	
	public int getNbrMaxRound(){
		return nb_max_round;
	}
	
	public int getNbrMaxGamers() {
		return nb_max_gamers;
	}

	public LinkedList<Gamer> getListGamer() {
		return gamers;
	}
	
	public LinkedList<ChatHandler> getListChat() {
		
		LinkedList<ChatHandler> c = new LinkedList<ChatHandler>();
		for(Gamer g : gamers) {
			c.add(g.chat);
		}
		
		return c;
	}

	public LinkedList<DrawingHandler> getListDrawing() {
		
		LinkedList<DrawingHandler> d = new LinkedList<DrawingHandler>();
		for(Gamer g : gamers) {
			d.add(g.draw);
		}
		
		return d;
	}
	
	public String getCategory() {
		return category;
	}
	
	public Gamer selectDrawer() { 
		Random r = new Random();
		
		int temp = r.nextInt() % gamers.size();
		Gamer g = gamers.get(temp); 
		
		if(indice_round > 0 && round.get(indice_round-1).getDrawer() == g)
			temp = temp + 1 % gamers.size();
		
		return g;
	}

	public void addGamer(Gamer g) { 
		gamers.add(g);
	}
	
	static public LinkedList<Game> getGameActiveList() { 
		return game_active;
	}
	
	static public void addGameActive(Game g) {
		game_active.add(g);
	}
	
	public void startGame() {
		execute_game = new Thread(this);
		execute_game.start();
	}
	
	public String getWord() {
		return round.get(indice_round).getWord();
	}
	
	public void winWord(Gamer winner) {
		round.get(indice_round).setWinner(winner);
		execute_game.notify();
	}
	
	@Override
	public void run() {
		
		for(int r=0; r < nb_max_round; r++) {
			
			Gamer drawer = selectDrawer();
			String word = Dictionary.getWord(category);
			round.add(new Round(this,drawer,word));

			//Protocole start round
			for(Gamer g : gamers) {
				g.connection.round_start_protocole(g == drawer);
			}
			
			//Protocole word round
			drawer.connection.round_word_protocole(word);
			
			try {
				execute_game.sleep(60000);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String winner;
			if(round.get(indice_round).getWinner() != null)
				winner = round.get(indice_round).getWinner().getPseudo();
			else
				winner = "NONE";
			
			//Protocole end round
			for(Gamer g : gamers) {
				g.connection.round_end_protocole(winner,word);
			}
			
			indice_round++;
		}
		
	}
}
