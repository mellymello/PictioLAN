
 package gui_Client;

import javax.swing.*;

import java.awt.*;

public class InfoConnexion extends JPanel implements configuration.Configuration
{
	
	//Group the radio buttons.
    private ButtonGroup  groupe  = new ButtonGroup();
    private JRadioButton equipe1 = new JRadioButton("1");
    private JRadioButton equipe2 = new JRadioButton("2");
	private JLabel lblEquipe = new JLabel("Equipe");
    
    private JPanel pEquipe = new JPanel();
	
	public InfoConnexion()
	{
		this.setLayout(new FlowLayout());
		
		pEquipe.setLayout(new FlowLayout(FlowLayout.LEFT));
	    equipe1.setSelected(true);
	    
	    pEquipe.add(lblEquipe);
		pEquipe.add(equipe1);
		pEquipe.add(equipe2);
		
		this.add(pEquipe);
	
	}
}
