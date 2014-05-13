package dictionary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import server.BDConnexion;

public class Dictionary
{
	private Statement stmt;

	/* TODO
	 * Connexion avec la base de donnée
	 */
	public Dictionary()
	{
		this.stmt = BDConnexion.bd.stmt;
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
		String requete = "SELECT Word FROM words INNER JOIN category ON category.ID_Category = words.ID_Category WHERE category.Name = \""+Category+ "\"";
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
	
}
