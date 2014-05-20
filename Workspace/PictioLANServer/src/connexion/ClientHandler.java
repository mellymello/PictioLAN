package connexion;

/**
 * Dernière mise à jour : 6 mai 2014
 * 
 * Cette classe contrôle le modèle Gamer et le modèle ControlGame (qui est un crontrôleur)
 * 
 * En plus de la création des instances cette classe s'occupe de transmettre 
 * tous les messages reçu du socket et de l'authentification du gamer.
 * 
 * Cette classe peut fermer un socket et mettre fin à la connexion du joueur.
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
	
	//TODO : utilisr la méthode authentification du Gamer pour authentifier la connexion
	//Si l'authentification échoue, on ferme le socket et la connexion.
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