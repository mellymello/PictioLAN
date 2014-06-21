package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatConnection implements Runnable {
	
	int chat_port = 3333;
	
	Thread chatThread;
	
	Socket socketChat = null;
	
	BufferedReader inChat;
	BufferedWriter outChat;
	
	boolean isConnect = false;
	
	boolean endConnection = false;
	
	public ChatConnection () throws IOException {
		
		socketChat = new Socket(PictioLan.modele_gamer.getIP(), chat_port);
		inChat = new BufferedReader(new InputStreamReader(socketChat.getInputStream()));
		outChat = new BufferedWriter(new OutputStreamWriter(socketChat.getOutputStream()));
	}

	public boolean isConnect() { return isConnect; };

	public void startChat() {
		
		if(isConnect) {
			chatThread = new Thread(this);
			chatThread.start();
			auth_protcole();
		}
	}
	
	public boolean auth_protcole() { 
		
		try {
			
			outChat.write("AUTH\n");
			outChat.flush();
			
			String pseudo = PictioLan.modele_gamer.getPseudo();
			String pass = PictioLan.modele_gamer.getPassword();
				
			outChat.write(pseudo + "\n");
			outChat.flush();
			outChat.write(pass + "\n");
			outChat.flush();
				
			String rep = inChat.readLine();
				
			if(rep.equals("AUTH_SUCCESSFUL")) {
				isConnect = true;
				endConnection = false;
			}
			else {
				isConnect = false;
				endConnection = true;
			}
			
		} catch(IOException e) { 
			e.printStackTrace();
		}
		
		return isConnect;
	}
	
	
	public void sendMessage(String msg) throws IOException {
		outChat.write(msg + "\n");
		outChat.flush();
		System.out.println("ENVOIE : " + msg);
	}
	
	public void closeChat() {
		
		endConnection = true;
	}
	
	public void run() {
		
		String message;
		
		while(!endConnection) {
			
			try {
				System.out.println("Attente");
				
				message = inChat.readLine();
				
				if(PictioLan.modele_gamer.getGame().getClient() != null)
					PictioLan.modele_gamer.getGame().getClient().getChat().addText(message);
				
				System.out.println("RECU : " + message);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				
				try {
					if(inChat != null) 
						inChat.close();
					
					if(outChat != null) 
						outChat.close();
					
					if(socketChat !=  null)
						socketChat.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
