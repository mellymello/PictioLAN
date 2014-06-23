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
	
	Vector<Point> debug = new Vector<Point>();

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
	
		Vector<Point> temp = debug;//PictioLan.modele_gamer.getGame().getClient().getDrawedPoint();
		
		outDrawing.write("DRAW_SEND_MESSAGE\n");
		outDrawing.flush();
		
		outDrawing.write(temp.size());
		outDrawing.flush();
		
		System.out.print("Envoie image [" +  temp.size() + "]: ");
		
		for(int i=0; i < temp.size(); i++) {
			
			outDrawing.write(temp.get(i).x);
			outDrawing.flush();
			outDrawing.write(temp.get(i).y);
			outDrawing.flush();
			
			System.out.print("(" + temp.get(i).x +  "," + temp.get(i).y + ")");
		}
		
		System.out.println();
	}
	


private void getMessage() throws IOException {
	
	Vector<Point> buffer = new Vector<Point>();
	
	outDrawing.write("DRAW_GET_MESSAGE\n");
	outDrawing.flush();

	int size = inDrawing.read();

	System.out.print("Reçoit image [" + size + "]: ");
	
	for(int i=0; i < size; i++) {
		
		int x = inDrawing.read();
		int y = inDrawing.read();
		
		System.out.print("(" + x +  "," + y + ")");
		
		buffer.add(new Point(x,y));
	}
	
	System.out.println();
	
	if(PictioLan.modele_gamer.getGame().getClient() != null)
		PictioLan.modele_gamer.getGame().getClient().setPoint(buffer);
	
}
	
	
	public void closeDraw() {
		endConnection = true;
	}
	
	public void run() {
		
		debug.add(new Point(103,63));
		debug.add(new Point(103,63));
		debug.add(new Point(104,63));
		debug.add(new Point(106,63));
		debug.add(new Point(110,63));
		debug.add(new Point(115,63));
		debug.add(new Point(119,63));
		debug.add(new Point(122,246));
		debug.add(new Point(124,234));
		debug.add(new Point(127,223));
		debug.add(new Point(63,212));
		debug.add(new Point(63,200));
		debug.add(new Point(63,191));
		debug.add(new Point(63,181));
		debug.add(new Point(63,174));
		debug.add(new Point(63,165));
		debug.add(new Point(63,63));
		debug.add(new Point(63,63));
		debug.add(new Point(164,63));
		debug.add(new Point(171,127));
		debug.add(new Point(178,120));
		debug.add(new Point(186,113));
		
		
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

