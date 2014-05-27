package connexion;

/**
 * Derni�re mise � jour : 6 mai 2014
 * 
 * Cette classe contr�le le mod�le Gamer et le mod�le ControlGame (qui est un crontr�leur)
 * 
 * En plus de la cr�ation des instances cette classe s'occupe de transmettre 
 * tous les messages re�u du socket et de l'authentification du gamer.
 * 
 * Cette classe peut fermer un socket et mettre fin � la connexion du joueur.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import game.*;
import gamer.ManageGamer;

public class ClientHandler implements Runnable {
		
	Socket connexion = null;
	
	ActiveGamer gamer;
	ControlGame ctrGame;

	BufferedReader in;
	PrintWriter out;
	
	public ClientHandler(){}
	
	public ClientHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new PrintWriter(connexion.getOutputStream());
		
		//authentification_Client();
	}
	
	//TODO : utilisr la m�thode authentification du Gamer pour authentifier la connexion
	//Si l'authentification �choue, on ferme le socket et la connexion.
	public boolean authentification_Client() throws IOException {
		
		String pseudo = in.readLine();
		String pass = in.readLine();
		
		gamer = ManageGamer.authentification_BD(pseudo, pass);
		
		if(gamer == null)
			return false;
		else
			return true;
	}
	
	
	public void run() {
		
		
	}

}