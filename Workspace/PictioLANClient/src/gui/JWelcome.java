package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JWelcome {

	private JFrame frame;
	private JTextField txtAdresseIp;
    private Pattern pattern;
    private Matcher matcher;
	 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JWelcome window = new JWelcome();
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
	public JWelcome() {
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
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				
				boolean isIpValide = true;
				try
				{
					InetAddress ip = InetAddress.getByName(txtAdresseIp.getText());
				}
				catch (UnknownHostException uhe)
				{
				
					txtAdresseIp.setText("");
					isIpValide = false;
				}
				if (isIpValide == true)
				{
					JConnect connect = new JConnect();
					frame.dispose();
				}

			}
		});
		
		JButton btnAnonymous = new JButton("Anonymous");
		pButton.add(btnAnonymous);
		btnAnonymous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JButton btnSubscribe = new JButton("Subscribe");
		pButton.add(btnSubscribe);
		btnSubscribe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JSubscribe subscribe = new JSubscribe();
				frame.dispose();
				
			}
		});
		
		JPanel pText = new JPanel();
		frame.getContentPane().add(pText, BorderLayout.SOUTH);
		pText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtAdresseIp = new JTextField();
		txtAdresseIp.setText("Adresse IP");
		pText.add(txtAdresseIp);
		txtAdresseIp.setColumns(10);
		txtAdresseIp.addMouseListener(new MouseListener() {
			
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
				txtAdresseIp.setText("");
				
			}
		});
		
	}

}
