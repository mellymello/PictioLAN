package server;

import gui.JConfiguration;
import gui.JGamer;
import gui.JServer;
import gui.JDictionary;

import java.io.IOException;

import connexion.*;
import dictionary.Dictionary;

public class Server {
	
	private Thread threadListener;
	private ServerListener listener;
	
	private Configuration config;
	private JConfiguration viewConfig;
	private JGamer viewGamers;
	
	private Dictionary dictionary;
	private JDictionary viewDictionary;
	
	JServer viewServer;
	
	int port;
	
	public Server(int p ) {

		port = p;
		
		//Création Listener
		try {
			listener = new ServerListener();
		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		//Creation configuration
		config = new Configuration();
		viewConfig = new JConfiguration(config);
		
		
		//Dictionnaire
		dictionary = new Dictionary();
		viewDictionary = new JDictionary(dictionary);
		
		//Gamers
		viewGamers = new JGamer();
		
		viewServer = new JServer(viewConfig, viewDictionary,viewGamers);
	}
	
	//Lance le thread qui permet d'écouter le socket
	public void startListener() {
		threadListener.start();
	}
	
	public static void main (String[] args) {
		Server server = new Server(3333);
	}
}
