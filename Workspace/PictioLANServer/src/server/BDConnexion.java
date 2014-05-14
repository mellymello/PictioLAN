package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDConnexion {
	
	static public final BDConnexion bd = new BDConnexion();
	
	private Connection conn;
	public Statement stmt;

	/* TODO
	 * Connexion avec la base de donnée
	 */
	private BDConnexion()
	{
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pictiolan?"+ "user=root&password=");
			stmt = (Statement) conn.createStatement();
			
		} catch (SQLException ex) {
			System.out.println("Impossible to connect to the database !\nTurn the server on and retry");
		}
	}
	
	public ResultSet requete(String requete) 
	{
		ResultSet res = null;
		try {
			res = stmt.executeQuery(requete);
		}
		catch (SQLException e) {
			System.out.println("Impossible");
		}
		
		return res;
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
