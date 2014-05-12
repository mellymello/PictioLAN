package connexion;

/**
 * Derni�re mise � jour : 6 mai 2014
 * 
 * But : Le but de cette classe et d'impl�menter un thread qui �couter
 * les connexions TCP sur le serveur.
 * 
 * Cette classe impl�mente le contr�leur des connexions au serveur.
 * Il contr�le le mod�le ClientHandler et met � jour la vue JGamer de la classe JServer
 */


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Runnable {

	ServerSocket socket;
	
	//TODO : Mettre a jour l'attribut pour g�rer une liste de client
	ClientHandler client;
	
	//TODO : Liste de joueur authentifier actif.
	
	public ServerListener(int port) throws IOException {
		socket = new ServerSocket(port);
		client = new ClientHandler();
	}

	//TODO : Mettre � jour la m�thode pour qu'elle accepte plusieurs connexion
	public void run() {
			
		try {
			Socket s = socket.accept();
			client = new ClientHandler(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}

