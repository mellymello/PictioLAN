package game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import server.BDConnection;

public class Dictionary {

	static public int getCategoryID(String category) throws SQLException {
		int id = -1;

		String requete = "SELECT ID_Category FROM category WHERE name=\""
				+ category + "\"";

		ResultSet res;
		try {
			res = BDConnection.getBD().executeQuery(requete);
			res.next();
			id = res.getInt("ID_Category");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to get the category ID for category: "
							+ category, "Error", JOptionPane.ERROR_MESSAGE);
			throw e;
		}
		return id;
	}

	static public int getNbrWords(int categoryID) throws SQLException {

		int nbrW = -1;

		String requete = "SELECT count(ID_Word) AS nbr FROM word WHERE ID_Category="
				+ categoryID;

		ResultSet res;
		try {
			res = BDConnection.getBD().executeQuery(requete);

			nbrW = res.getInt("nbr");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to get count words for category ID: "
							+ categoryID, "Error", JOptionPane.ERROR_MESSAGE);
			throw e;
		}

		return nbrW;
	}

	/*
	 * Utiliser la connexion à la base de donnée pour récupérer un mot
	 * aléatoirement du dictionnaire.
	 */
	static public String getWord(String category) {
		String word = "";
		Random rand = new Random();
		System.out.println(category);
		if(category.equals("ALL"))
		{
			try {

				int categoryID = 0;
				int nbrWord = 0;
				LinkedList<String> listeCat = getListCategory();
				for(String l : listeCat)
				{
					categoryID = getCategoryID(l);
					nbrWord += getNbrWords(categoryID);
				}

				int nombreAleatoire = rand.nextInt(nbrWord);
				String requete = "SELECT Word FROM `Word` WHERE Word.ID_Word = "
						+ nombreAleatoire;

				ResultSet res;
				res = BDConnection.getBD().executeQuery(requete);
				res.next();
				word = res.getString("Word");

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Impossible to get a random word from ALL : ", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			try {
	
				int categoryID = getCategoryID(category);
				int nbrWordCat = getNbrWords(categoryID);
				int nbrWordTot = 0;
				
				for(int i = 1; i < categoryID; i++)
				{
					nbrWordTot += getNbrWords(i);
				}
	
				int nombreAleatoire = rand.nextInt(nbrWordCat);
				nombreAleatoire += nbrWordTot;
				System.out.println(nombreAleatoire);
				String requete = "SELECT Word FROM `Word` WHERE ID_Word = "
						+ nombreAleatoire
						+ " AND ID_Category = "
						+ categoryID;
	
				ResultSet res;
				System.out.println(requete);
	
				res = BDConnection.getBD().executeQuery(requete);
				res.next();
				word = res.getString("Word");
	
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Impossible to get a random word from category : "
								+ category, "Error", JOptionPane.ERROR_MESSAGE);
	
			}
		}
		return word;
	}

	/*
	 * Utiliser la connexion à la base de donnée pour récupérer la liste
	 * des catégories enregistrées dans la BD.
	 */
	static public LinkedList<String> getListCategory() {
		String requete = "SELECT * FROM `Category`";
		LinkedList<String> listeCat = new LinkedList<String>();

		ResultSet res;
		try {
			res = BDConnection.getBD().executeQuery(requete);

			while (res.next()) {
				listeCat.add(res.getString("Name"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to get the category list", "Error",
					JOptionPane.ERROR_MESSAGE);

		}

		return listeCat;
	}

	/*
	 * Utiliser la connexion à la base de donnée pour récupérer la liste
	 * des mots composant une catégorie enregistrés dans la BD.
	 */
	static public LinkedList<String> getListWordCategory(String category) {
		String requete = "SELECT Word FROM word INNER JOIN category ON category.ID_Category = word.ID_Category WHERE category.Name = \""
				+ category + "\"";
		LinkedList<String> listeWordCat = new LinkedList<String>();

		ResultSet res;
		try {
			res = BDConnection.getBD().executeQuery(requete);

			while (res.next()) {
				listeWordCat.add(res.getString("Word"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to get the words list", "Error",
					JOptionPane.ERROR_MESSAGE);

		}

		return listeWordCat;
	}

	static public void addCategory(String category) {

		String requete = "INSERT INTO Category(name) VALUES (\"" + category
				+ "\")";

		try {
			BDConnection.getBD().executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible to add category !",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	static public void addWord(String word, String category) {

		int categoryID;
		try {
			categoryID = getCategoryID(category);

			String requete = "INSERT INTO Word(Word, ID_Category) VALUES ('" + word
					+ "', '" + categoryID + "')";

			BDConnection.getBD().executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible to add word !",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	static public void deleteCategory(String category) {

		String requete = "DELETE FROM category WHERE name=\"" + category + "\"";

		try {

			BDConnection.getBD().executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible to delete category : " + category, "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Deleted category :" + category, " !",
				JOptionPane.INFORMATION_MESSAGE);

	}

	static public void deleteWord(String word) {

		String requete = "DELETE FROM word WHERE word=\"" + word + "\"";

		try {

			BDConnection.getBD().executeUpdate(requete);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible to delete word : "
					+ word, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Deleted word :" + word, " !",
				JOptionPane.INFORMATION_MESSAGE);

	}

}
