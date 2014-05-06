package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class JServer extends JFrame{
   private JTabbedPane panelOnglet;
   private JConfiguration jp1;
   private ViewDictionary jp2;

   
   public JServer(JConfiguration config, ViewDictionary dic){
	   
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setPreferredSize(new Dimension(275,350));
	  jp1 = config;
	  jp2 = dic;
	  
      panelOnglet = new JTabbedPane();


      panelOnglet.addTab("Panel1", null, jp1);
      panelOnglet.addTab("Panel2", null, jp2);
      
      this.setTitle("Fenetre avec un JTabbedPane");
      this.getContentPane().add(panelOnglet);
//      this.setSize(500,500);
      pack();
      this.setVisible(true);
   }
}
