package game;

public class Round {

	Gamer drawer;
	Gamer winner;
	
	String word;
	
	public Round(Gamer drawer, String word){
	}
	
	public void setWord( String w){ word = w; }
	public String getWord() {return word;}
}
