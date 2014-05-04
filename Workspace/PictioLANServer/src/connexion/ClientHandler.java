package connexion;

import java.net.Socket;
import game.*;

public class ClientHandler {
	
	Socket connexion;
	
	public ClientHandler(){}
	
	public ClientHandler(Socket s) {
		connexion = s;
	}
	
	public void setConnexion(Socket s) {
		connexion = s;
	}
	
	public Gamer Authentification() {
		return new Gamer();
	}
}