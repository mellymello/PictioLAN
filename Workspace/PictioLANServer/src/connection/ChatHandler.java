package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.ManagerGamer;
import game.*;

public class ChatHandler implements Runnable {
	
	Socket connexion = null;
	Thread threadChat;
	
	BufferedReader in;
	BufferedWriter out;
	
	boolean endConnection = false;
	
	Gamer gamer = null;
	
	public ChatHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
		
		threadChat = new Thread(this);
		threadChat.start();
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
	
	public void close() {
		endConnection = true;
	}
	
	public void run() {

		System.out.println("SERVER CHAT DEM");
	
		try {
			
			String pseudo = in.readLine();
			String pass = in.readLine();
			
			gamer = ManagerGamer.authentification_BD(pseudo, pass);
			
			if(gamer != null) {
				out.write("AUTH_SUCCESSFUL\n");
				gamer.setChat(this);
			}
			else
				out.write("AUTH_FAILED\n");
			
			while(!endConnection) {
				
				String tmp = in.readLine();
				
				if(gamer.getGame() != null && gamer.getGame().getWord() == tmp) {
					
					gamer.getGame().winWord(gamer);
				}
				
				System.out.println("RECU SERVEUR: " + tmp);
				
				if(gamer.getGame() != null) {
					for(ChatHandler c : gamer.getGame().getListChat()) {
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