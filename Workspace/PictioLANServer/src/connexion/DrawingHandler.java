package connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import game.*;
import gamer.ManageGamer;

public class DrawingHandler implements Runnable {
		
	Socket connexion = null;
	
	ActiveGamer gamer;
 

	InputStreamReader in;
	OutputStreamWriter out;
	
	public DrawingHandler(){}
	
	public DrawingHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in =  new InputStreamReader (connexion.getInputStream());
		out = new OutputStreamWriter(connexion.getOutputStream());
		
		//Récupérer le pseudo + passeword
		
		ManageGamer.authentification_BD("magali", "1234");
	}
	
	public void run() {
		
		
	}

}