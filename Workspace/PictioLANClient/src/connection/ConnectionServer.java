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

import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.BoldAction;

public class ConnectionServer {

	String IPserver = "";
	int portConnexion = 3336;

	public ConnexionListener connexion;
	Thread threadConnexion;

	Game game = null;
	Gamer gamer = null;

	String pseudo = null;
	String password = null;
	String email = null;

	Boolean connexionDone = false;

	public ConnectionServer() {
	}

	public void setIP(String ip) {
		IPserver = ip;
	}

	// Méthodes Getter and Setter
	public void setPassword(String pass) {
		this.password = pass;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void setEmail(String e) {
		email = e;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void launchConnexion() throws IOException, UnknownHostException {
		connexion = new ConnexionListener();
		connexionDone = true;
	}

	public Gamer getGamer() {
		return gamer;
	}

	public Boolean getConnexionDone() {
		return connexionDone;
	}

	public void authentification(String msg) {

		if (connexion.auth_protocole(msg)) {
			gamer = new Gamer(pseudo, password, email);
		} else {
			pseudo = null;
			password = null;
			email = null;
		}

		System.out.println("gamer=" + gamer);
	}

	public void createGame(int nbGamer, boolean mode, int nbRound,
			String category) {
		int id = connexion.createGame_protocole(nbGamer, mode, nbRound,
				category);

		if (id != -1)
			game = new Game(id, category, nbRound, mode, pseudo, nbGamer);

	}

	public LinkedList<Game> listGame() {
		return connexion.listGame_protocole();
	}

	public String[] listCategory() {
		
		String[] testCateg = connexion.listCategory_protocol();

		return testCateg;

	}

	public void joinGame(Game g) {
		game = connexion.joinGame_protocole(g, g.getIdGame());
	}

	public void closeConnexion() {
		connexion.closeConnexion();
		connexionDone = false;
	}

	// Socket ChatListener
	class ChatListener implements Runnable {

		Socket socketChat;

		BufferedReader inChat;
		BufferedWriter outChat;

		public ChatListener() throws IOException {
			socketChat = new Socket(IPserver, 3333);
			inChat = new BufferedReader(new InputStreamReader(
					socketChat.getInputStream()));
			outChat = new BufferedWriter(new OutputStreamWriter(
					socketChat.getOutputStream()));
		}

		public void run() {

		}
	}

	// Socket ChatListener
	class DrawingListener implements Runnable {

		Socket socketDrawing;

		InputStreamReader inDrawing;
		OutputStreamWriter outDrawing;

		public DrawingListener() throws IOException {
			socketDrawing = new Socket(IPserver, 3334);
			inDrawing = new InputStreamReader(socketDrawing.getInputStream());
			outDrawing = new OutputStreamWriter(socketDrawing.getOutputStream());
		}

		public void run() {
			// Envoyer le pseudo + passeword
		}
	}

	// Socket ConnexionListener
	public class ConnexionListener {

		public Socket socketConnexion = null;

		BufferedReader inConnexion;
		BufferedWriter outConnexion;

		public ConnexionListener() throws IOException, UnknownHostException {

			socketConnexion = new Socket(IPserver, portConnexion);
			inConnexion = new BufferedReader(new InputStreamReader(
					socketConnexion.getInputStream()));
			outConnexion = new BufferedWriter(new OutputStreamWriter(
					socketConnexion.getOutputStream()));

		}

		public boolean auth_protocole(String msg) {

			try {
				outConnexion.write(msg + "\n");
				outConnexion.flush();

				if (msg.equals("AUTH_ANONYMOUS")) {
					System.out.println("DEBUG Anonymous");
					ConnectionServer.this.pseudo = inConnexion.readLine();
					return true;
				} else if (msg.equals("AUTH_CONNECT")) {
					System.out.println("DEBUG connect");
					outConnexion.write(pseudo + "\n");
					outConnexion.flush();
					outConnexion.write(password + "\n");
					outConnexion.flush();

					String rep = inConnexion.readLine();

					if (rep.equals("AUTH_SUCCESSFUL"))
						return true;
					else
						return false;
				} else if (msg.equals("AUTH_SUBSCRIBE")) {
					System.out.println("DEBUG subscribe");
					outConnexion.write(pseudo + "\n");
					outConnexion.flush();

					outConnexion.write(password + "\n");
					outConnexion.flush();

					outConnexion.write(email + "\n");
					outConnexion.flush();

					String rep = inConnexion.readLine();

					if (rep.equals("AUTH_SUCCESSFUL"))
						return true;
					else
						return false;
				} else
					return false;

			} catch (IOException e) {
				System.out.println("Error : Connexion impossible");
				e.printStackTrace();
			}

			return false;

		}

		public int createGame_protocole(int nbGamer, boolean mode, int nbRound,
				String category) {

			try {

				outConnexion.write("GAME_CREATE\n");

				outConnexion.write(nbRound);
				outConnexion.write(nbGamer);
				outConnexion.write(category + "\n");
				outConnexion.write(new Boolean(mode).toString() + "\n");
				outConnexion.flush();

				int id = inConnexion.read();

				return id;

			} catch (IOException e) {
				e.printStackTrace();
			}

			return -1;
		}

		public LinkedList<Game> listGame_protocole() {

			LinkedList<Game> games = new LinkedList<Game>();

			try {
				System.out.println("GAME_LIST");
				outConnexion.write("GAME_LIST\n");
				outConnexion.flush();
				System.out.println("envoie passe");
				int nbrGames = inConnexion.read();
				// System.out.println("nb="+nb);

				int id;
				String creatorPseudo;
				boolean mode;
				int nbrRounds;
				int nbrMaxGamers;
				int nbrActivesGamers;
				String category;
				for (int i = 0; i < nbrGames; i++) {

					id = inConnexion.read();
					creatorPseudo = inConnexion.readLine();
					mode = Boolean.parseBoolean(inConnexion.readLine());
					nbrRounds = inConnexion.read();
					nbrMaxGamers = inConnexion.read();
					nbrActivesGamers = inConnexion.read();
					category = inConnexion.readLine();

					games.add(new Game(id, category, nbrRounds, mode,
							creatorPseudo, nbrMaxGamers));
				}

				return games;

			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		public Game joinGame_protocole(Game g, int id) {

			try {
				outConnexion.write("GAME_JOIN\n");
				outConnexion.write(new Integer(id).toString());
				outConnexion.flush();

				String rep = inConnexion.readLine();

				if (rep.equals("JOIN_SUCCESS")) {
					return g;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		public void closeConnexion() {

			try {
				if (socketConnexion != null)
					socketConnexion.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public String[] listCategory_protocol() {

			String[] cat=new String[0];

			try {
				System.out.println("CATEGORY_LIST");
				outConnexion.write("CATEGORY_LIST\n");
				outConnexion.flush();

				int nbrCat = inConnexion.read();
				// System.out.println("nb="+nb);
				if (nbrCat > 0) {
					cat = new String[nbrCat];

					for (int i = 0; i < nbrCat; i++) {

						cat[i] = inConnexion.readLine();

					}
				} 

			} catch (IOException e) {
				e.printStackTrace();
			}

			return cat;
		}

	} // Fin de la class ConnexionListener

}
