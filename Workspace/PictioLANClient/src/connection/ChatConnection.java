package connection;

import game.Gamer;
import gui.JProposition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatConnection implements Runnable {
	
	int chat_port = 3333;
	
	Gamer gamer;
	
	//Test
	int test;
	
	Thread chatThread;
	
	JProposition vue;
		
	Socket socketChat = null;
	
	BufferedReader inChat;
	BufferedWriter outChat;
	
	public ChatConnection (Gamer g, JProposition  pro) throws IOException {
		
		gamer = g;
		
		socketChat = new Socket(gamer.getIP(), chat_port);
		inChat = new BufferedReader(new InputStreamReader(socketChat.getInputStream()));
		outChat = new BufferedWriter(new OutputStreamWriter(socketChat.getOutputStream()));
		
		vue = pro;
		
		chatThread = new Thread(this);
		chatThread.start();
	}
	
	public void sendMessage(String msg) throws IOException {
		outChat.write(msg + "\n");
		outChat.flush();
		System.out.println("ENVOIE : " + msg);
	}
	
	public void run() {
		
		//ConnectionServer.this.authentification("AUTH_CONNECT");
		
		String message;
		while(true) {
			
			try {
				System.out.println("Attente");
				message = inChat.readLine();
				vue.addText(message);
				System.out.println("RECU : " + message);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
