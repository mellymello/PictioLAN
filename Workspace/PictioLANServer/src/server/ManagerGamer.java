package server;

import game.Gamer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class ManagerGamer {

	//Liste static des joueurs connectés et/ou authentifiés !!!
	public static LinkedList<Gamer> listGamer = new LinkedList<Gamer>();
	
	private static int idAnonyme = 0;
	
	public static int getIDAnonymous() { 
		
		return idAnonyme++;
	}
	
	public static void addAnonymoGamer(Gamer g) {
		listGamer.add(g);
	}
	
	public static Gamer getGamer(String pseudo) {
		
		for(Gamer g : listGamer) {
			if(g.getPseudo().equals(pseudo))
				return g;
		}
		
		return null;
	}
	
	public static Gamer authentification_BD(String pseudo, String pass) {
		
		String requete = "SELECT password FROM player WHERE pseudo = \"" + pseudo + "\"";
		System.out.println(requete);
		ResultSet res;
		
		try {
			res = BDConnection.getBD().executeQuery(requete);
			
			if(res != null) {
				res.next();
				String passSHA1 = sha1(pass);
				if(res.getString(1).equals(passSHA1)) {
					Gamer temp = new Gamer(pseudo);
					
					boolean exist = false;
					for(Gamer g : listGamer) {
						if(g.getPseudo() == pseudo) {
							exist = true;
						}
					}
					
					if(!exist)
						listGamer.add(temp);
					
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
	
	private static String sha1(String input) {
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	
	public static Gamer addGamerBD(String pseudo, String pass, String email){
	
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
				String sha1Pass = sha1(pass);
				String requete = "INSERT INTO player(Pseudo, Password, Email) VALUES (\""+pseudo+"\",\""+sha1Pass+"\",\""+email+"\")";
				BDConnection.getBD().executeUpdate(requete);
				
				return new Gamer(pseudo);

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
		boolean isPlayerActive = false;
		
		try {
			res = BDConnection.getBD().executeQuery(requete);

			while (res.next()) {
				
				for (Gamer ag : listGamer)
				{
					if(res.getString("Pseudo") == ag.getPseudo())
					{
						isPlayerActive = true;
					}
				}
				if(isPlayerActive == true)
				{
					gamers.add(res.getString("Pseudo")+"	Actif");
				}
				else{
					gamers.add(res.getString("Pseudo"));
				}
				
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
		
		for(Gamer gamer : listGamer) {
			
			if(gamer.getPseudo().equals(pseudo)) {
				notExist = false;
			}
		}
		
		return notExist;
}
	public static void deleteGamer(String gamerPseudo) {

		String requete = "DELETE FROM player WHERE pseudo=\"" + gamerPseudo + "\"";

		try {

			BDConnection.getBD().executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible to delete player : "
					+ gamerPseudo, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Deleted player " + gamerPseudo, " !",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
