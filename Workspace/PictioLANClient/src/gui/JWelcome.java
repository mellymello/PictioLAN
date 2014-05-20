package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextField;

import connection.ConnectionServer;

public class JWelcome implements Observer{

	private JFrame frame;
	private JTextField txtAdresseIp;
    private Pattern pattern;
    private Matcher matcher;
    
	private boolean enable = false;

	ConnectionServer connServer;
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	 
	/**
	 * Create the application.
	 */
	public JWelcome(ConnectionServer server) {
		connServer = server;
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
		frame.setResizable(false);
		frame.setTitle("Welcome - PictioLan");
		
		JPanel pButton = new JPanel();
		frame.getContentPane().add(pButton, BorderLayout.CENTER);
		pButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConnect = new JButton("Connect");
		pButton.add(btnConnect);
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
//				boolean isIpValide = true;
//				try {
//					InetAddress ip = InetAddress.getByName(txtAdresseIp.getText());
//				}
//				catch (UnknownHostException uhe) {
//					txtAdresseIp.setText("");
//					isIpValide = false;
//				}
				
				//if (isIpValide == true) {
					
					//Connexion au serveur !!
					connServer.setIP(txtAdresseIp.getText());
					connServer.launchConnexion();
					//Connexion au serveur !!
					
					JConnect connect = new JConnect(connServer);
					frame.dispose();
				//}
			}
		});
		
		JButton btnAnonymous = new JButton("Anonymous");
		pButton.add(btnAnonymous);
		btnAnonymous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Connexion au serveur !!
				connServer.setIP(txtAdresseIp.getText());
				connServer.launchConnexion();
				connServer.authentification("AUTH_ANONYMOUS");
				//Client client = new Client("Client PictioLan");
				//Connexion au serveur !!
			}
		});
		
		JButton btnSubscribe = new JButton("Subscribe");
		pButton.add(btnSubscribe);
		btnSubscribe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JSubscribe subscribe = new JSubscribe(JWelcome.this,connServer);
				frame.setEnabled(false);
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

	@Override
	public void update(Observable arg0, Object arg1) {
		frame.setEnabled(true);
	}
}
