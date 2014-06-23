/*************************************************************

 ************************************************************/
package gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;

public class JDraw extends JPanel implements Configuration {
	protected Vector<Point> points = new Vector<Point>();

	private JDrawListener ecouteur;

	private boolean enabled = true;
	private Color currentColor = Color.BLACK;
	BufferedImage bImage = new BufferedImage(LARGEUR_DESSIN, HAUTEUR_DESSIN,
			BufferedImage.TYPE_INT_RGB);

	private JClient vue_parente;
	
	public JDraw(JClient client) {
		vue_parente = client;
		ecouteur = new JDrawListener(this, client);
		this.addMouseListener(ecouteur);
		this.addMouseMotionListener(ecouteur);

		Graphics g2d = bImage.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, LARGEUR_DESSIN, HAUTEUR_DESSIN);
		g2d.dispose();
		
		this.setEnabled(false);
	}

	public void addPoint(Point p) {
		points.addElement(p);
//		System.out.print("(" + p.x + "," + p.y + ")");
		ecouteur.refreshDraw();
	}
	
	public Vector<Point> getAllPoints(){
		return points;
	}

	public void effacerDessin() {
		points.clear();
		Graphics g = bImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, LARGEUR_DESSIN, HAUTEUR_DESSIN);
		g.dispose();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawImage();
		g.drawImage(bImage, 0, 0, null);
		drawLines(g);
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
//			client.sendPoint(new Point(e.getPoint()));
			panel.repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		if (panel.getEnabled()) {
			panel.points.clear();
			panel.addPoint(new Point(e.getPoint()));
			// client.sendPoint(new Rectangle(new Point(e.getPoint()));
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (panel.getEnabled()) {
			panel.addPoint(new Point(e.getPoint()));
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
	
	public void refreshDraw() {
		panel.repaint();
	}

}
