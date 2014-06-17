package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.ManagerGamer;
import game.*;

public class DrawingHandler implements Runnable {
		
	Socket connexion = null;
	Thread threadDrawing;
	
	//InputStreamReader in;
	//OutputStreamWriter out;
	
	BufferedReader in;
	BufferedWriter out;
	
	boolean endConnection = false;
	
	Gamer gamer = null;
	
	public DrawingHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
		
		threadDrawing = new Thread(this);
		threadDrawing.start();
	}
	
	public void close() {
		endConnection = true;
	}
	
	public void send(String s) { 
		try {
			out.write(s+"\n");
			out.flush();
			System.out.println("TRANSMI : " + s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {

		System.out.println("SERVER CHAT DEM");
	
		try {
			
			String pseudo = in.readLine();
			String pass = in.readLine();
			
			gamer = ManagerGamer.authentification_BD(pseudo, pass);
			
			if(gamer != null) {
				out.write("AUTH_SUCCESSFUL\n");
				gamer.setDrawing(this);
			}
			else
				out.write("AUTH_FAILED\n");
			
			while(!endConnection) {
				
				String tmp = in.readLine();
				System.out.println("RECU SERVEUR: " + tmp);
				
				if(gamer.getGame() != null) {
					for(DrawingHandler c : gamer.getGame().getListDrawing()) {
						if(this != c)
							c.send(tmp);
					}
				}
			} // fin de la boucle
			
		} catch (IOException e) {
			e.printStackTrace();
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
	}

}