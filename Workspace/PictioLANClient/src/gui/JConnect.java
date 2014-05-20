package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import connection.ConnectionServer;

public class JConnect {

	private JFrame frame;
	private JTextField txtPseudo;
	private JTextField txtPassword;

	private ConnectionServer connServer;
	
	/**
	 * Create the application.
	 */
	public JConnect(ConnectionServer c) {
		initialize();
		frame.setVisible(true);
		frame.pack();
		
		connServer = c;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Connect to our db");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtPseudo = new JTextField();
		txtPseudo.setText("Pseudo");
		panel.add(txtPseudo);
		txtPseudo.setColumns(10);
		txtPseudo.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtPseudo.setText("");
				
			}
		});
		
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				txtPassword.setText("");
				
			}
		});
		
		JButton btnOk = new JButton("OK");
		panel.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Authentification !!
				connServer.setPseudo(txtPseudo.getText());
				connServer.setPassword(txtPassword.getText());
				connServer.authentification("AUTH_CONNECT");
				//Authentification !!
			}
		});
	}

}
