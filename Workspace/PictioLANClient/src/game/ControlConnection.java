package game;

import connection.ConnectionServer;
import gui.JConnection;

public class ControlConnection {

	JConnection vue;
	ConnectionServer modele;
	Thread communication;
	
	public ControlConnection() {
		modele = new ConnectionServer("192.168.1.140");
		vue = new JConnection();
		communication = new Thread(modele);
		communication.start();
	}
	
	public static void main (String[] args) {
		ControlConnection c = new ControlConnection();
	}
}
