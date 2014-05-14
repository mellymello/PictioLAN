package gamer;

import game.ActiveGamer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import server.BDConnexion;

public class ManageGamer {
	
	public ManageGamer() {}
	
	public static ActiveGamer authentification_BD(String pseudo, String pass) {
		
		String requete = "SELECT * FROM `Category`";
		ResultSet res;
		
		try {
			res = BDConnexion.bd.stmt.executeQuery(requete);
			
			if(res == null)
				return null;
			else
				return new ActiveGamer(pseudo, false);
			
		} catch (SQLException e) {
			System.out.println("Impossible to execute the request 2!");
		}
		
		return null;
	}

	public void delGamer(String pseudo){
		
	}
	
	
	public void addGamer(String pseudo, String pass, boolean isAdmin){
		
	}

	public LinkedList<String> getGamers(){
		
		LinkedList<String> gamers = new LinkedList<String>();
		
		String requete = "SELECT * FROM `Category`";
		ResultSet res;
		
		try {
			res = BDConnexion.bd.stmt.executeQuery(requete);
			
			while(res.next())
				gamers.add(res.getString("pseudo"));
			
		} catch (SQLException e) {
			System.out.println("Impossible to execute the request 2!");
		}
		
		return gamers;
	}
}
