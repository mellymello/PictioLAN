package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import connection.ConnectionServer;

public class JCreateGame extends JFrame{
	
	private ConnectionServer conn;
	
	private JTextField txtNombreDeJoueurs;

    private ButtonGroup  groupe  = new ButtonGroup();
    private JRadioButton rdbtnSingle = new JRadioButton("Single");
    private JRadioButton rdbtnMulti = new JRadioButton("Multi");
	/**
	 * 
	 * Create the application.
	 */
	public JCreateGame(String title, ConnectionServer conn) {
		
		this.conn = conn;
		
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtNombreDeJoueurs = new JTextField();
		txtNombreDeJoueurs.setText("Nombre de joueurs");
		panel.add(txtNombreDeJoueurs);
		txtNombreDeJoueurs.setColumns(10);
		
		
		groupe.add(rdbtnSingle);
		groupe.add(rdbtnMulti);
		panel.add(rdbtnSingle);
		panel.add(rdbtnMulti);
		
		JButton btnStart = new JButton("Start");
		panel.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCreateGame.this.conn.createGame(Integer.parseInt(txtNombreDeJoueurs.getText()), rdbtnMulti.isSelected());
			}
		});
		
		this.setVisible(true);
		this.pack();
	}

}
