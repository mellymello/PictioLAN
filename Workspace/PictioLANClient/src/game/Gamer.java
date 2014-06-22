package game;

import java.io.IOException;

import gui.JClient;
import gui.JDraw;
import gui.JProposition;
import connection.ChatConnection;
import connection.DrawingConnnection;
import connection.ServerConnection;

public class Gamer {

	String IPserver = "";
	
	ChatConnection chat = null;
	DrawingConnnection draw = null;
	ServerConnection server = null;
	
	int equipe;
	
	String pseudo = null;
	String password = null;
	String email = null;
	
	Game game;

	public Gamer(){}
	
	public Gamer(String p) {
		pseudo = p;
	}

	public Gamer(String p, String w) {
		pseudo = p;
		password = w;
	}
	
	public Gamer(String p, String w, String e) {
		pseudo = p;
		password = w;
		email = e;
	}
	
	public void setPassword(String pass) { this.password = pass; }
	public void setPseudo(String pseudo) { this.pseudo = pseudo; }
	public void setEmail(String e) { email = e; }
	public void setIP(String ip) { IPserver = ip; }
	public void setGame(Game g) { game = g; }
	
	public String getPassword() { return this.password; }
	public String getPseudo() { return this.pseudo; }
	public String getEmail() { return email; }
	public String getIP() { return IPserver; }
	public Game getGame() { return game; }
	
	public ChatConnection getChat(){ return chat; }
	public DrawingConnnection getDraw(){ return draw; }
	public ServerConnection getConnection(){ return server; }
	
	public void launchConnexion(String ip) {
		IPserver = ip;
		server = new ServerConnection(this);
	}
	
	public void launchChatDraw(){
		try {
			chat = new ChatConnection();
			chat.startChat();

			draw = new DrawingConnnection();
			draw.startDraw();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
