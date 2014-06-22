package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import org.w3c.dom.ls.LSInput;

import server.ManagerGamer;
import game.*;

public class ChatHandler implements Runnable {
	
	Socket connexion = null;
	Thread threadChat;
	
	BufferedReader in;
	BufferedWriter out;
	
	boolean endConnection = false;
	
	Gamer gamer = null;
	
	LinkedList<String> list_message = new LinkedList<String>();
	
	public ChatHandler(Socket s) throws IOException {
		
		connexion = s;
		
		in = new BufferedReader (new InputStreamReader (connexion.getInputStream()));
		out = new BufferedWriter (new OutputStreamWriter(connexion.getOutputStream()));
		
		threadChat = new Thread(this);
		threadChat.start();
	}
	
	public void auth_protocole() throws IOException { 
		
		String pseudo = in.readLine();
		
		gamer = ManagerGamer.getGamer(pseudo);
		
		if(gamer != null) {
			out.write("AUTH_SUCCESS\n");
			out.flush();
			
			gamer.setChat(this);
		}
		else {
			out.write("AUTH_FAILED\n");
			out.flush();
		}
	}
	
	public void addMessage(String s) {
		list_message.add(s);
	}
	
	public void send() { 
		
		try {
			out.write(list_message.size());
			out.flush();
			
			for(int i=0; i < list_message.size(); i++) {
				out.write(list_message.get(i) + "\n");
				out.flush();
				
				list_message.remove(i);
			}
			
//			if(list_message != null)
//				list_message.clear();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendError() {
		try {
			out.write(0);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void close() {
		endConnection = true;
	}
	
	@Override
	public void run() {

		try {
			
			while(!endConnection) {
				
				String tmp = in.readLine();

				if(tmp.equals("AUTH")) {
					auth_protocole();
				}
				else if(tmp.equals("CHAT_GET_MESSAGE")) {
					
					if(gamer.getGame() != null) {
						
						if(gamer.getGame().getListChat().isEmpty()) {
							sendError();
						}
						else {
						
							for(ChatHandler c : gamer.getGame().getListChat()) {
								
								if(this != c) 
									c.send();
							}
						}
						
					}
					else {
						sendError();
					}
				
				}
				else if(tmp.equals("CHAT_SEND_MESSAGE")) {
					
					String message = in.readLine();
					
					System.out.println("CHAT : " + gamer.getGame().getWord() + "==" + message);
					
					if(gamer.getGame() != null && gamer.getGame().getWord().equalsIgnoreCase(message)) {
						gamer.getGame().getRoundActive().setWinner(gamer);
//						gamer.getConnection().threadConnexion.notify();
						System.out.println("SOMEONE WIN");
					}
					
					if(gamer.getGame() != null) {
						
						for(ChatHandler c : gamer.getGame().getListChat()) {
							if(this != c) {
								c.addMessage(message);
							}
						}
					}
				
				}
				else if(tmp.equals("CLOSE")) {
					endConnection = true;
					break;
				}
				
			} // fin de la boucle
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				if(in != null)
					in.close();
				if(out != null)
					out.close();
				if(connexion != null)
					connexion.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}