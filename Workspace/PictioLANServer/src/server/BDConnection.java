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
			conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/pictiolan","root","");
			stmt = (Statement) conn.createStatement();
			
		} catch (SQLException ex) {
			System.out.println("Impossible to connect to the database !\nTurn the server on and retry");
			ex.printStackTrace();
		}
	}
	
	//static public Statement getBD() { return stmt; }
	
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
