package connection;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DrawingConnnection implements Runnable {
	
	int drawing_port = 3334;

	Socket socketDrawing;
	
	BufferedReader inDrawing;
	BufferedWriter outDrawing;
	
	Thread drawing;
	
	boolean isConnect = false;
	
	boolean endConnection = false;
	
	public DrawingConnnection () throws IOException {
		
		socketDrawing = new Socket(PictioLan.modele_gamer.getIP(), drawing_port);
		
		inDrawing = new BufferedReader(new InputStreamReader(socketDrawing.getInputStream()));
		outDrawing = new BufferedWriter(new OutputStreamWriter(socketDrawing.getOutputStream()));
		
		drawing = new Thread(this);
		drawing.start();
	}
	
	public boolean isConnect() { return isConnect; };

	public void startDraw() {
		
		if(isConnect) {
			drawing = new Thread(this);
			drawing.start();
			auth_protcole();
		}
	}
	
	public boolean auth_protcole() { 
		try {
			
			outDrawing.write("AUTH\n");
			
			String pseudo = PictioLan.modele_gamer.getPseudo();
			String pass = PictioLan.modele_gamer.getPassword();
				
			outDrawing.write(pseudo + "\n");
			outDrawing.flush();
			outDrawing.write(pass + "\n");
			outDrawing.flush();
				
			String rep = inDrawing.readLine();
				
			if(rep.equals("AUTH_SUCCESSFUL")) {
				isConnect = true;
				endConnection = false;
			}
			else {
				isConnect = false;
				endConnection = true;
			}
			
		} catch(IOException e) { 
			e.printStackTrace();
		}
		
		return isConnect;
	}
		
	public void sendMessage(Point p) throws IOException {
		outDrawing.write(p.x);
		outDrawing.flush();
		outDrawing.write(p.y);
		outDrawing.flush();
		
		System.out.println("ENVOIE POINT");
	}
	
	public void closeDraw() {
		endConnection = true;
	}
	
	public void run() {
		
		int x = 0;
		int y = 0;
		
		while(true) {
			
			try {
				System.out.println("Attente");
				
				x = inDrawing.read();
				y = inDrawing.read();
				
				if(PictioLan.modele_gamer.getGame().getClient() != null)
					PictioLan.modele_gamer.getGame().getClient().getDraw().addPoint(new Point(x,y));
				
				System.out.println("RECU POINT");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				
				try {
					if(inDrawing != null) 
						inDrawing.close();
					
					if(outDrawing != null) 
						outDrawing.close();
					
					if(socketDrawing !=  null)
						socketDrawing.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}

