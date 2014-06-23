package connection;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import server.ManagerGamer;
import game.*;

public class DrawingHandler implements Runnable {
	
	Gamer gamer;
	
	Socket connexion = null;
	Thread threadDrawing;
	
	BufferedReader in;
	BufferedWriter out;
	
	boolean endConnection = false;
	
	public DrawingHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
		
		threadDrawing= new Thread(this);
		threadDrawing.start();
	}
	
	public void auth_protocole() throws IOException { 
		
		String pseudo = in.readLine();
		
		gamer = ManagerGamer.getGamer(pseudo);
		
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

	public void recieve() {
		
		try {
			
			int x = in.read();
			int y = in.read();
			
			if(gamer.getGame() != null && !gamer.getGame().getListDrawing().isEmpty()) {
				
				for(DrawingHandler c : gamer.getGame().getListDrawing()) {
					
					if(this != c) { 
						c.out.write(x);
						c.out.write(y);
						c.out.flush();
					}
				}
			}
		
		} catch (IOException e) {
			
		}
		
	}
	
	
	public void close() {
		endConnection = true;
	}
	
	@Override
	public void run() {

		try {
			
			while(!endConnection) {
				
				String tmp = in.readLine();

				if(tmp.equals("AUTH")) {
					auth_protocole();
				}
				else if(tmp.equals("DRAW_MESSAGE")) {
					recieve();
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
//package connection;
//
//import java.awt.Rectangle;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//import java.util.Vector;
//
//import server.ManagerGamer;
//import game.*;
//
//public class DrawingHandler implements Runnable {
//	
//	Gamer gamer;
//	
//	Socket connexion = null;
//	Thread threadDrawing;
//	
//	BufferedReader in;
//	BufferedWriter out;
//	
//	boolean endConnection = false;
//	
//	public DrawingHandler(Socket s) throws IOException {
//		
//		connexion = s;
//		
//		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
//		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
//		
//		threadDrawing= new Thread(this);
//		threadDrawing.start();
//	}
//	
//	public void auth_protocole() throws IOException { 
//		
//		String pseudo = in.readLine();
//		
//		gamer = ManagerGamer.getGamer(pseudo);
//		
//		if(gamer != null) {
//			out.write("AUTH_SUCCESS\n");
//			out.flush();
//			
//			gamer.setDrawing(this);
//		}
//		else {
//			out.write("AUTH_FAILED\n");
//			out.flush();
//		}
//	}
//
//	public void recieve(String msg) {
//		
//		try {
//			
//			//Recevoir Point
//			int x = in.read();
//			int y = in.read();
////			int h = in.read();
////			int w = in.read();
//				
//			System.out.println("Recevoir(" + x +  "," + y + ")");
//			
//			for(Gamer g : gamer.getGame().getListGamer()) {
//				
//				if(g != gamer) {
//					g.getDrawing().out.write(msg + "\n");
//					g.getDrawing().out.flush();
//					
//					g.getDrawing().out.write(x);
//					g.getDrawing().out.flush();
//					g.getDrawing().out.write(y);
//					g.getDrawing().out.flush();
////					g.getDrawing().out.write(h);
////					g.getDrawing().out.flush();
////					g.getDrawing().out.write(w);
////					g.getDrawing().out.flush();
//					
//					System.out.println("Envoyer(" + x +  "," + y + ")");
//				}
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void close() {
//		endConnection = true;
//	}
//	
//	@Override
//	public void run() {
//
//		try {
//			
//			while(!endConnection) {
//				
//				String tmp = in.readLine();
//
//				if(tmp.equals("AUTH")) {
//					auth_protocole();
//				}
//				else if(tmp.equals("DRAW_MESSAGE") || tmp.equals("DRAW_ERASE")) {
//					recieve(tmp);
//				}
//				else if(tmp.equals("CLOSE")) {
//					endConnection = true;
//					break;
//				}
//				
//			} // fin de la boucle
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		finally {
//			
//			try {
//				if(in != null)
//					in.close();
//				if(out != null)
//					out.close();
//				if(connexion != null)
//					connexion.close();
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//}