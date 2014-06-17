package connection;

import game.Gamer;
import gui.JClient;
import gui.JDraw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DrawingConnnection implements Runnable {
	
	int drawing_port = 3334;

	Gamer gamer;
	
	JClient vue;
	
	Socket socketDrawing;
	
	BufferedReader inDrawing;
	BufferedWriter outDrawing;
	
	Thread drawing;
	
	public DrawingConnnection (Gamer g, JClient cli) throws IOException {
		
		gamer = g;
		
		socketDrawing = new Socket(gamer.getIP(), drawing_port);
		
		vue = cli;
		
		inDrawing = new BufferedReader(new InputStreamReader(socketDrawing.getInputStream()));
		outDrawing = new BufferedWriter(new OutputStreamWriter(socketDrawing.getOutputStream()));
		
		drawing = new Thread(this);
		drawing.start();
	}
	
	public void run() {
		//Envoyer le pseudo + passeword
	}

}

