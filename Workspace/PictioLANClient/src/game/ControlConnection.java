package game;

import connection.ConnectionServer;
import gui.Client;
import gui.JWelcome;

public class ControlConnection {
	
	JWelcome mainVue;
	ConnectionServer modele;
	
	public ControlConnection() {
		modele = new ConnectionServer();
		mainVue = new JWelcome(modele);
	}
	
	public static void main (String[] args) {
		ControlConnection c = new ControlConnection();
//		Client cli = new Client("test");
	}
}