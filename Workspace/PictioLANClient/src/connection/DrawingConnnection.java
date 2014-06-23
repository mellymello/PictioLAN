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
	
	
	public void sendMessage(int x, int y) throws IOException {
		
		if(PictioLan.modele_gamer.getGame().isStart()) {
		
			outDrawing.write("DRAW_MESSAGE\n");
			outDrawing.flush();
			
			BufferedImage tmp = PictioLan.modele_gamer.getGame().getClient()
					.getDrawedImage();
			
			
			OutputStream outStream = socketDrawing.getOutputStream();

			if (tmp == null) {
				tmp = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
			}

			ImageIO.write(tmp, "png", outStream);
			outStream.flush();
			System.out.println("CLI : img sended !!!");
			
//			//Envoyer un message [DRAW_MESSAGE]
//			outDrawing.write("DRAW_MESSAGE\n");
//			outDrawing.flush();
//			
//			//Envoyer le point
//			outDrawing.write(x);
//			outDrawing.flush();
//			outDrawing.write(y);
//			outDrawing.flush();
//	
//			System.out.println("Envoyer(" + x +  "," + y + ")");
		}
	}
	

	private void getMessage() throws IOException {
	
		
		BufferedImage recvImg = ImageIO.read(ImageIO
				.createImageInputStream(socketDrawing.getInputStream()));

		System.out.println("Client Image received!!!!");

		try {
			ImageIO.write(recvImg, "png", new File(
					"C:/Users/RighitZ/Desktop/rcvUSR.png"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Point
//		int x = inDrawing.read();
//		int y = inDrawing.read();
//		
//		System.out.println("Recevoir(" + x +  "," + y + ")");
//		
//		if(PictioLan.modele_gamer.getGame().getClient() != null) {
//			PictioLan.modele_gamer.getGame().getClient().getDraw().addPoint(new Point(x,y));
//		}
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

