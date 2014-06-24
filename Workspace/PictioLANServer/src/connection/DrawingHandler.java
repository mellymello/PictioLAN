package connection;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import javax.imageio.ImageIO;

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

			
			if(gamer.getGame() != null && !gamer.getGame().getListDrawing().isEmpty()) {
				
				
					BufferedImage recvImg = ImageIO.read(ImageIO
							.createImageInputStream(connexion.getInputStream()));

					System.out.println("SERVER: Imag received!!!!");


					if (recvImg == null) {
						recvImg = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
					}
					
					// store the image on server current game:
					gamer.getGame().drawedImage = recvImg;
					

			
				
				for(DrawingHandler c : gamer.getGame().getListDrawing()) {
					
					if(this != c) { 
						
		
						ImageIO.write(recvImg, "png", connexion.getOutputStream());
						out.flush();
						System.out.println("SERVER : img sent !!!");

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
