package connection;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

public class DrawingConnnection implements Runnable {
	
	int drawing_port = 3334;

	Socket socketDrawing;
	
	BufferedReader inDrawing;
	BufferedWriter outDrawing;
	
	Thread drawing;
	
	boolean endConnection = true;
	
	LinkedList<Point> buffer = new LinkedList<Point>();
	
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
	
	public boolean isBufferEmpty() {
		return buffer.isEmpty();
	}
	
	public void addPointToBuffer(Point s) {
		buffer.add(s);
	}
	
	public LinkedList<Point> getPointsToBuffer() {
		
		LinkedList<Point> temp = new LinkedList<Point>();
		
		for(Point s : buffer)
			temp.add(s);
		
		return temp;
	}
	
	public void removeMessagesToBuffer() {
		buffer.clear();
	}
	
	private void sendMessage() throws IOException {
		
		LinkedList<Point> temp = getPointsToBuffer();
		
		for(Point msg : temp) {
			
			outDrawing.write("DRAW_SEND_MESSAGE\n");
			outDrawing.flush();
			
			outDrawing.write(msg.x);
			outDrawing.flush();
			
			outDrawing.write(msg.y);
			outDrawing.flush();
			
			System.out.println("Envoie "+ msg.x +","+ msg.y);
		}
		
		removeMessagesToBuffer();
	}

	private void getMessage() throws IOException {
		
		outDrawing.write("DRAW_GET_MESSAGE\n");
		outDrawing.flush();
		
		int nbMessages = inDrawing.read();

		for(int i=0; i < nbMessages; i++) {
			
			int x = inDrawing.read();
			int y = inDrawing.read();
			
			System.out.println("Reçu "+ x +","+ y);
			
			if(PictioLan.modele_gamer.getGame().getClient() != null)
				PictioLan.modele_gamer.getGame().getClient().getDraw().addPoint(new Point(x,y));
		}
	}
	
	
	public void closeDraw() {
		endConnection = true;
	}
	
	public void run() {
		

		try {
			
			while (!endConnection) {
				
				boolean launchGame = PictioLan.modele_gamer.getGame().getListGamers().size() == PictioLan.modele_gamer.getGame().getNbMaxGamers();
				
				if(launchGame) {
				
					if(!isBufferEmpty()) {
						sendMessage();
					}
					
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

