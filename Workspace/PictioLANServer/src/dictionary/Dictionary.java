package dictionary;

import java.util.LinkedList;

public class Dictionary {

	/* TODO
	 * Connexion avec la base de donn�e
	 */
	public Dictionary() {
	
	}
	
	/* TODO
	 * Utiliser la connexion � la base de donn�e pour r�cup�rer
	 * un mot al�atoirement du dictionnaire.
	 */
	public String getWord(String Category) {
		return "";
	}
	
	/* TODO
	 * Utiliser la connexion � la base de donn�e pour r�cup�rer
	 * la liste des cat�gories enregistr�es dans la BD.
	 */
	public LinkedList<String> getListCategory() {
		return new LinkedList<String>();
	}

	/* TODO
	 * Utiliser la connexion � la base de donn�e pour r�cup�rer
	 * la liste des mots composant une cat�gorie enregistr�s dans la BD.
	 */
	public LinkedList<String> getListWordCategory() {
		return new LinkedList<String>();
	}
	
}
