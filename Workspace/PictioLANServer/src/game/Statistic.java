package game;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import server.BDConnexion;

public class Statistic 
{
	public void getStatistic ()
	{
		String requete = "SELECT Pseudo,Word,Time, Win FROM `stats` Inner Join word on word.ID_Word = stats.ID_Word "
				+ "Inner join player on player.ID_Player = stats.ID_Player";
		ResultSet res;
		
		try {
			res = BDConnexion.bd.stmt.executeQuery(requete);

			while (res.next()) 
			{

				
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to get the words list", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
