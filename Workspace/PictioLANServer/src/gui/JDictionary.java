package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import dictionary.Dictionary;

public class JDictionary extends JPanel {

	private Dictionary dictionary;

	private JList<String> words;
	private JList<String> categories;

	private JScrollPane scrollWords;
	private JScrollPane scrollCategories;

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

	private String selectedCat;
	private String selectedWord;

	public JDictionary(Dictionary d) {

		dictionary = d;

		// setting layouts to components
		setLayout(new BorderLayout());
		listsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonPanelListCtrl = new JPanel(new GridLayout(2, 2));

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
		scrollCategories = new JScrollPane(categories);
		categoryPanel.add(scrollCategories, BorderLayout.CENTER);

		wordsPanel.add(wordsLabel, BorderLayout.NORTH);
		scrollWords = new JScrollPane(words);
		wordsPanel.add(scrollWords, BorderLayout.CENTER);

		listsPanel.add(categoryPanel);
		listsPanel.add(wordsPanel);

		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectedCat = categories.getSelectedValue();

				LinkedList<String> tmpCat = dictionary.getListCategory();
				String[] cat = new String[tmpCat.size()];

				for (int i = 0; i < tmpCat.size(); i++) {
					cat[i] = tmpCat.get(i);
				}
				categories.setListData(cat);

				if (selectedCat != null) {
					LinkedList<String> tmpWords = dictionary
							.getListWordCategory(selectedCat);
					String[] w = new String[tmpWords.size()];

					for (int i = 0; i < tmpWords.size(); i++) {
						w[i] = tmpWords.get(i);
					}
					words.setListData(w);
				}

			}
		});
		addCatButton = new JButton("Add Category");
		addCatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String c = JOptionPane.showInputDialog(null,
						"Enter a category", "add category",
						JOptionPane.QUESTION_MESSAGE);
				if (c != null) {
					dictionary.addCategory(c);
				}

			}
		});
		addWordButton = new JButton("Add Word");
		addWordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				selectedCat = categories.getSelectedValue();
				if (selectedCat != null) {

					String w = JOptionPane.showInputDialog(null,
							"Enter a word", "add word",
							JOptionPane.QUESTION_MESSAGE);
					if (w != null) {
						dictionary.addWord(w, selectedCat);
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"You have to select a category !", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		deleteCatButton = new JButton("Remove Category");
		deleteCatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((selectedCat = categories.getSelectedValue()) != null) {
					dictionary.deleteCategory(selectedCat);
				} else {
					JOptionPane.showMessageDialog(null,
							"You have to select a category !", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		deleteWordButton = new JButton("Remove Word");
		deleteWordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((selectedWord = words.getSelectedValue()) != null) {
					dictionary.deleteWord(selectedWord);
				} else {
					JOptionPane.showMessageDialog(null,
							"You have to select a word !", "Warning",
							JOptionPane.WARNING_MESSAGE);
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
