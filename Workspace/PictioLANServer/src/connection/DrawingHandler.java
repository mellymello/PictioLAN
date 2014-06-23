package connection;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
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
	
	Vector<Point> buffer = new Vector<Point>();
	
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
		
		buffer.clear();
		
		try {
			
			int size = in.read();
			
			for(int i=0;  i < size; i++) {
				int x = in.read();
				int y = in.read();
				
				buffer.add(new Point(x,y));
			}
			
		} catch (IOException e) {
			
		}
		
	}
	
	public void send() { 
		
		try {
			
			out.write(buffer.size());
			out.flush();
			
			for(int i=0;  i < buffer.size(); i++) {
				out.write(buffer.get(i).x);
				out.write(buffer.get(i).y);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendError() {
		try {
			out.write(0);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
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
				else if(tmp.equals("DRAW_GET_MESSAGE")) {
					
					if(gamer.getGame() != null) {
						
						if(gamer.getGame().getListDrawing().isEmpty()) {
							sendError();
						}
						else {
						
							for(DrawingHandler c : gamer.getGame().getListDrawing()) {
								if(this != c) 
									c.send();
							}
						}
						
					}
					else {
						sendError();
					}
				
				}
				else if(tmp.equals("DRAW_SEND_MESSAGE")) {
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