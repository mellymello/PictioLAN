package connexion;

/**
 * Derni�re mise � jour : 6 mai 2014
 * 
 * Cette classe contr�le le mod�le Gamer et le mod�le ControlGame (qui est un crontr�leur)
 * 
 * En plus de la cr�ation des instances cette classe s'occupe de transmettre 
 * tous les messages re�u du socket et de l'authentification du gamer.
 * 
 * Cette classe peut fermer un socket et mettre fin � la connexion du joueur.
 */

import java.net.Socket;
import game.*;

public class ClientHandler {
	
	Socket connexion;
	ActiveGamer gamer;
	ControlGame ctrGame;
	
	public ClientHandler(){}
	
	public ClientHandler(Socket s) {
		connexion = s;
	}
	
	public void setConnexion(Socket s) {
		connexion = s;
	}
	
	//TODO : utilisr la m�thode authentification du Gamer pour authentifier la connexion
	//Si l'authentification �choue, on ferme le socket et la connexion.
	public void Authentification() {
		
	}
}