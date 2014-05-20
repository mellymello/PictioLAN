package gui;

import gamer.ManageGamer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class JGamer extends JPanel implements ItemListener{

	private LinkedList<String> activesGamers;
	
	private LinkedList<JCheckBox> gamersCheckboxes;
	
	private JButton deleteSelectedPlayer;
	
	private JButton activateDesactivateAll;
	private boolean allSelected;
	
	private JPanel buttonPanel;
	private JPanel checkBoxPanel;
	
	public JGamer(){
		
		this.setLayout(new BorderLayout());
		activesGamers= new LinkedList<String>();
		
		gamersCheckboxes=new LinkedList<JCheckBox>();
		for (int i = 0; i < activesGamers.size(); i++) {
			gamersCheckboxes.add(new JCheckBox(activesGamers.get(i)));
			gamersCheckboxes.get(i).addItemListener(this);
		}
		
		allSelected=true;
		selectAll();
		
		deleteSelectedPlayer= new JButton("del players");
		
		deleteSelectedPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0; i<gamersCheckboxes.size();i++){
					if(gamersCheckboxes.get(i).isSelected()){
						ManageGamer.deleteGamer(gamersCheckboxes.get(i).getText());
						gamersCheckboxes.remove(i);
						setGamerCheckBoxes();
					}
				}
				
			}
		});
		
		activateDesactivateAll= new JButton("Activate/Desactivate ALL");
		activateDesactivateAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(allSelected){
					deselectAll();
					allSelected=false;
				}
				else{
					selectAll();
					allSelected=true;
				}
			}
		});
		buttonPanel=new JPanel(new FlowLayout());
		buttonPanel.add(activateDesactivateAll);
		buttonPanel.add(deleteSelectedPlayer);
		
		checkBoxPanel= new JPanel(new GridLayout(activesGamers.size(), 0));
		setGamerCheckBoxes();
		
		this.add(checkBoxPanel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
	}
	
	private void setGamerCheckBoxes( ){
		checkBoxPanel.removeAll();
		for (int i = 0; i < gamersCheckboxes.size(); i++) {
			checkBoxPanel.add(gamersCheckboxes.get(i));
		}
		repaint();
	}
	
	private void selectAll(){
		for (int i = 0; i < gamersCheckboxes.size(); i++) {
			gamersCheckboxes.get(i).setSelected(true);
		}
	}
	
	private void deselectAll(){
		for (int i = 0; i < gamersCheckboxes.size(); i++) {
			gamersCheckboxes.get(i).setSelected(false);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		for (int i = 0; i < gamersCheckboxes.size(); i++) {
			if(gamersCheckboxes.get(i).equals(e.getItem())){
				System.out.println("Do some action over gamer: "+ gamersCheckboxes.get(i).getText());
			}
		}
	}
}
