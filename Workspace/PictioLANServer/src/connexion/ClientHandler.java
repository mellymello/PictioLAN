package connexion;

/**
 * Dernière mise à jour : 6 mai 2014
 * 
 * Cette classe contrôle le modèle Gamer et le modèle ControlGame (qui est un crontrôleur)
 * 
 * En plus de la création des instances cette classe s'occupe de transmettre 
 * tous les messages reçu du socket et de l'authentification du gamer.
 * 
 * Cette classe peut fermer un socket et mettre fin à la connexion du joueur.
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
	
	//TODO : utilisr la méthode authentification du Gamer pour authentifier la connexion
	//Si l'authentification échoue, on ferme le socket et la connexion.
	public void Authentification() {
		
	}
}