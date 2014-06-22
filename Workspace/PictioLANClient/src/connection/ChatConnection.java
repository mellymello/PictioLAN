package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ChatConnection implements Runnable {
	
	int chat_port = 3333;
	
	Thread chatThread;
	
	Socket socketChat = null;
	
	BufferedReader inChat;
	BufferedWriter outChat;
	
	boolean endConnection = true;
	
	LinkedList<String> buffer = new LinkedList<String>();
 	
	public ChatConnection () throws IOException {
		
		socketChat = new Socket(PictioLan.modele_gamer.getIP(), chat_port);
		inChat = new BufferedReader(new InputStreamReader(socketChat.getInputStream()));
		outChat = new BufferedWriter(new OutputStreamWriter(socketChat.getOutputStream()));
	}

	public boolean isConnect() { return !endConnection; };

	public void startChat() {
		
		boolean actif = auth_protcole();
		
		if(!actif) {
			chatThread = new Thread(this);
			chatThread.start();
		}
	}
	
	private boolean auth_protcole() { 
		
		try {
			
			outChat.write("AUTH\n");
			outChat.flush();
			
			String pseudo = PictioLan.modele_gamer.getPseudo();
				
			outChat.write(pseudo + "\n");
			outChat.flush();
				
			String rep = inChat.readLine();
			
			endConnection = !rep.equals("AUTH_SUCCESS");
			
		} catch(IOException e) { 
			e.printStackTrace();
		}
		
		return endConnection;
	}
	
	public synchronized boolean isBufferEmpty() {
		return buffer.isEmpty();
	}
	
	public synchronized void addMessageToBuffer(String s) {
		buffer.add(s);
	}
	
	public synchronized LinkedList<String> getMessagesToBuffer() {
		
		LinkedList<String> temp = new LinkedList<String>();
		
		for(String s : buffer)
			temp.add(s);
		
		return temp;
	}
	
	public synchronized void removeMessagesToBuffer() {
		buffer.remove();
	}
	
	private void sendMessage() throws IOException {
		
		LinkedList<String> temp = getMessagesToBuffer();
		
		for(String msg : temp) {
			
			outChat.write("CHAT_SEND_MESSAGE\n");
			outChat.flush();
			
			outChat.write(msg + "\n");
			outChat.flush();
		}
		
		removeMessagesToBuffer();
	}

	private void getMessage() throws IOException {
		
		outChat.write("CHAT_GET_MESSAGE\n");
		outChat.flush();
		
		int nbMessages = inChat.read();

		for(int i=0; i < nbMessages; i++) {
			
			String message = inChat.readLine();
			
			if(PictioLan.modele_gamer.getGame().getClient() != null)
				PictioLan.modele_gamer.getGame().getClient().getChat().addText(message);
		}
	}
	
	
	public void closeChat() {
		endConnection = true;
	}
	
	public void run() {
		
		String s;
		String message;

		try {
			
			while (!endConnection) {
				
				boolean launchGame = PictioLan.modele_gamer.getGame().getListGamers().size() == PictioLan.modele_gamer.getGame().getNbMaxGamers();
				
				if(launchGame) {
				
					if(!isBufferEmpty()) {
						sendMessage();
					}
					
					getMessage();
				}
				
				try {
					chatThread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			outChat.write("CLOSE\n");
			outChat.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (inChat != null)
					inChat.close();

				if (outChat != null)
					outChat.close();

				if (socketChat != null)
					socketChat.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
