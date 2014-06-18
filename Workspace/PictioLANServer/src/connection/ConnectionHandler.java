package connection;

import game.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import server.ManagerGamer;

public class ConnectionHandler implements Runnable {
	
	Socket connexion = null;
	
	Thread threadConnexion;

	BufferedReader in;
	BufferedWriter out;
	
	boolean endConnection = false;

	Gamer gamer = null;
	
	public ConnectionHandler(Socket s) {
		connexion = s;
		threadConnexion = new Thread(this);
		threadConnexion.start();
	}
	
	public void auth_subscribe_protocole() throws IOException {
		
		Gamer temp;
		
		String pseudo = "";
		String pass = "";
		String email = "";
		
		pseudo = in.readLine();
		pass = in.readLine();
		email = in.readLine();
		
		temp = ManagerGamer.addGamer(pseudo, pass, email);
		
		if(temp != null) 
			out.write("AUTH_SUCCESSFUL\n");
		else
			out.write("AUTH_FAILED\n");	
		
		out.flush();
	}
	
	public void auth_anonymous_protocole() throws IOException {

		int id = ManagerGamer.getIDAnonymous();
		
		gamer = new Gamer("Anonyme_" + id);
		gamer.setConnection(this);
		
		out.write("Anonyme_" + id + "\n");
		out.flush();
	}
	
	public void auth_connect_protocole() throws IOException {
		
		String pseudo = "";
		String pass = "";
		String email = "";
		
		pseudo = in.readLine();
		pass = in.readLine();
		
		gamer = ManagerGamer.authentification_BD(pseudo, pass);
		
		if(gamer != null) {
			out.write("AUTH_SUCCESSFUL\n");
			gamer.setConnection(this);
		}
		else
			out.write("AUTH_FAILED\n");
		
		out.flush();
	}
	
	public void list_category_protocole() throws IOException {
		
		LinkedList<String> list = Dictionary.getListCategory();
		System.out.println("List cat = " + list.size());
		out.write(list.size());
		out.flush();
		for(int i=0; i < list.size(); i++)
			out.write(list.get(i) + "\n");
		
		out.flush();
	}
	
	public void game_create_protocole() throws IOException {
		
		int nbRound = in.read();
		int nbrGamers = in.read();
		String category = in.readLine();
		
		boolean mode = Boolean.parseBoolean(in.readLine());
		
		Game g = new Game(nbrGamers,nbRound,category,mode,gamer);
		Game.addGameActive(g);
		gamer.setGame(g);
		
		out.write(gamer.getGame().getID());
		out.flush();
		
	}

	public void game_join_protocole() throws IOException {
		
		System.out.println("OK1");
		int id_game = in.read();
		System.out.println("OK2");
		for(Game g : Game.getGameActiveList()) {
			System.out.println("OK3");
			if(g.getID() == id_game){
				System.out.println("OK4");
				if(g.isStarting())
				{
					System.out.println("OK5");
					out.write("JOIN_FAILED\n");
					out.flush();
					return;
				}
				else
				{
					System.out.println("OK6");
					gamer.setGame(g);
					g.addGamer(gamer);
					System.out.println("OK7");
					out.write("JOIN_SUCCESS\n");
					out.flush();
				}
				
				break;
			}
		}
		
		if(gamer.getGame().getListGamer().size() == gamer.getGame().getNbrMaxGamers()) {
			game_start_protocole();
			gamer.getGame().startGame();
		}
	}
	
	public void game_list_protocole() throws IOException {
		
		out.write(Game.getGameActiveList().size());
		out.flush();
		
		for(Game g : Game.getGameActiveList()) {
			
			if(!g.isStarting()) {
				
				out.write(g.getID());
				out.write(g.getCreator().getPseudo() + "\n");
				out.write(new Boolean(g.isTeamGame()).toString()+"\n");
				out.write(g.getNbrMaxRound());
				out.write(g.getNbrMaxGamers());
				out.write(g.getListGamer().size());
				out.write(g.getCategory() + "\n");
			
				out.flush();
			}
		}
	}

	public void game_start_protocole() {
		try{
			
			out.write("GAME_START\n");
			out.flush();
			
			out.write(gamer.getGame().getListGamer().size());
			out.flush();
			for(int i=0; i < gamer.getGame().getListGamer().size(); i++) {
				
				out.write(gamer.getGame().getListGamer().get(i).getPseudo() + "\n");
				out.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void round_start_protocole(boolean isDrawer) {
		
		try{
			out.write("ROUND_START\n");
			
			if(isDrawer)
				out.write("DRAWER\n");
			else
				out.write("GUESSER\n");
			
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void round_word_protocole(String word) {
		
		try{
			out.write("ROUND_WORD\n");
			out.write(word + "\n");
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void round_end_protocole(String pseudo, String word) {
		try{
			
			out.write("ROUND_END\n");
			
			out.write(pseudo + "\n");
			out.write(word + "\n");
			
			out.flush();
			
			System.out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		endConnection = true;
	}
	
	public void run() {
		
		try {
			
			in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
			out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));	
			
			String protocole;
			
			while(!endConnection) {
				
				System.out.println("Debug - protocole ..");
				protocole = in.readLine();
				System.out.println("Debug - protocole=" + protocole);
				
				if(protocole == null)
					break;
				
				if(protocole.equals("AUTH_CONNECT")) {
					auth_connect_protocole();
				}
				else if(protocole.equals("AUTH_SUBSCRIBE")) {
					auth_subscribe_protocole();
				}
				else if(protocole.equals("AUTH_ANONYMOUS")) {
					auth_anonymous_protocole();
				}
				else if(protocole.equals("LIST_CATEGORY")) {
					list_category_protocole();
				}
				else if(protocole.equals("GAME_CREATE")) {
					game_create_protocole();
				}
				else if(protocole.equals("GAME_JOIN")) {
					game_join_protocole();
				}
				else if(protocole.equals("GAME_LIST")) {
					game_list_protocole();
				}
				else if(protocole.equals("CLOSE_CONNEXION")) {
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error !");
		}
		finally {
			
			try {
				if(connexion != null)
					connexion.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} //Fin méthode run
}