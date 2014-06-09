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

import gamer.ManageGamer;
import gui.JConfiguration;
import gui.JGamer;
import gui.JServer;
import gui.JDictionary;
import gui.JStatistic;

import java.io.IOException;

import connexion.*;
import dictionary.Dictionary;

public class Server {
	
	//Mod�le Listener et vue des joueurs actifs
	private ServerListener listener;
	
	private JConfiguration viewConfig;
	
	private ManageGamer managGamers;
	private JGamer viewGamers;
	
	//Mod�le et vue pour la gestion du dictionnaire
	private Dictionary dictionary;
	private JDictionary viewDictionary;
	
	//TODO Mod�le et vue pour la gestion des parties actives
	private ManageGamer gamers_bd;
	
	private JStatistic viewStat;
	
	//Vue pricipale
	JServer viewServer;
	
	public Server() {

		//Cr�ation Listener
		listener = new ServerListener();
		
		//Cr�ation configuration
		viewConfig = new JConfiguration(this);

		dictionary = new Dictionary();
		viewDictionary = new JDictionary(dictionary);
		
		managGamers= new ManageGamer();
		//TODO Cr�ation �l�ments pour la gestion des joueurs
		//gamers_bd = new ManageGamer();
		viewGamers = new JGamer();
		
		viewStat = new JStatistic();
		
		//TODO Cr�ation �l�ments pour la gestion des parties en cours
		
		//Cr�ation de la vue
		viewServer = new JServer(viewConfig, viewDictionary, viewGamers, viewStat);
	}
	
	//Mod�le pour la configuration
	
	//Lance le thread qui permet d'�couter le socket
	public void startListener() {
		
	}
	
	//TODO arr�te le thread qui permet d'�couter le socket
	public void stopListener() {
		
	}
	
	public static void main (String[] args) {
		Server server = new Server();
		//new Test();
	}
}
