package connection;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Vector;

import javax.imageio.ImageIO;

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
	
	
	public void sendMessage() throws IOException {
		
		if(PictioLan.modele_gamer.getGame().isStart()) {
		
			outDrawing.write("DRAW_MESSAGE\n");
			outDrawing.flush();
			
			BufferedImage tmp = PictioLan.modele_gamer.getGame().getClient().getDraw().getImage();
			
			OutputStream outStream = socketDrawing.getOutputStream();

			if (tmp == null) {
				tmp = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
			}

			ImageIO.write(tmp, "png", outStream);
			outStream.flush();
			System.out.println("CLIENT : img sent !!!");
			
		}
	}
	

	private void getMessage() throws IOException {
	
		
		BufferedImage recvImg = ImageIO.read(ImageIO
				.createImageInputStream(socketDrawing.getInputStream()));

		System.out.println("CLIENT:  img received!!!!");

		try {
			
			PictioLan.modele_gamer.getGame().getClient().getDraw().setImage(recvImg);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		

	}
	
	
	public void closeDraw() {
		endConnection = true;
	}
	
	public void run() {
		
		try {
			
			while (!endConnection) {
				
				if(PictioLan.modele_gamer.getGame().isStart() && !PictioLan.modele_gamer.getGame().isDrawer()) {
					getMessage();
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

