package dictionary;

import java.util.LinkedList;

public class Dictionary {

	/* TODO
	 * Connexion avec la base de donnée
	 */
	public Dictionary() {
	
	}
	
	/* TODO
	 * Utiliser la connexion à la base de donnée pour récupérer
	 * un mot aléatoirement du dictionnaire.
	 */
	public String getWord(String Category) {
		return "";
	}
	
	/* TODO
	 * Utiliser la connexion à la base de donnée pour récupérer
	 * la liste des catégories enregistrées dans la BD.
	 */
	public LinkedList<String> getListCategory() {
		return new LinkedList<String>();
	}

	/* TODO
	 * Utiliser la connexion à la base de donnée pour récupérer
	 * la liste des mots composant une catégorie enregistrés dans la BD.
	 */
	public LinkedList<String> getListWordCategory() {
		return new LinkedList<String>();
	}
	
}
