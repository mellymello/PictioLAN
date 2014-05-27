package connection;

import game.Game;
import game.Gamer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.text.StyledEditorKit.BoldAction;

public class ConnectionServer {

	String IPserver = "";
	int portConnexion = 3336;
	
	ConnexionListener connexion;
	Thread threadConnexion;
	
	Game game = null;
	Gamer gamer = null;
	
	String pseudo = null;
	String password = null;
	String email = null;
	
	public ConnectionServer() { }

	public void setIP(String ip) { IPserver = ip; }
	
	//Méthodes Getter and Setter
	public void setPassword(String pass) { this.password = pass; }
	public void setPseudo(String pseudo) { this.pseudo = pseudo; }
	public void setEmail(String e) { email = e; }
	
	public String getPassword() { return this.password; }
	public String getPseudo() { return this.pseudo; }
	public String getEmail() { return email; }

	public void launchConnexion() {
		connexion = new ConnexionListener();
	}
	
	public void authentification(String msg){
		
		if(connexion.auth_protocole(msg)) {
			gamer = new Gamer(pseudo, password, email);
		}
		else {
			pseudo = null;
			password = null;
			email = null;
		}
		
		System.out.println("gamer=" + gamer);
	}
	
	public void createGame(int nbGamer, boolean mode) {
		int id = connexion.createGame_protocole(nbGamer, mode);
		
		if(id != -1)
			game = new Game(id,mode,pseudo);
	}
	
	public LinkedList<Game> listGame() {
		return connexion.listGame_protocole();
	}
	
	public void joinGame(Game g) {
		game = connexion.joinGame_protocole(g, g.getIdGame());
	}
	
	public void closeConnexion() {
		connexion.closeConnexion();
	}
	
	//Socket ChatListener
 	class ChatListener implements Runnable {
		
		Socket socketChat;
		
		BufferedReader inChat;
		BufferedWriter outChat;
		
		public ChatListener () throws IOException {
			socketChat = new Socket(IPserver, 3333);
			inChat = new BufferedReader(new InputStreamReader(socketChat.getInputStream()));
			outChat = new BufferedWriter(new OutputStreamWriter(socketChat.getOutputStream()));
		}
		
		public void run() {
			
		}
	}
	
	//Socket ChatListener
	class DrawingListener implements Runnable {
		
		Socket socketDrawing;
		
		InputStreamReader inDrawing;
		OutputStreamWriter outDrawing;
		
		public DrawingListener () throws IOException {
			socketDrawing = new Socket(IPserver, 3334);
			inDrawing = new InputStreamReader(socketDrawing.getInputStream());
			outDrawing = new OutputStreamWriter(socketDrawing.getOutputStream());
		}
		
		public void run() {
			//Envoyer le pseudo + passeword
		}
	}
	
	//Socket ConnexionListener
	public class ConnexionListener {
		
		public Socket socketConnexion = null;
		
		BufferedReader inConnexion;
		BufferedWriter outConnexion;
		
		public ConnexionListener () {
			
			try {
				socketConnexion = new Socket(IPserver, portConnexion);
				inConnexion = new BufferedReader (new InputStreamReader(socketConnexion.getInputStream()));
				outConnexion = new BufferedWriter (new OutputStreamWriter(socketConnexion.getOutputStream()));	

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public boolean auth_protocole(String msg) {
			
			try {
				outConnexion.write(msg+"\n");
				outConnexion.flush();
				
				if(msg.equals("AUTH_ANONYMOUS")) {
					System.out.println("DEBUG Anonymous");
					ConnectionServer.this.pseudo = inConnexion.readLine();
					return true;
				}
				else if(msg.equals("AUTH_CONNECT")) {
					System.out.println("DEBUG connect");
					outConnexion.write(pseudo + "\n");
					outConnexion.flush();
					outConnexion.write(password + "\n");
					outConnexion.flush();
					
					String rep = inConnexion.readLine();
					
					if(rep.equals("AUTH_SUCCESSFUL"))
						return true;
					else
						return false;
				}
				else if(msg.equals("AUTH_SUBSCRIBE")) {
					System.out.println("DEBUG subscribe");
					outConnexion.write(pseudo + "\n");
					outConnexion.flush();
					
					outConnexion.write(password + "\n");
					outConnexion.flush();
					
					outConnexion.write(email + "\n");
					outConnexion.flush();
					
					String rep = inConnexion.readLine();
					
					if(rep.equals("AUTH_SUCCESSFUL"))
						return true;
					else
						return false;
				}
				else
					return false;
				
				
			} catch (IOException e) {
				System.out.println("Error : Connexion impossible");
				e.printStackTrace();
			}
			
			return false;
			
		}
		
		public int createGame_protocole(int nb, boolean m)  {
			
			try {
				System.out.println("DEBUG client 1");
				outConnexion.write("GAME_CREATE\n");
				
				outConnexion.flush();
				
				System.out.println("DEBUG client 2");
				int id = inConnexion.read();
				
				System.out.println("DEBUG client 3");
				outConnexion.write(nb);
				
				System.out.println("DEBUG client 4");
				outConnexion.write(new Boolean(m).toString() + "\n");
				outConnexion.flush();
				
				//outConnexion.write("GAME_READY\n");
				
				return id;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return -1;
		}

		public LinkedList<Game> listGame_protocole()  {
			
			LinkedList<Game> games = new LinkedList<Game>();
			
			try {
				System.out.println("GAME_LIST");
				outConnexion.write("GAME_LIST\n");
				outConnexion.flush();
				System.out.println("envoie passe");
				int nb = inConnexion.read();
				System.out.println("nb="+nb);
				for(int i=0; i < nb; i++) {
					
					int id = inConnexion.read();
					boolean mode = Boolean.parseBoolean(inConnexion.readLine());
					String pseudo = inConnexion.readLine();
					System.out.println("game = " + id+mode+pseudo);
					games.add(new Game(id,mode,pseudo));
				}
				
				return games;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		public Game joinGame_protocole(Game g, int id)  {
			
			try {
				outConnexion.write("GAME_JOIN\n");
				outConnexion.write(new Integer(id).toString());
				outConnexion.flush();
				
				String rep = inConnexion.readLine();
				
				if(rep.equals("GAME_JOIN_SUCCESS"))
					return g;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		public void closeConnexion() {

			try {
				if(socketConnexion != null)
					socketConnexion.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	} //Fin de la class ConnexionListener
	
} 
