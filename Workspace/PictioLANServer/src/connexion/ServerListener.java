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

public class ServerListener implements Runnable {

	ServerSocket socket;
	
	//TODO : Mettre a jour l'attribut pour gérer une liste de client
	ClientHandler client;
	
	//TODO : Liste de joueur authentifier actif.
	
	public ServerListener(int port) throws IOException {
		socket = new ServerSocket(port);
		client = new ClientHandler();
	}

	//TODO : Mettre à jour la méthode pour qu'elle accepte plusieurs connexion
	public void run() {
			
		try {
			Socket s = socket.accept();
			client = new ClientHandler(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}

