
 package gui;

import javax.swing.*;

import java.awt.*;

public class JInfoConnexion extends JPanel implements Configuration
{
	//Group the radio buttons.
    private ButtonGroup  groupe  = new ButtonGroup();
    private JRadioButton equipe1 = new JRadioButton("1");
    private JRadioButton equipe2 = new JRadioButton("2");
	private JLabel lblEquipe = new JLabel("Equipe");
    
    private JPanel pEquipe = new JPanel();
	
	public JInfoConnexion()
	{
		this.setOpaque(false);
		this.setLayout(new FlowLayout());
		
		pEquipe.setLayout(new FlowLayout(FlowLayout.LEFT));
	    pEquipe.setOpaque(false);
	    
	    equipe1.setOpaque(false);
	    equipe1.setForeground(Color.WHITE);
	    equipe2.setOpaque(false);
	    equipe2.setForeground(Color.WHITE);
	    
		equipe1.setSelected(true);
	    
	    lblEquipe.setForeground(Color.WHITE);
	    
	    groupe.add(equipe1);
	    groupe.add(equipe2);
	    
	    pEquipe.add(lblEquipe);
		pEquipe.add(equipe1);
		pEquipe.add(equipe2);
		
		this.add(pEquipe);
	
	}
}
