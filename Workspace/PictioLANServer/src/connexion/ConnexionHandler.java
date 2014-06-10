package connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import dictionary.Dictionary;
import game.*;
import gamer.ManageGamer;

public class ConnexionHandler implements Runnable {
	
	Socket connexion = null;
	
	ActiveGamer gamer;
	
	
	Thread threadConnexion;

	BufferedReader in;
	BufferedWriter out;
	
	public ConnexionHandler(Socket s) {
		
		connexion = s;
		threadConnexion = new Thread(this);
		threadConnexion.start();
	}
	
	public void auth_protocole(String type_auth) throws IOException {
		
		String pseudo = "";
		String pass = "";
		String email = "";
		
		if(type_auth.equals("AUTH_CONNECT")) {
			
			pseudo = in.readLine();
			pass = in.readLine();
			
			gamer = ManageGamer.authentification_BD(pseudo, pass);
			
			if(gamer != null)
				out.write("AUTH_SUCCESSFUL\n");
			else
				out.write("AUTH_FAILED\n");
			
		}
		else if(type_auth.equals("AUTH_SUBSCRIBE")) {
			
			pseudo = in.readLine();
			pass = in.readLine();
			email = in.readLine();
			
			gamer = ManageGamer.addGamer(pseudo, pass, email);
			
			if(gamer != null)
				out.write("AUTH_SUCCESSFUL\n");
			else
				out.write("AUTH_FAILED\n");			
			
		}
		else if(type_auth.equals("AUTH_ANONYMOUS")) {
			
			gamer = new ActiveGamer("Anonyme_" + ManageGamer.idAnonyme);
			out.write("Anonyme_" + ManageGamer.idAnonyme + "\n");
			ManageGamer.idAnonyme++;
		}
		
		out.flush();
		
		//DEBUG
		System.out.println("DEBUG - Utilisateur connecte, serveur en attente !");
		
		if(gamer != null)
			System.out.println(gamer.getPseudo());
		else
			System.out.println("DEBUG - NULL");

	}
	
	public void game_protocole(String type_game) throws IOException {
		
		if (type_game.equals("GAME_CREATE")) {
			
			int nbRound = in.read();
			int nbrGamers = in.read();
			String category = in.readLine();
			
			boolean mode = Boolean.parseBoolean(in.readLine());
			
				
			
			gamer.setCtrGame(new Game(nbrGamers,nbRound,category,mode,gamer));
			
			out.write(gamer.getCtrGame().getGameID());
			out.flush();
		}
		else if(type_game.equals("GAME_JOIN")) {
			
			int id_game = in.read();
			
			for(Game g : Game.getGamesList()) {
				if(g.getGameID() == id_game){
					gamer.setCtrGame(g);
					g.addGamer(gamer);
				}
			}
			
			out.write("JOIN_SUCCESS\n");
			out.flush();
			
		}
		else if(type_game.equals("GAME_LIST")) {
			out.write(Game.getGamesList().size());
			out.flush();
			for(Game g : Game.getGamesList()) {
				out.write(g.getGameID());
				out.write(g.getCreator().getPseudo() + "\n");
				out.write(new Boolean(g.isTeamGame()).toString() + "\n");
				out.write(g.getNbrRounds());
				out.write(g.getNbrMaxGamers());
				out.write(g.getListGamer().size());
				out.write(g.getCategory() + "\n");
			
				out.flush();
			}
		}
		else if(type_game.equals("CATEGORY_LIST")) {
			LinkedList<String> cat = Dictionary.getListCategory();
			out.write(cat.size());
			out.flush();
			for(String s : cat) {
				out.write(s+"\n");
				out.flush();
			}
		}
	}
	
	public void run() {
		
		try {
			
			in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
			out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));	
			
			String protocole;
			
			while(true) {
				System.out.println("Debug - protocole ..");
				protocole = in.readLine();
				System.out.println("Debug - protocole=" + protocole);
				
				if(protocole == null)
					break;
				
				if(protocole.equals("AUTH_CONNECT") || protocole.equals("AUTH_SUBSCRIBE") || protocole.equals("AUTH_ANONYMOUS")) {
					auth_protocole(protocole);
				}
				else if (protocole.equals("GAME_CREATE") || protocole.equals("GAME_JOIN") || protocole.equals("GAME_LIST")|| protocole.equals("CATEGORY_LIST")) { 
					game_protocole(protocole);
				}
					
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		finally {
			
			try {
				if(connexion != null)
					connexion.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} //Fin méthode run
}