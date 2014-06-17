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

public class ServerConnection implements Runnable {
	
	int connexion_port = 3336;
	
	public Socket socketConnexion = null;
	
	BufferedReader inConnexion;
	BufferedWriter outConnexion;
	
	Gamer gamer;
	
	Thread gaming;
	
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
			System.out.println(rep);
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
			gamer.getGame().getListGamers().add(gamer);
//			start_game_protocole();
			
			return id;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public LinkedList<String> list_category_protocole() { 
		
		System.out.println("Entree_client_list_category");
		LinkedList<String> cat = new LinkedList<String>();
		
		try {
			System.out.println("write LIST_CATEGORY");
			outConnexion.write("LIST_CATEGORY\n");
			outConnexion.flush();
			System.out.print("size = ..");
			int size = inConnexion.read();
			System.out.println(size);
			System.out.println("List category = " + size);
			for(int i=0; i < size; i++) {
				System.out.print("["+i+"] = ..");
				String temp = inConnexion.readLine();
				cat.add(temp);
				System.out.println(temp);
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
			
			System.out.println("envoie passe");
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
		
		System.out.println("OK1");
		
		try {
			outConnexion.write("GAME_JOIN\n");
			outConnexion.write(id);
			outConnexion.flush();
			System.out.println("OK2");
			String rep = inConnexion.readLine();
			System.out.println("OK3" + rep);
			if(rep.equals("JOIN_SUCCESS")){
				System.out.println("OK4");
				gamer.setGame(g);
				gamer.getGame().getListGamers().add(gamer);
//				start_game_protocole();
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
		
		gaming = new Thread(this);
		gaming.start();
	}

	public boolean get_round_start_protocole() {
		
		try {
			
			inConnexion.readLine();
			String type = inConnexion.readLine();
			
			if(type.equals("Drawer")) {
				
				return true;
			}
			else{
				
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void get_round_word() {
		
		try {
			inConnexion.readLine();
			gamer.getGame().getRoundActive().setWord(inConnexion.readLine());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean get_round_end_protocole() {
		
		try {
			
			inConnexion.readLine();
			String winner = inConnexion.readLine();
			String word = inConnexion.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return gamer.getGame().getRounds().size() == gamer.getGame().getNbRounds();
	}
	
	public void closeConnexion() {

		try {
			if(socketConnexion != null)
				socketConnexion.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		String pseudo;
		
		try {
			System.out.println("client thread");
			String start_message = inConnexion.readLine();
			System.out.println(start_message);
			int nbGamers = inConnexion.read();
			System.out.println(nbGamers);
			//Récupérer tous les joueurs
			for(int i=0; i < nbGamers; i++){
				gamer.getGame().getListGamers().add(new Gamer(inConnexion.readLine()));
			}
			
			while(!endGame) {
				
				//J'ai démarré
				if(get_round_start_protocole())
					get_round_word();
				
				endGame = get_round_end_protocole();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} //Fin de la class ConnexionListener

