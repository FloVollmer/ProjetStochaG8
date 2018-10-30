package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.JPanel;

import modele.Chemin;

public class PanneauMap extends JPanel implements ComponentListener, MouseListener, MouseMotionListener {
	private  ArrayList<Point2D.Double> posVilles;
	private  ArrayList<Point> posRepr;
	private Chemin chemin;
	private Rectangle.Double bordsCamera = new Rectangle.Double();
	
	public PanneauMap (FenetreRendu fenetreRendu, ArrayList<Point2D.Double> posVilles, Chemin chemin) {
		
		this.posVilles = posVilles;
		this.chemin = chemin;

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(new Color(32, 32, 32));
		
		Point2D.Double posMin = (Double) posVilles.get(0).clone();
		Point2D.Double posMax = (Double) posVilles.get(0).clone();
		
		
		// On les valeurs x et y minimales et maximales
		for (Point2D.Double pos : posVilles) {
			//System.out.println("pos.x = " + pos.x);
			//System.out.println("posMax.x = " + posMax.x);
			//System.out.println("posMin.x = " + posMin.x);
			if (pos.x < posMin.x)
				posMin.x = pos.x;
			else if (pos.x > posMax.x)
				posMax.x = pos.x;
			
			if (pos.y < posMin.y)
				posMin.y = pos.y;
			else if (pos.y > posMax.y)
				posMax.y = pos.y;
			//System.out.println();
		}
		
		
		
		// On ajuste les bords de la camera en fonction des points trouves
		Point2D.Double centreCam = new Point2D.Double((posMax.x+posMin.x)*0.5, (posMax.y+posMin.y)*0.5);
		double coteCamera = 1.2*((posMax.x-posMin.x > posMax.y-posMin.y) ? posMax.x-posMin.x : posMax.y-posMin.y);
		/*bordsCamera = new Rectangle.Double (
				posMin.x - (posMax.x-posMin.x)*0.1,
				posMin.y - (posMax.y-posMin.y)*0.1,
				posMax.x-posMin.x + (posMax.x-posMin.x)*0.2,
				posMax.y-posMin.y + (posMax.y-posMin.y)*0.2);*/
		
		bordsCamera = new Rectangle.Double (
				centreCam.x - 0.5*coteCamera,
				centreCam.y - 0.5*coteCamera,
				coteCamera,
				coteCamera);
		
		majPosRepr();
		
	}
	
	// On calcule les coordonnees auxquelles vont etre representees les villes
	public void majPosRepr() {
		
		double cote = (getWidth() > getHeight()) ? getHeight() : getWidth();
		double coeffCote = cote/bordsCamera.width;
		System.out.println("bordsCamera = " + bordsCamera);
		System.out.println("largeurCam = " + bordsCamera.width);
		System.out.println("cote = " + cote);
		System.out.println("coeffCote = " + coeffCote);
		
		posRepr = new ArrayList<Point>();
		for (Point2D.Double pos : posVilles) {
			posRepr.add(new Point((int)((pos.x-bordsCamera.x)*coeffCote), (int)((pos.y-bordsCamera.y)*coeffCote)));
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int cote = (getWidth() > getHeight()) ? getHeight() : getWidth();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, cote, cote);
		
		cote *= 0.005;
		g.setColor(Color.RED); // On affiche les points en rouge
		for (Point pos : posRepr) {
			g.fillRect(pos.x-cote, pos.y-cote, 1+cote*2, 1+cote*2);
		}
		g.setColor(Color.CYAN); // sauf le 1er/dernier qui est en cyan
		Point pos = posRepr.get(chemin.get(0));
		g.fillRect(pos.x-cote, pos.y-cote, 1+cote*2, 1+cote*2);
		
		
		afficherChemin(chemin, g);
		
	}
	 
	public void afficherChemin(Chemin chemin, Graphics2D g) {
		g.setColor(Color.GREEN); // On affiche les arcs empruntes en vert
		for (int i=0; i<chemin.length()-1; ++i) {
			g.drawLine(
					posRepr.get(chemin.get(i)).x,
					posRepr.get(chemin.get(i)).y,
					posRepr.get(chemin.get(i+1)).x,
					posRepr.get(chemin.get(i+1)).y);
		}
		g.setColor(Color.CYAN); // sauf le dernier qui est en cyan
		g.drawLine(
				posRepr.get(chemin.get(chemin.length()-1)).x,
				posRepr.get(chemin.get(chemin.length()-1)).y,
				posRepr.get(chemin.get(0)).x,
				posRepr.get(chemin.get(0)).y);
		
	}
	
	public void setChemin(Chemin chemin) {
		this.chemin = chemin;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		majPosRepr();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
