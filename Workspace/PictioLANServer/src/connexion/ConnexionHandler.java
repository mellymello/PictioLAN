package connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import game.*;
import gamer.ManageGamer;

public class ConnexionHandler implements Runnable {
	
	Socket connexion = null;
	
	ActiveGamer gamer;
	ControlGame ctrGame;
	
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
			
			ctrGame = new ControlGame(gamer);
			
			out.write(ctrGame.getIDGame());
			out.flush();
			
			int nb = in.read();
			
			boolean mode = Boolean.parseBoolean(in.readLine());
			
			//Recevoir READY
			ctrGame.setModeEquipe(mode);
			ctrGame.setNbGamers(nb);
		}
		else if(type_game.equals("GAME_JOIN")) {
			
			int id_game = in.read();
			
			for(ControlGame g : ControlGame.listActiveGame) {
				if(g.getIDGame() == id_game)
					ctrGame = g;
			}
			
			out.write("JOIN_SUCCESS");
			
		}
		else if(type_game.equals("GAME_LIST")) {
			out.write(ControlGame.listActiveGame.size());
			out.flush();
			for(ControlGame g : ControlGame.listActiveGame) {
				out.write(g.getIDGame());
				out.write(new Boolean(g.isModeEquipe()).toString() + "\n");
				out.write(g.creatorPseudo() + "\n");
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
				else if (protocole.equals("GAME_CREATE") || protocole.equals("GAME_JOIN") || protocole.equals("GAME_LIST")) { 
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