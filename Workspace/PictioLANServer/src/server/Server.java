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
	
	//Modèle Listener et vue des joueurs actifs
	private ServerListener listener;
	
	private JConfiguration viewConfig;
	
	private ManageGamer managGamers;
	private JGamer viewGamers;
	
	//Modèle et vue pour la gestion du dictionnaire
	private Dictionary dictionary;
	private JDictionary viewDictionary;
	
	//TODO Modèle et vue pour la gestion des parties actives
	private ManageGamer gamers_bd;
	
	private JStatistic viewStat;
	
	//Vue pricipale
	JServer viewServer;
	
	public Server() {

		//Création Listener
		listener = new ServerListener();
		
		//Création configuration
		viewConfig = new JConfiguration(this);

		dictionary = new Dictionary();
		viewDictionary = new JDictionary(dictionary);
		
		managGamers= new ManageGamer();
		//TODO Création éléments pour la gestion des joueurs
		//gamers_bd = new ManageGamer();
		viewGamers = new JGamer();
		
		viewStat = new JStatistic();
		
		//TODO Création éléments pour la gestion des parties en cours
		
		//Création de la vue
		viewServer = new JServer(viewConfig, viewDictionary, viewGamers, viewStat);
	}
	
	//Modèle pour la configuration
	
	//Lance le thread qui permet d'écouter le socket
	public void startListener() {
		
	}
	
	//TODO arrête le thread qui permet d'écouter le socket
	public void stopListener() {
		
	}
	
	public static void main (String[] args) {
		Server server = new Server();
		//new Test();
	}
}
