package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JTextField;
import javax.swing.JButton;

public class JSubscribe extends Observable{

	private JFrame frame;
	private JTextField txtPseudo;
	private JTextField txtPassword;
	private JTextField txtEmail;


	/**
	 * Create the application.
	 */
	public JSubscribe(JConnect w) {
		initialize();
		

	    //frame.setUndecorated(true);
	    
		frame.setVisible(true);
		this.addObserver(w);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Subscribe and have an account !!!");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtPseudo = new JTextField();
		txtPseudo.setText("Pseudo");
		panel.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JButton btnSubscribe = new JButton("Subscribe");
		panel.add(btnSubscribe);
		btnSubscribe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers();
				frame.dispose();			
			}
		});
	}

}
