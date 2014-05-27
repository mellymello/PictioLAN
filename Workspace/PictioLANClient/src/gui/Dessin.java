/*************************************************************
 *Projet: Dessinary  GEN 2006
 *Fichier: Dessin.java
 *Auteur: Marazzi Laurent, Reymondin Louis, Carrupt Etienne
 *		  Scalfo Christophe, Melly Jonathan, Sauvin Anthony
 *Dernière modification: 10 mai 2006
 *Description: Interface de la zone de dessin
 *
 *Compilateur: java 1.5
 *
 ************************************************************/
package gui;
import javax.swing.*;

import configuration.Configuration;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;

public class Dessin extends JPanel implements Configuration
{
	protected Vector<Point> points = new Vector<Point>();
	
	private DessinEcouteur ecouteur;
	
	private boolean enabled = true;
	private Color currentColor = Color.BLACK;
	BufferedImage bImage = new BufferedImage(700, 500, BufferedImage.TYPE_INT_RGB);

	
	
	public Dessin(Client client)
	{
		ecouteur = new DessinEcouteur(this,client);
		this.addMouseListener(ecouteur);
		this.addMouseMotionListener(ecouteur);
		
        Graphics g2d = bImage.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 700, 500);
        g2d.dispose();
	}
	
	public void addPoint(Point p)
	{
		points.addElement(p);
	}
	
	public void effacerDessin()
	{
        points.clear();
        Graphics g = bImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 700, 500);
        g.dispose();
        repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
		g2d.setStroke(new BasicStroke(RAYON,BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		
        drawImage();
        g.drawImage(bImage,0,0,null);
        drawLines(g);
	}
	
	
   public void drawImage()
    {
        Graphics g = bImage.getGraphics();
        drawLines(g);
        g.dispose();
    }

    public void drawLines(Graphics g)
    {
         if(points != null && points.size() > 1)
         {

             g.setColor(getCurrentColor());
              for(int i = 0; i < points.size()-1;i++)
               {
                   int x1 = points.get(i).x;
                   int y1 = points.get(i).y;
                   int x2 = points.get(i+1).x;
                   int y2 = points.get(i+1).y;
                   g.drawLine(x1, y1, x2, y2);
               }
         }
    }
	public boolean getEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean isEnabled)
	{
		enabled = isEnabled;
	}
	public void setCurrentColor (Color c)
	{
		currentColor = c;
	}
	public Color getCurrentColor()
	{
		return currentColor;
	}
	
	
}

class DessinEcouteur implements MouseListener, MouseMotionListener
{
  private Dessin panel;
  private Client client;
	
	DessinEcouteur(Dessin panel, Client client)
	{
		this.panel = panel;
		this.client = client;
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(panel.getEnabled())
		{
			panel.addPoint(new Point(e.getPoint()));
			panel.repaint();
			//client.sendPoint(new Point(e.getPoint()));
		}
	}

	public void mousePressed(MouseEvent e)
	{
		if(panel.getEnabled())
		{
			panel.points.clear();
			panel.addPoint(new Point(e.getPoint()));
		    //client.sendPoint(new Rectangle(new Point(e.getPoint()));
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(panel.getEnabled())
		{
			panel.addPoint(new Point(e.getPoint()));
			panel.points.clear();
		    //client.sendPoint(new Point(e.getPoint()));
			panel.repaint();
		}
	}
	
	
	public void mouseMoved(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}  
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
}
