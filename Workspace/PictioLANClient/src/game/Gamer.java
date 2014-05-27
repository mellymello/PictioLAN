package game;

public class Gamer {

	int equipe;
	
	String pseudo = null;
	String password = null;
	String email = null;

	public Gamer(String p) {
		pseudo = p;
	}

	public Gamer(String p, String w) {
		pseudo = p;
		password = w;
	}
	
	public Gamer(String p, String w, String e) {
		pseudo = p;
		password = w;
		email = e;
	}
	
	public void setPassword(String pass) { this.password = pass; }
	public void setPseudo(String pseudo) { this.pseudo = pseudo; }
	public void setEmail(String e) { email = e; }
	
	public String getPassword() { return this.password; }
	public String getPseudo() { return this.pseudo; }
	public String getEmail() { return email; }
	
}
