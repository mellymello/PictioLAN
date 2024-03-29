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
	
	boolean readyTimer = false;
	
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

	public ChatHandler  getChat() {
		return chat;
	}
	
	public DrawingHandler getDrawing() {
		return draw;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void quitGame() {
		ready = true;
	}
	
	public void setReadyTimer(boolean b) {
		readyTimer = b;
	}
	
	public boolean isReadyTimer() {
		return readyTimer;
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
