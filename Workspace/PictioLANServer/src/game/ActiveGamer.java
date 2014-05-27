package game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import server.BDConnexion;

/*
 * Dernière mise à jour : 6 mai 2014
 * 
 * Cette classe est le modele qui implemente le joeuur connecté à l'application
 * Le modèle founrnit donc l'etat du joueur dans le contexte d'une partie.
 * 
 * Cela comprend également une méthode pour l'authentification de l'utilisateur.
 */

public class ActiveGamer {
	
	String pseudo;
	boolean gameCreator;
	
	public ActiveGamer() {}

	public ActiveGamer(String p, boolean c) {
		pseudo = p;
		gameCreator = c;
	}
	
	public void setPseudo(String p) {
		pseudo = p;
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public void setCreateurPartie(boolean isCreator) {
		gameCreator = isCreator;
	}
	
	public boolean isCreateurPartie() {
		return gameCreator;
	}	
	
}
