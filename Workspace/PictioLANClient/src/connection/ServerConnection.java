package connection;

import game.Game;
import game.Gamer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class ServerConnection {//implements Runnable {
	
	int connexion_port = 3336;
	
	public Socket socketConnexion = null;
	
	BufferedReader inConnexion;
	BufferedWriter outConnexion;
	
	Gamer gamer;
	
//	Thread gaming;
	
	boolean endGame;
	boolean isStart;
	
	public ServerConnection (Gamer g) {
		
		gamer = g;
		
		try {
			socketConnexion = new Socket(gamer.getIP(), connexion_port);
			inConnexion = new BufferedReader (new InputStreamReader(socketConnexion.getInputStream()));
			outConnexion = new BufferedWriter (new OutputStreamWriter(socketConnexion.getOutputStream()));	

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean auth_connect_protocole(String pseudo, String pass) {
		try {
			outConnexion.write("AUTH_CONNECT\n");
			outConnexion.flush();
			
			outConnexion.write(pseudo + "\n");
			outConnexion.flush();
			outConnexion.write(pass + "\n");
			outConnexion.flush();
			
			String rep = inConnexion.readLine();
			
			if(rep.equals("AUTH_SUCCESSFUL")) {
				PictioLan.modele_gamer.setPseudo(pseudo);
				PictioLan.modele_gamer.setPassword(pass);
				return true;
			}
			else {
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	} 
	
	public boolean auth_anonymous_protocole() {
		
		try {
			
			outConnexion.write("AUTH_ANONYMOUS\n");
			outConnexion.flush();
			
			String p = inConnexion.readLine();
			PictioLan.modele_gamer.setPseudo(p);
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean auth_subscribe_protocole(String pseudo, String pass, String email) {
		try {
			outConnexion.write("AUTH_SUBSCRIBE\n");
			outConnexion.flush();
			
			System.out.println("DEBUG subscribe");
			outConnexion.write(pseudo + "\n");
			outConnexion.flush();
			
			outConnexion.write(pass + "\n");
			outConnexion.flush();
			
			outConnexion.write(email + "\n");
			outConnexion.flush();
			
			String rep = inConnexion.readLine();

			return rep.equals("AUTH_SUCCESSFUL");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
		
	public int create_game_protocole(int nbGamer, boolean mode, int nbRound, String category)  {
		
		try {
			outConnexion.write("GAME_CREATE\n");
			
			outConnexion.write(nbRound);
			outConnexion.write(nbGamer);
			outConnexion.write(category+"\n");
			outConnexion.write(new Boolean(mode).toString() + "\n");
			outConnexion.flush();
			
			int id = inConnexion.read();
			
			gamer.setGame(new Game(id, category, nbRound, mode, gamer.getPseudo(), nbGamer));
			
			//gamer.setGame(new Game(id, category, nbRound, mode, gamer.getPseudo(), nbGamer));
			//gamer.getGame().getListGamers().add(gamer);
//			start_game_protocole();
			
			return id;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public LinkedList<String> list_category_protocole() { 
		
		LinkedList<String> cat = new LinkedList<String>();
		
		try {
			outConnexion.write("LIST_CATEGORY\n");
			outConnexion.flush();
			
			int size = inConnexion.read();
			
			for(int i=0; i < size; i++) {
				cat.add(inConnexion.readLine());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cat;
	}

	public LinkedList<Game> list_game_protocole()  {
		
		LinkedList<Game> games = new LinkedList<Game>();
		
		try {
			outConnexion.write("GAME_LIST\n");
			outConnexion.flush();
			
			int nbrGames = inConnexion.read();
			
			int id;
			String creatorPseudo;
			boolean mode;
			int nbrRounds;
			int nbrMaxGamers;
			int nbrActivesGamers;
			String category;
			
			for(int i=0; i < nbrGames; i++) {
				
				id = inConnexion.read();
				creatorPseudo= inConnexion.readLine();
				mode = Boolean.parseBoolean(inConnexion.readLine());
				nbrRounds= inConnexion.read();
				nbrMaxGamers= inConnexion.read();
				nbrActivesGamers = inConnexion.read();
				category=inConnexion.readLine();
				
				games.add(new Game(id, category, nbrRounds, mode, creatorPseudo,nbrMaxGamers,nbrActivesGamers));
			}
			
			return games;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Game join_game_protocole(Game g, int id)  {
		
		try {
			outConnexion.write("GAME_JOIN\n");
			outConnexion.write(id);
			outConnexion.flush();
			
			String rep = inConnexion.readLine();

			if(rep.equals("JOIN_SUCCESS")){
				gamer.setGame(g);
				return g;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void start_game_protocole() {
		
		endGame = false;
		isStart = false;
		
		if(PictioLan.modele_gamer.getGame() != null)
			PictioLan.modele_gamer.getGame().startGame();
		else
			System.out.println("Game not launch");
	}
	
	public boolean get_role_gamer_protocole() {
		try {
			
			String type = inConnexion.readLine();
			return type.equals("DRAWER");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String get_word_protocole() {
		
		try {
			
			String w = inConnexion.readLine();
			//gamer.getGame().getRoundActive().setWord(w);
			return w;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
//	public boolean get_round_end_protocole() {
//		
//		try {
//			
//			String msg = inConnexion.readLine();
//			System.out.println(msg);
//			
//			String winner = inConnexion.readLine();
//			System.out.println(winner);
//			
//			String word = inConnexion.readLine();
//			System.out.println(word);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return gamer.getGame().getRounds().size() == gamer.getGame().getNbRounds();
//	}
	
	public void ready_protocole() { 
		
		try {
			
			outConnexion.write("GAMER_READY\n");
			outConnexion.flush();
			
			outConnexion.write("READY\n");
			outConnexion.flush();
			
			String start_message = inConnexion.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void get_liste_gamer_protocole() {

		try {
		
//			outConnexion.write("GAME_LIST_GAMER\n");
//			outConnexion.flush();
			
			//Utilisateurs
			int nbGamers = inConnexion.read();
			
			//Récupérer tous les joueurs
			for(int i=0; i < nbGamers; i++){
				gamer.getGame().getListGamers().add(new Gamer(inConnexion.readLine()));
			}
			
			System.out.println("Gamers for GAME: ");
			for(int i=0; i < nbGamers; i++){
				System.out.println(gamer.getGame().getListGamers().get(i).getPseudo());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void send_end_round_protocole() {
		try {
			
			String rep = inConnexion.readLine();
			String winner;
			
			if(rep.equals("END_ROUND_WINNER")) {
				winner = inConnexion.readLine();
				System.out.println("WINNER = " + winner);
			}
			else
				System.out.println("WINNER = NULL");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void end_game_protocole() { 
		
		try {
			inConnexion.readLine();
		
			outConnexion.write("CLOSE_CONNEXION\n");
			outConnexion.flush();
			
			if(gamer.getChat() != null)
				gamer.getChat().closeChat();
			
			if(gamer.getDraw() != null)
				gamer.getDraw().closeDraw();
			
			closeConnexion();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnexion() {

		try {
			
			if(outConnexion != null)
				outConnexion.close();
			
			if(inConnexion != null)
				inConnexion.close();
			
			if(socketConnexion != null)
				socketConnexion.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public boolean get_round_start_protocole() {
//		
//		try {
//			
//			inConnexion.readLine();
//			String type = inConnexion.readLine();
//			
//			if(type.equals("Drawer")) {
//				
//				return true;
//			}
//			else{
//				
//				return false;
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return false;
//	}
//	
//	public void get_round_word() {
//		
//		try {
//			inConnexion.readLine();
//			gamer.getGame().getRoundActive().setWord(inConnexion.readLine());
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public boolean get_round_end_protocole() {
//		
//		try {
//			
//			inConnexion.readLine();
//			String winner = inConnexion.readLine();
//			String word = inConnexion.readLine();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return gamer.getGame().getRounds().size() == gamer.getGame().getNbRounds();
//	}
//	
//	public void closeConnexion() {
//
//		try {
//			if(socketConnexion != null)
//				socketConnexion.close();
//		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void run() {
//		
//		String pseudo;
//		
//		try {
//			System.out.println("client thread");
//			String start_message = inConnexion.readLine();
//			System.out.println(start_message);
//			int nbGamers = inConnexion.read();
//			System.out.println(nbGamers);
//			//Récupérer tous les joueurs
//			for(int i=0; i < nbGamers; i++){
//				gamer.getGame().getListGamers().add(new Gamer(inConnexion.readLine()));
//			}
//			
//			while(!endGame) {
//				
//				//J'ai démarré
//				if(get_round_start_protocole())
//					get_round_word();
//				
//				endGame = get_round_end_protocole();
//			}
//		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
} //Fin de la class ConnexionListener

