package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import configuration.Configuration;


/*
 * C'est pas plus simple d'envoyer le Jpanel directement au lieu de faire: 
 * 
 * 	panel.addPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
 *	client.sendPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
 */

public class Dessin extends JPanel implements Configuration
{
	private Vector<Rectangle> point = new Vector<Rectangle>();
	
	private DessinEcouteur ecouteur;
	
	private boolean enabled = true;
	private Color color = Color.black;
	
	
	public Dessin(Client client)
	{
		ecouteur = new DessinEcouteur(this,client);
		this.addMouseListener(ecouteur);
		this.addMouseMotionListener(ecouteur);
	}
	
	public void addPoint(Rectangle p)
	{
		point.addElement(p);
		repaint();
	}
	
	public void effacerDessin()
	{
		point.clear();
		
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
    
    	g2d.setColor(Color.white);
		g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(RAYON,BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		Rectangle p;
		
		for(int i = 1; i<point.size(); i++)
		{
      		p = (Rectangle)point.elementAt(i);
			g2d.drawLine(p.x,p.y,p.width,p.height);
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
	

	public void setColor (Color c)
	{
		this.color = c;
	}
}

class DessinEcouteur implements MouseListener, MouseMotionListener
{
  private Dessin panel;
  private int x1=0,y1=0;
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
			panel.addPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
			client.sendPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
	    	x1=e.getX();
	    	y1=e.getY();
		}
	}

	public void mousePressed(MouseEvent e)
	{
		if(panel.getEnabled())
		{
		    if(x1==-1)
		    {
		      x1=e.getX();
		      y1=e.getY();
		    }
		    panel.addPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
		    client.sendPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
		    x1=e.getX();
		    y1=e.getY();
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(panel.getEnabled())
		{
		    panel.addPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
		    client.sendPoint(new Rectangle(x1, y1, e.getX(), e.getY()));
		    x1=-1;
		    y1=-1;
		}
	}
	
	
	public void mouseMoved(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}  
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
}
