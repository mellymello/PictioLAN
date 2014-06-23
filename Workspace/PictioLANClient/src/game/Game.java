package game;

import gui.JClient;

import java.util.LinkedList;

import connection.ChatConnection;
import connection.PictioLan;

public class Game implements Runnable {
	
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
	
	JClient client;
	
	Thread threadGaming;
	
	boolean isStart = false;
	boolean isDrawer = false;
	
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
	
	public JClient getClient() { 
		return client;
	}
	
	public void setClient(JClient c) {
		client = c;
	}
	
	public boolean isStart() {
		return isStart;
	}
	
	public boolean isDrawer() {
		return isDrawer;
	}
	public void setIsDrawer(boolean b) {
		isDrawer = b;
	}	
	
	
	public void startGame() {
		isStart = true;
		threadGaming = new Thread(this);
		threadGaming.start();
	}
	
	@Override
	public void run() {
	
		System.out.println(PictioLan.modele_gamer.getPseudo());
		
		PictioLan.modele_gamer.server.ready_protocole();
		
		PictioLan.modele_gamer.server.get_liste_gamer_protocole();
		
		
		
		if(client != null)
			client.printGamers();
		
		for(int i=0; i < nbrRounds ; i++) {
			
			isDrawer = false;
			
			if(PictioLan.modele_gamer.server.get_role_gamer_protocole()){
				
				System.out.println("IM DRAWER");
				isDrawer = true;
				
				if(PictioLan.modele_gamer.getGame().getClient() != null) {
//					PictioLan.modele_gamer.getGame().getClient().setEnableChat(false);
//					PictioLan.modele_gamer.getGame().getClient().setEnableDraw(true);
				}
				
				String w = PictioLan.modele_gamer.server.get_word_protocole();
				
				if(PictioLan.modele_gamer.getGame().getClient() != null) {
					PictioLan.modele_gamer.getGame().getClient().printRandomWord(w);
				}
				
			}
			else {
				
				isDrawer = false;
				
				if(PictioLan.modele_gamer.getGame().getClient() != null) {
//					PictioLan.modele_gamer.getGame().getClient().setEnableChat(true);
//					PictioLan.modele_gamer.getGame().getClient().setEnableDraw(false);
				}
				
			}
			
//			PictioLan.modele_gamer.server.send_start_timer_protocole();
			
			//Afficher Timer
			if(PictioLan.modele_gamer.getGame().getClient() != null) {
				
				for(int timer = 30; timer > 0; timer--) {
					PictioLan.modele_gamer.getGame().getClient().afficherTimer(timer);
					
					try { 
						Thread.currentThread().sleep(1000);
					} catch(InterruptedException e){}
				}
			}
			
			
			PictioLan.modele_gamer.server.send_end_round_protocole();
		}
		
		
		//SYNCRO ICI
		PictioLan.modele_gamer.server.end_game_protocole();
		
		System.out.println("END");
		
	}
}
