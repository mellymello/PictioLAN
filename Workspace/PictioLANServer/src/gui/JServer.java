package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class JServer extends JFrame{
   private JTabbedPane panelOnglet;
   private JConfiguration jp1;
   private ViewDictionary jp2;
   private JLabel jl1 = new JLabel("Server Listener"), jl2 = new JLabel("Dictionnaire");
   
   public JServer(JConfiguration config, ViewDictionary dic){
	   
	  jp1 = config;
	  jp2 = dic;
	  
      panelOnglet = new JTabbedPane();
      
      jp1.add(jl1,BorderLayout.NORTH);
      jp2.add(jl2,BorderLayout.NORTH);

      panelOnglet.addTab("Panel1", null, jp1);
      panelOnglet.addTab("Panel2", null, jp2);
      
      this.setTitle("Fenetre avec un JTabbedPane");
      this.getContentPane().add(panelOnglet);
      this.setSize(500,500);
      this.setVisible(true);
   }
}
