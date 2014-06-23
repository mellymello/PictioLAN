package gui;

import javax.swing.*;

import connection.PictioLan;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class JDraw extends JPanel implements Configuration {

	protected Vector<Point> points = new Vector<Point>();

	protected Vector<Point> pointsToSend = new Vector<Point>();

	protected BufferedImage img;

	private JDrawListener ecouteur;

	private boolean enabled = true;
	private Color currentColor = Color.BLACK;
	BufferedImage bImage = new BufferedImage(LARGEUR_DESSIN, HAUTEUR_DESSIN,
			BufferedImage.TYPE_INT_RGB);

	public JDraw(JClient client) {
		ecouteur = new JDrawListener(this, client);
		this.addMouseListener(ecouteur);
		this.addMouseMotionListener(ecouteur);

		Graphics g2d = bImage.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, LARGEUR_DESSIN, HAUTEUR_DESSIN);
		g2d.dispose();
	}

	public void addPoint(Point p) {
		points.addElement(p);
		pointsToSend.addElement(p);
	}

	public Vector<Point> getAllPoints() {
		return pointsToSend;
	}

	public void clearPointsToSend() {
		pointsToSend.clear();
	}

	public void effacerDessin() {
		points.clear();
		pointsToSend.clear();
		Graphics g = bImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, LARGEUR_DESSIN, HAUTEUR_DESSIN);
		g.dispose();
		repaint();
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public BufferedImage getImage() {
		return img;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (PictioLan.modele_gamer.getGame().isDrawer()) {
			drawImage();
			g.drawImage(bImage, 0, 0, null);
			drawLines(g);

			// store the image
			img = bImage;
			try {
				PictioLan.modele_gamer.getDraw().sendMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			g.drawImage(img, 0, 0, null);
		}

	}

	public void drawImage() {
		Graphics g = bImage.getGraphics();
		drawLines(g);
		g.dispose();
	}

	public void drawLines(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		g2d.setStroke(new BasicStroke(RAYON, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));

		if (points != null && points.size() > 1) {

			g.setColor(currentColor);
			for (int i = 0; i < points.size() - 1; i++) {
				int x1 = points.get(i).x;
				int y1 = points.get(i).y;
				int x2 = points.get(i + 1).x;
				int y2 = points.get(i + 1).y;
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		enabled = isEnabled;
	}

	public void setCurrentColor(Color c) {
		currentColor = c;
	}
}

class JDrawListener implements MouseListener, MouseMotionListener {
	private JDraw panel;
	private JClient client;

	JDrawListener(JDraw panel, JClient client) {
		this.panel = panel;
		this.client = client;
	}

	public void mouseDragged(MouseEvent e) {
		if (panel.getEnabled()) {
			panel.addPoint(new Point(e.getPoint()));
			panel.repaint();
//			client.sendPoint(new Point(e.getPoint()));
		}
	}

	public void mousePressed(MouseEvent e) {
		if (panel.getEnabled()) {

			// panel.image.add(new Vector<Point>(panel.points));

			panel.points.clear();
			panel.addPoint(new Point(e.getPoint()));
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (panel.getEnabled()) {
			panel.addPoint(new Point(e.getPoint()));

			// panel.image.add(new Vector<Point>(panel.points));

			panel.points.clear();
//			client.sendPoint(new Point(e.getPoint()));
			panel.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
