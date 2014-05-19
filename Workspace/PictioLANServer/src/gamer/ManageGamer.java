package gamer;

import game.ActiveGamer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import server.BDConnexion;

public class ManageGamer {
	
	private Connection conn;
	private Statement stmt;
	
	public ManageGamer() {
		try {

			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/pictiolan?"
							+ "user=root&password=");
			stmt = (Statement) conn.createStatement();
		} catch (SQLException ex) {
			JOptionPane
					.showMessageDialog(
							null,
							"Impossible to connect to the database !\nTurn the server on and retry",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
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
				
		String requete = "SELECT * FROM `player`";
		ResultSet res;
		
		LinkedList<String> gamers = new LinkedList<String>();

		try {
			res = stmt.executeQuery(requete);

			while (res.next()) {
				gamers.add(res.getString("Pseudo"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to get the words list", "Error",
					JOptionPane.ERROR_MESSAGE);

		}

		return gamers;
	}
	
	public void deleteGamer(String gamerPseudo) {

		String requete = "DELETE FROM player WHERE pseudo=\"" + gamerPseudo + "\"";

		try {

			stmt.executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible to delete player : "
					+ gamerPseudo, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Deleted player " + gamerPseudo, " !",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
