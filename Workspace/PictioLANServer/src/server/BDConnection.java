package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BDConnection {
	
	static private BDConnection bd;
	
	static private Connection conn;
	static private Statement stmt;

	private BDConnection()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:PictioLan.sqlite");
			stmt = (Statement) conn.createStatement();
			
		} catch (SQLException ex) {
			System.out.println("Impossible to connect to the database !\nTurn the server on and retry");
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Impossible to connect to the database !\nTurn the server on and retry");
			e.printStackTrace();
		}
	}
	
	public static Statement getBD(){
		if(bd == null){
			bd = new BDConnection();
		}
		return stmt;
	}
	static public void closeConnection() {

		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			
		} catch (SQLException ex) {
			System.out.println("Impossible to close the database connection !");
		}
	}
}
