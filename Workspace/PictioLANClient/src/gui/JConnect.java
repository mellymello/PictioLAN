package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class JConnect {

	private JFrame frame;
	private JTextField txtPseudo;
	private JTextField txtPassword;


	/**
	 * Create the application.
	 */
	public JConnect() {
		initialize();
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		JButton btnOk = new JButton("OK");
		panel.add(btnOk);
	}

}
