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
   private JDictionary jp2;
   private JGamer jp3;
   private JStatistic jp4;
   private JActivesGames jp5;

   
   public JServer(JConfiguration config, JDictionary dic, JGamer gamers, JStatistic stat, JActivesGames activesGames){
	   
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setPreferredSize(new Dimension(275,350));
	  jp1 = config;
	  jp2 = dic;
	  jp3= gamers;
	  jp4 = stat;
	  jp5= activesGames;
	  
      panelOnglet = new JTabbedPane();


      panelOnglet.addTab("Config", null, jp1);
      panelOnglet.addTab("Dictionary", null, jp2);
      panelOnglet.addTab("Gamers", null, jp3);
      panelOnglet.addTab("Statistic", null, jp4);
      panelOnglet.addTab("Actives Games", null ,jp5);
      
      
      
      this.setTitle("Server");
      this.getContentPane().add(panelOnglet);
      pack();
      this.setMinimumSize(new Dimension(250, 325));
      this.setVisible(true);
   }
}
