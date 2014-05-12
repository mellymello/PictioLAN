package server;

/**
 * Derni�re mise � jour : 6 mai 2014
 * 
 * But : Cette classe impl�mente le contr�leur du serveur. 
 * Le contr�leur instancie diff�rents mod�les pour la configuration et 
 * la gestion des parties, des joueurs, etc.
 * 
 * Cela correspond aux mod�les : Configuration, Dictionary, Game, Gamer.
 * 
 * Chacun de ses mod�les poss�de une sous-vue qui fera partie de la vue principale JServer.
 * 
 * Cela correspond aux vues : JConfiguration, viewDictionary, JGame, JGamer
 * 
 * Pour finir le contr�leur met � dispositon deux m�thodes (start/stop) 
 * qui permmettent de lancer un thread pour accepter les connexions TCP des joueurs.
 */

import gui.JConfiguration;
import gui.JServer;
import gui.ViewDictionary;

import java.io.IOException;
import connexion.*;
import dictionary.Dictionary;

public class Server {
	
	//Mod�le Listener et vue des joueurs actifs
	private Thread threadListener;
	private ServerListener listener;
	
	//Mod�le et vue pour la configuration
	int port;
	private JConfiguration viewConfig;
	
	//Mod�le et vue pour la gestion du dictionnaire
	private Dictionary dictionary;
	private ViewDictionary viewDictionary;
	
	//TODO Mod�le et vue pour la gestion des parties actives
	
	
	//TODO Mod�le et vue pour la gestion des joueurs (inscrits)
	
	
	//Vue pricipale
	JServer viewServer;
	
	public Server(int port ) {

		this.port = port;
		
		//Cr�ation Listener
		try {
			listener = new ServerListener(port);
		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		//Cr�ation configuration
		viewConfig = new JConfiguration(this);
		
		//Cr�ation Dictionnaire
		dictionary = new Dictionary();
		viewDictionary = new ViewDictionary(dictionary);
		
		//TODO Cr�ation �l�ments pour la gestion des joueurs
		
		//TODO Cr�ation �l�ments pour la gestion des parties en cours
		
		viewServer = new JServer(viewConfig, viewDictionary);
	}
	
	//Mod�le pour la configuration
	public void setPort(int p) { port = p; }
	public int getPort(int p) { return port; }
	
	//Lance le thread qui permet d'�couter le socket
	public void startListener() {
		threadListener.start();
	}
	
	//TODO arr�te le thread qui permet d'�couter le socket
	public void stopListener() {
		
	}
	
	public static void main (String[] args) {
		Server server = new Server(3333);
	}
}
