package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionServer {

	String pseudo = "Player1";
	String password = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";
	String email;
	
	String IPserver = "";
	int portConnexion = 3336;
	
	ConnexionListener connexion;
	Thread threadConnexion;
	
	public ConnectionServer() {
 
	}
	
	public void launchConnexion() {
		try {
			connexion = new ConnexionListener();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void setIP(String ip) {
		IPserver = ip;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public void setEmail(String e) {
		email = e;
	}
	
	public void authentification(String msg){
		connexion.authentification(msg);
	}
	
	//Socket ChatListener
 	class ChatListener implements Runnable {
		
		Socket socketChat;
		
		BufferedReader inChat;
		BufferedWriter outChat;
		
		public ChatListener () throws IOException {
			socketChat = new Socket(IPserver, 3333);
			inChat = new BufferedReader(new InputStreamReader(socketChat.getInputStream()));
			outChat = new BufferedWriter(new OutputStreamWriter(socketChat.getOutputStream()));
		}
		
		public void run() {
			
		}
	}
	
	//Socket ChatListener
	class DrawingListener implements Runnable {
		
		Socket socketDrawing;
		
		InputStreamReader inDrawing;
		OutputStreamWriter outDrawing;
		
		public DrawingListener () throws IOException {
			socketDrawing = new Socket(IPserver, 3334);
			inDrawing = new InputStreamReader(socketDrawing.getInputStream());
			outDrawing = new OutputStreamWriter(socketDrawing.getOutputStream());
		}
		
		public void run() {
			//Envoyer le pseudo + passeword
		}
	}
	
	//Socket ConnexionListener
	class ConnexionListener implements Runnable {
		
		Socket socketConnexion = null;
		
		InputStreamReader inConnexion;
		OutputStreamWriter outConnexion;
		
		public ConnexionListener () throws IOException {
			
			socketConnexion = new Socket(IPserver, portConnexion);
			
			inConnexion = new InputStreamReader(socketConnexion.getInputStream());
			outConnexion = new OutputStreamWriter(socketConnexion.getOutputStream());	
		
			threadConnexion = new Thread(this);
			threadConnexion.start();
		}
		
		public void authentification(String msg) {
			try {
				outConnexion.write(msg+"\n");
				outConnexion.flush();
				
				if(msg.equals("AUTH_ANONYMOUS")) {
					outConnexion.write("ANONYMOUS_" + pseudo + "\n");
					outConnexion.flush();
				}
			
				else if(msg.equals("AUTH_CONNECT")) {
					outConnexion.write(pseudo + "\n");
					outConnexion.flush();
					outConnexion.write(password + "\n");
					outConnexion.flush();
				}
				
				else if(msg.equals("AUTH_SUBSCRIBE")) {
					outConnexion.write(pseudo + "\n");
					outConnexion.flush();
					outConnexion.write(password + "\n");
					outConnexion.flush();
					outConnexion.write(email + "\n");
					outConnexion.flush();
				}
				
				outConnexion.flush();
				
			} catch (IOException e) {
				System.out.println("Error : Connexion impossible");
				e.printStackTrace();
			}
			finally {
				
				try {
					if(socketConnexion != null)
						socketConnexion.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		public void run() {
			
		}
	}
} 
