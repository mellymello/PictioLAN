package server;

import java.io.IOException;
import java.net.InetAddress;
import connexion.*;

public class Server {
	
	Thread threadListener;
	ServerListener listener;
	
	InetAddress ip;
	int port;
	
	public Server() {

	}
	
//	public InetAddress getLocalIP() {
//	    
//		try 
//	    {
//	      InetAddress addr = InetAddress.getLocalHost();
//	  
//	      byte[] ipAddr = addr.getAddress();
//	      String hostname = addr.getHostName();
//	      
//	    } catch (UnknownHostException e) {}
//	}
	
	//Lance le thread qui permet d'écouter le socket
	public void startListener() {
		
		try {
			listener = new ServerListener(port);
		
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		threadListener.start();
	}
	
	//Lance le thread qui permet de configurer le serveur
	public void startConfiguration() {
		
	}
	
	public static void main (String[] args){
		Server server = new Server();
	}
}
