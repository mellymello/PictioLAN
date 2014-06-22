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
	//Game game = null;
	
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
		
		temp = ManagerGamer.addGamerBD(pseudo, pass, email);
		
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
		
		ManagerGamer.addAnonymoGamer(gamer);
	}
	
	public void auth_connect_protocole() throws IOException {
		
		String pseudo = "";
		String pass = "";
		
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
		gamer.getGame().getListGamer().add(gamer);
		
		out.write(gamer.getGame().getID());
		out.flush();

		System.out.println("creator = " + gamer.getPseudo());
		System.out.println("nb joueurs = " + gamer.getGame().getGameActiveList().size());		
	}

	public void game_join_protocole() throws IOException {
		
		int id_game = in.read();

		for(Game g : Game.getGameActiveList()) {
			
			if(g.getID() == id_game){

				if(g.isStarting() || g.getListGamer().size() >= g.getNbrMaxGamers()) {
					
					out.write("JOIN_FAILED\n");
					out.flush();
					return;
				}
				else {
					
					gamer.setGame(g);
					g.addGamer(gamer);
					
					out.write("JOIN_SUCCESS\n");
					out.flush();
					return;
				}
				
			}
		}
		
		out.write("JOIN_FAILED\n");
		out.flush();
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

	public void game_start_protocole() throws IOException{
		
		String msg = in.readLine();
		
		gamer.setIsReady(true);
		
		boolean everyoneIsReady = true;
		
		if(gamer.getGame().getNbrMaxGamers() == gamer.getGame().getListGamer().size()) {
		
			for(Gamer g : gamer.getGame().getListGamer()) {
				
				if(!g.isReady()) {
					everyoneIsReady = false;
					break;
				}
			}
		}
		else
			everyoneIsReady = false;
		
		if(everyoneIsReady) {
			
			//BROADCAST
			for(Gamer g : gamer.getGame().getListGamer()) {
				g.getConnection().out.write("GAME_START\n");
				g.getConnection().out.flush();
			}
			
			gamer.getGame().startGame();
			//game = gamer.getGame();
		}
	}
	
//	public void round_start_protocole(boolean isDrawer) {
//		
//		try{
//			out.write("ROUND_START\n");
//			
//			if(isDrawer)
//				out.write("DRAWER\n");
//			else
//				out.write("GUESSER\n");
//			
//			out.flush();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void round_word_protocole(String word) {
//		
//		try{
//			out.write("ROUND_WORD\n");
//			out.write(word + "\n");
//			out.flush();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void round_end_protocole(String pseudo, String word) {
//		try{
//			
//			out.write("ROUND_END\n");
//			
//			out.write(pseudo + "\n");
//			out.write(word + "\n");
//			
//			out.flush();
//			
//			System.out.println();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void send_list_gamer_protocole() throws IOException {
		
		out.write(gamer.getGame().getListGamer().size());
		out.flush();
		
		for(int i=0; i < gamer.getGame().getListGamer().size(); i++) {
			out.write(gamer.getGame().getListGamer().get(i).getPseudo() + "\n");
			out.flush();
		}
	}
	
	public void send_gamer_role_protocole(boolean isDrawer) throws IOException{
		
		if(isDrawer) {
			out.write("DRAWER\n");
			out.flush();
		} 
		else {
			out.write("GAMER\n");
			out.flush();			
		}
	}
	
	public void send_word_gamer_protocole(String word) throws IOException {
		out.write(word + "\n");
		out.flush();
	}
	
	public void send_winner_protocole(String pseudo) throws IOException {
			out.write(pseudo + "\n");
			out.flush();
	}
	
	public void send_end_round_protocole(Gamer winner) throws IOException {
		
		if(winner != null) {
			out.write("END_ROUND_WINNER\n");
			out.flush();
			
			out.write(winner.getPseudo() + "\n");
			out.flush();
		}
		else {
			out.write("END_ROUND_NULL\n");
			out.flush();
		}
	}
	
	public void end_protocole() throws IOException {
			out.write("END_GAME\n");
			out.flush();
//			threadConnexion.notify();
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
				else if(protocole.equals("GAMER_READY")) {
					game_start_protocole();
//					threadConnexion.interrupt();
				}
//				else if(protocole.equals("GAME_LIST_GAMER")) {
//					send_list_gamer_protocole();
//				}
//				else if(protocole.equals("GAME_SEND_ROLE")) {
//					send_gamer_role_protocole();
//				}
//				else if(protocole.equals("GAME_SEND_WORD")) {
//					send_word_gamer_protocole();
//				}
//				else if(protocole.equals("GAME_SEND_WINNER")) {
//					send_winner_protocole();
//				}
				else if(protocole.equals("CLOSE_CONNEXION")) {
					System.out.println("Arret du system");
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error !");
		}
		finally {
			
			try {
				if(out != null)
					out.close();

				if(in != null)
					in.close();
				
				if(connexion != null)
					connexion.close();
				
				System.out.println("bloc finally atteint");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} //Fin méthode run
}