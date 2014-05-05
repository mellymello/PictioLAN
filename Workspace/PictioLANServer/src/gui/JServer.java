package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class JServer extends JFrame {
	private JTabbedPane panelOnglet;
	private JPanel jp1 = new ViewDictionary();
	private JPanel jp2 = new JPanel();

	private JLabel jl2 = new JLabel("un label pour le panel2");

	public JServer() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelOnglet = new JTabbedPane();
		
		jp2.add(jl2);
		panelOnglet.addTab("Dictionary", null, jp1);
		panelOnglet.addTab("Panel2", null, jp2);
		this.setTitle("Fenetre avec un JTabbedPane");
		this.getContentPane().add(panelOnglet);
		this.setSize(500, 500);
		this.setVisible(true);
	}

	public static void main(String[] arg) {
		JServer f = new JServer();

	}
}
