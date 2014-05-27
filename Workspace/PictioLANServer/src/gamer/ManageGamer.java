package gamer;

import game.ActiveGamer;
import server.BDConnexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import server.BDConnexion;

public class ManageGamer {

	//Liste static des joueurs connectés et/ou authentifiés !!!
	static LinkedList<ActiveGamer> listActiveGamer = new LinkedList<ActiveGamer>();
	
	public static int idAnonyme = 0;
	
	public ManageGamer() { }
		
	public static ActiveGamer authentification_BD(String pseudo, String pass) {
		
		String requete = "SELECT password FROM player WHERE pseudo = \"" + pseudo + "\"";
		System.out.println(requete);
		ResultSet res;
		
		try {
			res = BDConnexion.bd.stmt.executeQuery(requete);
			
			if(res != null) {
				res.next();
				if(res.getString(1).equals(pass)) {
					ActiveGamer temp = new ActiveGamer(pseudo);
					listActiveGamer.add(temp);
					return temp;
				}
				else
					return null;
				}
			
		} catch (SQLException e) {
			System.out.println("Impossible to execute the request authentification !");
		}
		
		return null;
	}

	public static void delGamer(String pseudo){
		
	}
	
	
	public static ActiveGamer addGamer(String pseudo, String pass, String email){
	
		LinkedList<String> listPseudo = getGamers();
		
		boolean notExist = true;
		
		for(int i=0; i < listPseudo.size(); i++){
			
			if(listPseudo.get(i).equals(pseudo)) {
				notExist = false;
				break;
			}
		}
		
		if(notExist) {
			try {

				String requete = "INSERT INTO player(Pseudo, Password, Email) VALUES (\""+pseudo+"\",\""+pass+"\",\""+email+"\")";
				BDConnexion.bd.stmt.executeUpdate(requete);
				
				return new ActiveGamer(pseudo);

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Impossible to add player !",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return null;
	}


	public static LinkedList<String> getGamers(){
		
		LinkedList<String> gamers = new LinkedList<String>();
		
		String requete = "SELECT pseudo FROM `player`";
		ResultSet res;
		
		try {
			res = BDConnexion.bd.stmt.executeQuery(requete);

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
	

	public static boolean checkPseudo(String pseudo) {
		
		boolean notExist = true;
		
		for(ActiveGamer gamer : listActiveGamer) {
			
			if(gamer.getPseudo().equals(pseudo)) {
				notExist = false;
			}
		}
		
		return notExist;
}
	public static void deleteGamer(String gamerPseudo) {

		String requete = "DELETE FROM player WHERE pseudo=\"" + gamerPseudo + "\"";

		try {

			BDConnexion.bd.stmt.executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible to delete player : "
					+ gamerPseudo, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Deleted player " + gamerPseudo, " !",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
