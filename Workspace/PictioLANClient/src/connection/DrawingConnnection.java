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

public class DrawingConnnection implements Runnable {
	
	int drawing_port = 3334;

	Socket socketDrawing;
	
	BufferedReader inDrawing;
	BufferedWriter outDrawing;
	
	Thread drawing;
	
	boolean endConnection = true;
	
	public DrawingConnnection () throws IOException {
		
		socketDrawing = new Socket(PictioLan.modele_gamer.getIP(), drawing_port);
		inDrawing = new BufferedReader(new InputStreamReader(socketDrawing.getInputStream()));
		outDrawing = new BufferedWriter(new OutputStreamWriter(socketDrawing.getOutputStream()));
	}

	public boolean isConnect() { return !endConnection; };

	public void startDraw() {
		
		boolean actif = auth_protcole();
		
		if(!actif) {
			drawing = new Thread(this);
			drawing.start();
		}
	}
	
	private boolean auth_protcole() { 
		
		try {
			
			outDrawing.write("AUTH\n");
			outDrawing.flush();
			
			String pseudo = PictioLan.modele_gamer.getPseudo();
				
			outDrawing.write(pseudo + "\n");
			outDrawing.flush();
				
			String rep = inDrawing.readLine();
			
			endConnection = !rep.equals("AUTH_SUCCESS");
			
		} catch(IOException e) { 
			e.printStackTrace();
		}
		
		return endConnection;
	}
	
	
	private void sendMessage() throws IOException {
	
		Vector<Point> temp = PictioLan.modele_gamer.getGame().getClient().getDrawedPoint();
		
		outDrawing.write("DRAW_SEND_MESSAGE\n");
		outDrawing.flush();
		
		outDrawing.write(temp.size());
		outDrawing.flush();
		
		for(int i=0; i < temp.size(); i++) {
			
			outDrawing.write(temp.get(i).x);
			outDrawing.write(temp.get(i).x);
			outDrawing.flush();
		}
		
		System.out.println("Envoie image");
	}
	


private void getMessage() throws IOException {
	
	Vector<Point> buffer = new Vector<Point>();
	
	outDrawing.write("DRAW_GET_MESSAGE\n");
	outDrawing.flush();
	
	int size = inDrawing.read();

	for(int i=0; i < size; i++) {
		
		int x = inDrawing.read();
		int y = inDrawing.read();
		
		buffer.add(new Point(x,y));
	}
	
	if(PictioLan.modele_gamer.getGame().getClient() != null)
		PictioLan.modele_gamer.getGame().getClient().setPoint(buffer);
}
	
	
	public void closeDraw() {
		endConnection = true;
	}
	
	public void run() {
		
		try {
			
			while (!endConnection) {
				
					
				if(PictioLan.modele_gamer.getGame().isDrawer()) {
					sendMessage();
					
				} else {
					getMessage();
				}
				
				try {
					drawing.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			outDrawing.write("CLOSE\n");
			outDrawing.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (inDrawing != null)
					inDrawing.close();

				if (outDrawing != null)
					outDrawing.close();

				if (socketDrawing != null)
					socketDrawing.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

