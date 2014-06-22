package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerListener {
	
	//Modèle et vue pour la configuration
	int chat_port = 3333;
	int drawing_port = 3334;
	int connexion_port = 3336;
	
	ChatListener chat;
	DrawingListener drawing;
	ConnexionListener connexion;

	Thread threadChat;
	Thread threadDrawing;
	Thread threadConnexion;

	public ServerListener () {
		connexion = new ConnexionListener();
		chat = new ChatListener();
		drawing = new DrawingListener();
 	}
	
	//Socket ChatListener
	class ChatListener implements Runnable {
		
		LinkedList<ChatHandler> chat = new LinkedList<ChatHandler>();
		ServerSocket socketChatListener;
		
		public ChatListener ()  {
			threadChat = new Thread(this);
			threadChat.start();
		}
		
		public void run() {
			
			try {
				socketChatListener = new ServerSocket(chat_port);
				
				while(true) {
					Socket s = socketChatListener.accept();
					chat.add(new ChatHandler(s));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Socket ChatListener
	class DrawingListener implements Runnable {
		
		LinkedList<DrawingHandler> draw = new LinkedList<DrawingHandler>();
		ServerSocket socketDrawListener;
		Socket s = null;
		
		public DrawingListener() {
			threadDrawing = new Thread(this);
			threadDrawing.start();
		}
		
		public void run() {
			
			try {
				
				socketDrawListener = new ServerSocket(drawing_port);
				
				while(true) {
					Socket s = socketDrawListener.accept();
					draw.add(new DrawingHandler(s));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Socket ConnexionListener
	public class ConnexionListener implements Runnable {
		
		LinkedList<ConnectionHandler> client = new LinkedList<ConnectionHandler>();
		ServerSocket socketConnexionListener = null;
		Socket s = null;
		
		boolean isClose = false;
		
		public ConnexionListener () {
			threadConnexion = new Thread(this);
			threadConnexion.start();
		}
		
		public void closeConnexion() { 
			isClose = true;
		}
		
		public void run() {
			
			try {

				System.out.println("DEBUG - run listener");
				socketConnexionListener = new ServerSocket(connexion_port);
				
				while(!isClose) {
					s = socketConnexionListener.accept();
					client.add(new ConnectionHandler(s));
					System.out.println("DEBUG - client socket");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					socketConnexionListener.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
