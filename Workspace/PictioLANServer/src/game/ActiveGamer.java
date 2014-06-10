package game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


import server.BDConnexion;

/*
 * Derni�re mise � jour : 6 mai 2014
 * 
 * Cette classe est le modele qui implemente le joeuur connect� � l'application
 * Le mod�le founrnit donc l'etat du joueur dans le contexte d'une partie.
 * 
 * Cela comprend �galement une m�thode pour l'authentification de l'utilisateur.
 */

public class ActiveGamer {
	
	String pseudo;
	Game ctrGame;
	
	public ActiveGamer() {}

	public ActiveGamer(String p) {
		pseudo = p;
	}
	
	public void setPseudo(String p) {
		pseudo = p;
	}
	
	public String getPseudo(){
		return pseudo;
	}

	public Game getCtrGame() {
		return ctrGame;
	}

	public void setCtrGame(Game ctrGame) {
		this.ctrGame = ctrGame;
		
		
	}
	
	
}
