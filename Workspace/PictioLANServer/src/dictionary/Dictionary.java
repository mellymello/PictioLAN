package dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

public class Dictionary
{
	private Connection conn;
	private Statement stmt;

	/* TODO
	 * Connexion avec la base de donnée
	 */
	public Dictionary()
	{
		try {

			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/pictiolan?"
							+ "user=root&password=");
			stmt = (Statement) conn.createStatement();
		} catch (SQLException ex) 
		{
			System.out.println("Impossible to connect to the database !\nTurn the server on and retry");
		}
	}
	
	/* TODO
	 * Utiliser la connexion à la base de donnée pour récupérer
	 * un mot aléatoirement du dictionnaire.
	 */
	public String getWord(String Category) 
	{
		String word = "";
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(50);
		String requete = "SELECT Word FROM `Word` WHERE Words.ID_Word = " + nombreAleatoire 
				+ " AND Category.Name = "+ Category
				+ " INNER JOIN Category ON Category.ID_Category = Words.ID_Category";
		
		ResultSet res;
		try
		{
			res = stmt.executeQuery(requete);
			res.next();
			word = res.getString("Word");
			
		}
		catch (SQLException e)
		{
			System.out.println("Impossible");
		}
		return word;
	}
	
	/* TODO
	 * Utiliser la connexion à la base de donnée pour récupérer
	 * la liste des catégories enregistrées dans la BD.
	 */
	public LinkedList<String> getListCategory() 
	{
		String requete = "SELECT * FROM `Category`";
		LinkedList<String> listeCat = new LinkedList<String>();

		ResultSet res;
		try {
			res = stmt.executeQuery(requete);

			while (res.next()) {
				listeCat.add(res.getString("Name"));
			}
		} catch (SQLException e) {
			System.out.println("Impossible to execute the request 2!");
		}

		return listeCat;
	}

	/* TODO
	 * Utiliser la connexion à la base de donnée pour récupérer
	 * la liste des mots composant une catégorie enregistrés dans la BD.
	 */
	public LinkedList<String> getListWordCategory(String Category) 
	{
		String requete = "SELECT Word FROM `Word` WHERE Category.Name = " + Category 
				+ "INNER JOIN Category ON Category.ID_Category = Words.ID_Category";
		LinkedList<String> listeWordCat = new LinkedList<String>();

		ResultSet res;
		try {
			res = stmt.executeQuery(requete);

			while (res.next()) {
				listeWordCat.add(res.getString("Word"));
			}
		} catch (SQLException e) {
			System.out.println("Impossible to execute the request 3!");
		}

		return listeWordCat;
	}
	
	public void closeConnection() {

		try {
			if (stmt != null)
				stmt.close(); // This closes ResultSet too
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			System.out.println("Impossible to close the database connection !");
		}

	}
	
}
