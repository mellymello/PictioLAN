package connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Runnable {

	ServerSocket socket;
	ClientHandler client;

	
	public ServerListener() throws IOException {
	}

	public ServerListener(int port) throws IOException {
		socket = new ServerSocket(port);
		client = new ClientHandler();
	}

	public void run() {
			
		try {
			Socket s = socket.accept();
			client = new ClientHandler(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}

