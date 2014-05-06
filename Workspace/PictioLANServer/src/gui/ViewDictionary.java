package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


import javax.swing.ListModel;

import dictionary.Dictionary;

public class ViewDictionary extends JPanel {

	private Dictionary dictionary;

	private JList<String> words;
	private JList<String> categories;
	
	private JPanel listsPanel;
	private JPanel categoryPanel;
	private JPanel wordsPanel;
	private JPanel buttonsPanel;
	
	private JLabel wordsLabel;
	private JLabel categoriesLabel;
	
	private JButton refreshButton;
	private JButton addCatButton;
	private JButton addWordButton;
	private JButton deleteCatButton;
	private JButton deleteWordButton;
	
	
	
	public ViewDictionary(Dictionary d){
		
		dictionary = d;
		
		//setting layouts to components
		setLayout(new BorderLayout());
		listsPanel= new JPanel(new GridLayout(1,2));
		buttonsPanel= new JPanel(new FlowLayout());
		
		categoryPanel = new JPanel(new BorderLayout());
		wordsPanel = new JPanel(new BorderLayout());
		
		wordsLabel = new JLabel("Words");
		categoriesLabel= new JLabel("Categories");
		
		words = new JList<String>();
		categories = new JList<String>();
		
		buttonsPanel.setBackground(Color.gray);
		categoryPanel.setBackground(Color.orange);
		wordsPanel.setBackground(Color.cyan);
		
		categoryPanel.add(categoriesLabel,BorderLayout.NORTH);
		categoryPanel.add(categories,BorderLayout.CENTER);
		
		wordsPanel.add(wordsLabel,BorderLayout.NORTH);
		wordsPanel.add(words,BorderLayout.CENTER);
		
	
		listsPanel.add(categoryPanel);
		listsPanel.add(wordsPanel);
		
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] s = new String[dictionary.getListCategory().size()];
				
				for(int i=0; i < dictionary.getListCategory().size(); i++)
					s[i] = dictionary.getListCategory().get(i);
				categories.setListData(s);
				
			}
		});
		addCatButton = new JButton("Add Category");
		addCatButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Adding Category");
				
			}
		});
		addWordButton = new JButton("Add Word");
		addWordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Adding word");
				
			}
		});
		
		deleteCatButton = new JButton("Remove Category");
		deleteCatButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Removing category");
				
			}
		});
		
		deleteWordButton = new JButton("Remove Word");
		deleteWordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("removing word");
				
			}
		});
		
		buttonsPanel.add(refreshButton);
		buttonsPanel.add(addCatButton);
		buttonsPanel.add(addWordButton);
		buttonsPanel.add(deleteCatButton);
		buttonsPanel.add(deleteWordButton);
		
		
		add(listsPanel,BorderLayout.CENTER);
		add(buttonsPanel,BorderLayout.SOUTH);
	}
}
