package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionServer implements Runnable {

	String pseudo; 
	String password;
	
	String IPserver;
	
	Socket socket = null;
	BufferedReader in;
	PrintWriter out;
	
	public ConnectionServer(String ip) {
		IPserver = ip;
		
		try {
			socket = new Socket(IPserver, 3333);
			in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void authentification(String pseudo, String password) throws IOException  {

		if(socket != null) {
			
			out.write(pseudo);
			out.write(password);
			
	       String rep = in.readLine();
	       
	       if(rep == "AUTH_SUCCESS")
	    	   System.out.println("Success");
		}
	}
	
	public void run() {
		
//		if(socket != null) {
//			
//		}
	}
} 
