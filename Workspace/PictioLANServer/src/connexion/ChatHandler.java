package connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import game.*;
import gamer.ManageGamer;

public class ChatHandler implements Runnable {
		
	Socket connexion = null;
	
	ActiveGamer gamer;
	ControlGame ctrGame;

	BufferedReader in;
	BufferedWriter out;
	
	public ChatHandler(){}
	
	public ChatHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
		
		//Récupérer le pseudo + passeword
		
		gamer = ManageGamer.authentification_BD("magali", "1234");		
	}
	
	public void run() {

		
	}

}