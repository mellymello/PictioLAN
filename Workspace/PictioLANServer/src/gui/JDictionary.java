package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.ListModel;

import dictionary.Dictionary;

public class JDictionary extends JPanel {

	private Dictionary dictionary;

	private JList<String> words;
	private JList<String> categories;

	private JPanel listsPanel;
	private JPanel categoryPanel;
	private JPanel wordsPanel;
	private JPanel buttonsPanel;
	private JPanel buttonPanelListCtrl;

	private JLabel wordsLabel;
	private JLabel categoriesLabel;

	private JButton refreshButton;
	private JButton addCatButton;
	private JButton addWordButton;
	private JButton deleteCatButton;
	private JButton deleteWordButton;

	public JDictionary(Dictionary d) {

		dictionary = d;

		// setting layouts to components
		setLayout(new BorderLayout());
		listsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel = new JPanel(new GridLayout(1,2));
		buttonPanelListCtrl = new JPanel(new GridLayout(2,2));

		categoryPanel = new JPanel(new BorderLayout());
		wordsPanel = new JPanel(new BorderLayout());

		wordsLabel = new JLabel("Words");
		categoriesLabel = new JLabel("Categories");

		words = new JList<String>();
		categories = new JList<String>();

		buttonsPanel.setBackground(Color.gray);
		categoryPanel.setBackground(Color.orange);
		wordsPanel.setBackground(Color.cyan);

		categoryPanel.add(categoriesLabel, BorderLayout.NORTH);
		categoryPanel.add(categories, BorderLayout.CENTER);

		wordsPanel.add(wordsLabel, BorderLayout.NORTH);
		wordsPanel.add(words, BorderLayout.CENTER);

		listsPanel.add(categoryPanel);
		listsPanel.add(wordsPanel);

		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] s = new String[dictionary.getListCategory().size()];

				for (int i = 0; i < dictionary.getListCategory().size(); i++)
					s[i] = dictionary.getListCategory().get(i);
				categories.setListData(s);

			}
		});
		addCatButton = new JButton("Add Category");
		addCatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Adding Category");
				String c = JOptionPane.showInputDialog(null,
						"Enter a category", "add category",
						JOptionPane.QUESTION_MESSAGE);
				if (c != null) {
					System.out.println(c);
				}

			}
		});
		addWordButton = new JButton("Add Word");
		addWordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Adding word");
				String w = JOptionPane.showInputDialog(null, "Enter a word",
						"add word", JOptionPane.QUESTION_MESSAGE);
				if (w != null) {
					System.out.println(w);
				}
			}
		});

		deleteCatButton = new JButton("Remove Category");
		deleteCatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedCat;
				if((selectedCat=categories.getSelectedValue())!=null){
					System.out.println("Removing :" +selectedCat);
				}
				else{
					JOptionPane.showMessageDialog(null, "You have to select a category !","Warning", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		deleteWordButton = new JButton("Remove Word");
		deleteWordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedWord;
				if((selectedWord=words.getSelectedValue())!=null){
					System.out.println("Removing :" +selectedWord);
				}
				else{
					JOptionPane.showMessageDialog(null, "You have to select a word !","Warning", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		buttonsPanel.add(refreshButton);
		
		buttonPanelListCtrl.add(addCatButton);
		buttonPanelListCtrl.add(addWordButton);
		buttonPanelListCtrl.add(deleteCatButton);
		buttonPanelListCtrl.add(deleteWordButton);

		buttonsPanel.add(buttonPanelListCtrl);
		
		add(listsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
}
