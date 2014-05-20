package server;

import gamer.ManageGamer;
import gui.JConfiguration;
import gui.JDictionary;
import gui.JGamer;
import gui.JServer;
import connexion.ServerListener;
import dictionary.Dictionary;


//Class TEST
//Cette classe teste le fonctionnement de l'application sans l'interface graphique.

public class Test {
	
	public Test() {
		
		//Variable temp
		String tmp = null;
		
		//Creation du serveur
		
		//Les op�rations suivantes sont inclues dans Server s = new Server();
		Dictionary	dictionary = new Dictionary();
		ManageGamer gamers = new ManageGamer();
		
//		//Test sur le dictionnaire
//		System.out.println("Liste des categories : ");
//		for( String cat : dictionary.getListCategory()){
//			System.out.println(cat);
//			tmp = cat;
//		}
//		
//		System.out.println("Liste des mots par cat�gorie: ");
//		System.out.println("Test avec une cat�gorie null: ");
//		System.out.println(dictionary.getListWordCategory(null));
//		System.out.println("Test avec une cat�gorie \"\": ");
//		System.out.println(dictionary.getListWordCategory(""));
//		System.out.println("Test avec une cat�gorie qui n'existe pas: ");
//		System.out.println(	dictionary.getListWordCategory("nexistepas!"));
//		System.out.println("Test avec la cat�gorie "+tmp+": ");
//		for(String word : dictionary.getListWordCategory(tmp)){
//			System.out.println(word);
//		}
//		
//		System.out.println("Quelques mots al�atoires : ");
//		System.out.println("Test avec une cat�gorie null: ");
//		System.out.println(dictionary.getWord(null));
//		System.out.println("Test avec une cat�gorie \"\": ");
//		System.out.println(dictionary.getWord(""));
//		System.out.println("Test avec une cat�gorie qui n'existe pas: ");
//		System.out.println(dictionary.getWord("nexistepas!"));
//		System.out.println("Test avec la cat�gorie "+tmp+": ");
//		System.out.println(dictionary.getWord(tmp));
//		System.out.println(dictionary.getWord(tmp));
//		System.out.println(dictionary.getWord(tmp));
		
		//Fonctionnalit� manquante : Ajout/suppression Category Ajout/Suppression Word
		
		//Test ManageGamer
//		gamers.addGamer(pseudo, pass, isAdmin);
//		gamers.delGamer(pseudo);
//		gamers.getGamers();
		ManageGamer.authentification_BD("Player1", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8");
	}
}
