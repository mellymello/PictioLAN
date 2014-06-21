package game;

import connection.ChatHandler;
import connection.ConnectionHandler;
import connection.DrawingHandler;
import connection.ServerListener.ConnexionListener;

public class Gamer {
	
	ConnectionHandler connection;
	ChatHandler chat;
	DrawingHandler draw;
	
	String password;
	String pseudo;
	
	Game gameActive;
	boolean ready = false;
	
	public Gamer(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setGame(Game g) {
		gameActive = g;
	}
	
	public Game getGame() {
		return gameActive;
	}
	
	public void setConnection(ConnectionHandler c) {
		connection = c;
	}

	public void setIsReady(boolean b) {
		ready = b;
	}
	
	public ConnectionHandler getConnection() {
		return connection;
	}
	
	public void setChat(ChatHandler c) {
		chat = c;
	}
	
	public void setDrawing(DrawingHandler d) {
		draw = d;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void quitGame() {
		ready = true;
	}
	
	public void closeConnection() {
		
		if(connection != null )
			connection.close();
		
		if(chat != null )
			chat.close();
		
		if(draw != null)
			draw.close();
	}
	
	
	
}
