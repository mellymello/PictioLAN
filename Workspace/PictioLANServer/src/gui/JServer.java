package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class JServer extends JFrame{
   private JTabbedPane panelOnglet;
   private JPanel jp1 = new JPanel(), jp2 = new JPanel();
   private JLabel jl1 = new JLabel("un label pour le panel1"), jl2 = new JLabel("un label pour le panel2");
   
   public JServer(){
      panelOnglet = new JTabbedPane();
      jp1.add(jl1);
      jp2.add(jl2);
      panelOnglet.addTab("Panel1", null, jp1);
      panelOnglet.addTab("Panel2", null, jp2);
      this.setTitle("Fenetre avec un JTabbedPane");
      this.getContentPane().add(panelOnglet);
      this.setSize(500,500);
      this.setVisible(true);
   }
   public static void main(String[]arg){
	   JServer f = new JServer();
   }
}