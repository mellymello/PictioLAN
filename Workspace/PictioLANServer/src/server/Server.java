package server;

/**
 * Dernière mise à jour : 6 mai 2014
 * 
 * But : Cette classe implémente le contrôleur du serveur. 
 * Le contrôleur instancie différents modèles pour la configuration et 
 * la gestion des parties, des joueurs, etc.
 * 
 * Cela correspond aux modèles : Configuration, Dictionary, Game, Gamer.
 * 
 * Chacun de ses modèles possède une sous-vue qui fera partie de la vue principale JServer.
 * 
 * Cela correspond aux vues : JConfiguration, viewDictionary, JGame, JGamer
 * 
 * Pour finir le contrôleur met à dispositon deux méthodes (start/stop) 
 * qui permmettent de lancer un thread pour accepter les connexions TCP des joueurs.
 */

import gui.JConfiguration;
import gui.JServer;
import gui.ViewDictionary;

import java.io.IOException;
import connexion.*;
import dictionary.Dictionary;

public class Server {
	
	//Modèle Listener et vue des joueurs actifs
	private Thread threadListener;
	private ServerListener listener;
	
	//Modèle et vue pour la configuration
	int port;
	private JConfiguration viewConfig;
	
	//Modèle et vue pour la gestion du dictionnaire
	private Dictionary dictionary;
	private ViewDictionary viewDictionary;
	
	//TODO Modèle et vue pour la gestion des parties actives
	
	
	//TODO Modèle et vue pour la gestion des joueurs (inscrits)
	
	
	//Vue pricipale
	JServer viewServer;
	
	public Server(int port ) {

		this.port = port;
		
		//Création Listener
		try {
			listener = new ServerListener(port);
		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		//Création configuration
		viewConfig = new JConfiguration(this);
		
		//Création Dictionnaire
		dictionary = new Dictionary();
		viewDictionary = new ViewDictionary(dictionary);
		
		//TODO Création éléments pour la gestion des joueurs
		
		//TODO Création éléments pour la gestion des parties en cours
		
		viewServer = new JServer(viewConfig, viewDictionary);
	}
	
	//Modèle pour la configuration
	public void setPort(int p) { port = p; }
	public int getPort(int p) { return port; }
	
	//Lance le thread qui permet d'écouter le socket
	public void startListener() {
		threadListener.start();
	}
	
	//TODO arrête le thread qui permet d'écouter le socket
	public void stopListener() {
		
	}
	
	public static void main (String[] args) {
		Server server = new Server(3333);
	}
}
