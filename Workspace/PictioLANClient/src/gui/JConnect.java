package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JLabel;

public class JConnect {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JConnect window = new JConnect();
					window.frame.setVisible(true);
					window.frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JConnect() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pButton = new JPanel();
		frame.getContentPane().add(pButton, BorderLayout.CENTER);
		pButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConnect = new JButton("Connect");
		pButton.add(btnConnect);
		
		JButton btnAnonymous = new JButton("Anonymous");
		pButton.add(btnAnonymous);
		
		JButton btnSubscribe = new JButton("Subscribe");
		pButton.add(btnSubscribe);
		
		JPanel pText = new JPanel();
		frame.getContentPane().add(pText, BorderLayout.SOUTH);
		pText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIpAdresse = new JLabel("IP Adresse:");
		pText.add(lblIpAdresse);
		
	}

}
