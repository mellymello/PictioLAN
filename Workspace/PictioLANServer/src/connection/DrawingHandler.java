package connection;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.ManagerGamer;
import game.*;

public class DrawingHandler implements Runnable {
	
	Gamer gamer;
	
	Socket connexion = null;
	Thread threadDrawing;
	
	//InputStreamReader in;
	//OutputStreamWriter out;
	
	BufferedReader in;
	BufferedWriter out;
	
	boolean endConnection = false;
	
	public DrawingHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
		
		threadDrawing = new Thread(this);
		threadDrawing.start();
	}
	
	public void auth_protocole() throws IOException { 
		
		String pseudo = in.readLine();
		String pass = in.readLine();
		
		gamer = ManagerGamer.authentification_BD(pseudo, pass);
		
		if(gamer != null) {
			out.write("AUTH_SUCCESS\n");
			out.flush();
			
			gamer.setDrawing(this);
		}
		else {
			out.write("AUTH_FAILED\n");
			out.flush();
		}
	}
	
	public void close() {
		endConnection = true;
	}
	
	
	public void send(int x, int y) { 
		try {
			out.write(x);
			out.flush();
			
			out.write(y);
			out.flush();
			
			System.out.println("TRANSMI : ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {

		System.out.println("SERVER Drawing DEM");
	
		try {
			
			while(!endConnection) {
				
				String tmp = in.readLine();
				
				if(tmp.equals("AUTH")) {
					auth_protocole();
				}
				else if(tmp.equals("DRAW_POINT")) {
				
					System.out.println("RECU SERVEUR: " + tmp);
					
					int x = in.read();
					int y = in.read();
					
					if(gamer.getGame() != null) {
						
						for(DrawingHandler c : gamer.getGame().getListDrawing()) {
							if(this != c)
								c.send(x,y);
						}
						
					}
				
				}
				else if(tmp.equals("CLOSE")) {
					endConnection = true;
					break;
				}
				
			} // fin de la boucle
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				if(in != null)
					in.close();
				
				if(out != null)
					out.close();
				
				if(connexion != null)
					connexion.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}