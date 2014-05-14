package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import server.*;


public class JConfiguration extends JPanel{
	
	private Server config;
	
	
	private JButton buttonStart;
	private BufferedImage buttonStartImage;
	private ImageIcon buttonStartIcon;
	private BufferedImage buttonStartPressedImage;
	private ImageIcon buttonStartPressedIcon;

	private JButton buttonStop;
	private BufferedImage buttonStopImage;
	private ImageIcon buttonStopIcon;
	private BufferedImage buttonStopPressedImage;
	private ImageIcon buttonStopPressedIcon;

	private Image backGroundImage;

	private JPanel buttonPanel;
	
	public JConfiguration(Server c) {
		config = c;

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setOpaque(false);

		this.setLayout(new BorderLayout());

		try {

			// funny buttons
			buttonStartImage = ImageIO.read(getClass().getResource(
					"/img/startButton.png"));
			buttonStartIcon = new ImageIcon(buttonStartImage);
			buttonStartPressedImage = ImageIO.read(getClass().getResource(
					"/img/startButtonPressed.png"));
			buttonStartPressedIcon = new ImageIcon(buttonStartPressedImage);

			buttonStart = new JButton(buttonStartIcon);
			buttonStart.setBorder(BorderFactory.createEmptyBorder());
			buttonStart.setContentAreaFilled(false);

			buttonStopImage = ImageIO.read(getClass().getResource(
					"/img/stopButton.png"));
			buttonStopIcon = new ImageIcon(buttonStopImage);
			buttonStopPressedImage = ImageIO.read(getClass().getResource(
					"/img/stopButtonPressed.png"));
			buttonStopPressedIcon = new ImageIcon(buttonStopPressedImage);

			buttonStop = new JButton(buttonStopPressedIcon);

			buttonStop.setBorder(BorderFactory.createEmptyBorder());
			buttonStop.setContentAreaFilled(false);

			// for setting background
			backGroundImage = new ImageIcon(getClass().getResource(
					"/img/background.jpg")).getImage();

		} catch (IOException e) {
			// the image is not found...do a simple button

			buttonStart = new JButton("Start");
			buttonStop = new JButton("Stop");
		}

		buttonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				System.out.println("Starting UP");
				config.startListener();
				buttonStart.setIcon(buttonStartPressedIcon);
				buttonStop.setIcon(buttonStopIcon);
				

			}
		});

		buttonStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Shut Down NOW !");
				config.stopListener();
				buttonStop.setIcon(buttonStopPressedIcon);
				buttonStart.setIcon(buttonStartIcon);

			}
		});

		buttonPanel.add(buttonStart );
		buttonPanel.add(buttonStop);
		
		this.add(buttonPanel, BorderLayout.CENTER);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
	}

}
