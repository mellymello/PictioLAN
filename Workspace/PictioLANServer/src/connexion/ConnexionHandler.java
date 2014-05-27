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
	
	public ConnexionHandler(){}
	
	public ConnexionHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));	
		
		threadConnexion = new Thread(this);
		threadConnexion.start();
	}
	
	public void run() {
		
		String type_auth;
		
		String pseudo = "";
		String pass = "";
		String email = "";
		
		try {
			type_auth = in.readLine();
			pseudo = in.readLine();
			
			if(type_auth.equals("AUTH_CONNECT")) {
				pass = in.readLine();
				gamer = ManageGamer.authentification_BD(pseudo, pass);
			}
			else if(type_auth.equals("AUTH_SUBSCRIBE")) {
				pass = in.readLine();
				email = in.readLine();
				gamer = ManageGamer.addGamer(pseudo, pass, email);
			}
			else if(type_auth.equals("AUTH_ANONYMOUS")) {
				gamer = new ActiveGamer(pseudo,true);
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

		System.out.println("DEBUG - Utilisateur connecte, serveur en attente !");
		
		if(gamer != null)
			System.out.println(gamer.getPseudo());
		else
			System.out.println("DEBUG - NULL");
	}
}