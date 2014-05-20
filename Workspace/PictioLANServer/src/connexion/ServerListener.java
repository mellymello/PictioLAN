package connexion;

/**
 * Dernière mise à jour : 6 mai 2014
 * 
 * But : Le but de cette classe et d'implémenter un thread qui écouter
 * les connexions TCP sur le serveur.
 * 
 * Cette classe implémente le contrôleur des connexions au serveur.
 * Il contrôle le modèle ClientHandler et met à jour la vue JGamer de la classe JServer
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
	
	//Modèle et vue pour la configuration
	int chat_port = 3333;
	int drawing_port = 3334;
	int connexion_port = 3335;
	
	ChatListener chat;
	DrawingListener drawing;
	ConnexionListener connexion;

	Thread threadChat;
	Thread threadDrawing;
	Thread threadConnexion;

	public ServerListener () {
		
		System.out.println("DEBUG - new ServerListener");
		
		try {
			//chat = new ChatListener();
			//drawing = new DrawingListener();
			connexion = new ConnexionListener();
			
			//threadChat = new Thread(chat);
			//threadDrawing = new Thread(drawing);
			
			//threadChat.start();
			//threadDrawing.start();
			
			
		} catch (IOException e) {
			System.out.println("Error : ServerListener");
			e.printStackTrace();
		}
	}
	
	//Socket ChatListener
	class ChatListener implements Runnable {
		
		ChatHandler client;
		ServerSocket socketChatListener;
		
		public ChatListener () throws IOException {
			socketChatListener = new ServerSocket(chat_port);
		}
		
		public void run() {
			
			try {
				Socket s = socketChatListener.accept();
				client = new ChatHandler(s);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Socket ChatListener
	class DrawingListener implements Runnable {
		
		DrawingHandler client;
		ServerSocket socketChatListener;
		
		public DrawingListener() throws IOException {
			socketChatListener = new ServerSocket(drawing_port);
		}
		
		public void run() {
			
			try {
				Socket s = socketChatListener.accept();
				client = new DrawingHandler(s);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Socket ConnexionListener
	class ConnexionListener implements Runnable {
		
		ConnexionHandler client;
		ServerSocket socketConnexionListener = null;
		Socket s = null;
		
		public ConnexionListener () throws IOException {
			threadConnexion = new Thread(this);
			threadConnexion.start();
		}
		
		public void run() {
			
			try {
				socketConnexionListener = new ServerSocket(connexion_port);
				s = socketConnexionListener.accept();
				client = new ConnexionHandler(s);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				
				try {
					if(socketConnexionListener != null)
						socketConnexionListener.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

