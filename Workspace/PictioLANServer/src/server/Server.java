package server;

import connection.ServerListener;
import gui.JActivesGames;
import gui.JConfiguration;
import gui.JDictionary;
import gui.JGamer;
import gui.JServer;
import gui.JStatistic;


public class Server {
	
	ServerListener serverlistener;
	
	public Server() {
		serverlistener = new ServerListener();
	}
	
	public void startListener() { }
	
	public void stopListener() { }
	
	
	public static void main (String[] args) {
		Server server = new Server();
//		JServer vue = new JServer(new JConfiguration(server), new JDictionary(), new JGamer(), new JStatistic(), new JActivesGames());
	}
}
