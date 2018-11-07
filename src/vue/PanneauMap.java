package vue;

import java.awt.Color;
import java.awt.Font;
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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.JPanel;

import modele.Chemin;

public class PanneauMap extends JPanel implements ComponentListener, MouseListener, MouseMotionListener,MouseWheelListener {
	private  ArrayList<Point2D.Double> posVilles;
	private  ArrayList<Point> posRepr = new ArrayList<Point>();
	private Chemin chemin;
	private Rectangle.Double bordsCamera = new Rectangle.Double();
	private double coutTotal;
	

	public PanneauMap (FenetreRendu fenetreRendu, ArrayList<Point2D.Double> posVilles, Chemin chemin) {
		
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setPosVilles(posVilles, chemin);
		
	}
	
	public void setPosVilles(ArrayList<Point2D.Double> posVilles, Chemin chemin) {

		this.posVilles = posVilles;
		this.chemin = null;
		coutTotal = 0;
		
		Point2D.Double posMin = (Double) posVilles.get(0).clone();
		Point2D.Double posMax = (Double) posVilles.get(0).clone();
		
		// On les valeurs x et y minimales et maximales
		for (Point2D.Double pos : posVilles) {
			if (pos.x < posMin.x)
				posMin.x = pos.x;
			else if (pos.x > posMax.x)
				posMax.x = pos.x;
			
			if (pos.y < posMin.y)
				posMin.y = pos.y;
			else if (pos.y > posMax.y)
				posMax.y = pos.y;
		}
		
		// On ajuste les bords de la camera en fonction des points trouves
		Point2D.Double centreCam = new Point2D.Double((posMax.x+posMin.x)*0.5, (posMax.y+posMin.y)*0.5);
		double coteCamera = 1.2*((posMax.x-posMin.x > posMax.y-posMin.y) ? posMax.x-posMin.x : posMax.y-posMin.y);
		
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
		
		ArrayList<Point> posTemp = new ArrayList<Point>(); // on procede ainsi pour limiter les collisions entre threads
		for (Point2D.Double pos : posVilles) {
			posTemp.add(new Point((int)((pos.x-bordsCamera.x)*coeffCote), (int)((pos.y-bordsCamera.y)*coeffCote)));
		}
		
		synchronized(posRepr) {
			posRepr = posTemp;
		}
		
	}
	
	@Override
	public void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int cote = (getWidth() > getHeight()) ? getHeight() : getWidth();
		g.setColor(new Color(63, 63, 63));
		g.fillRect(0, 0, cote, cote);
		
		int coteVille = (int)(cote*0.005);
		g.setColor(Color.RED); // On affiche les points en rouge
		for (Point pos : posRepr) {
			g.fillRect(pos.x-coteVille, pos.y-coteVille, 1+coteVille*2, 1+coteVille*2);
		}


		Font font = new Font("Arial", Font.PLAIN, cote/32);
		g.setColor(Color.GREEN);
		g.setFont(font);
		String tempStr = java.lang.Double.toString(bordsCamera.width*0.25);
		if (tempStr.length()>6)
			tempStr = tempStr.substring(0, 6);
		g.drawString(tempStr,(int) (cote*0.1), (int)(cote*0.97));
		g.drawRect((int)(cote*0.02), (int)(cote*0.98), (int)(cote*0.25), 1);
		g.drawRect((int)(cote*0.02), (int)(cote*0.97), 1, (int)(cote*0.02));
		g.drawRect((int)(cote*0.27), (int)(cote*0.97), 1, (int)(cote*0.02));
		g.setColor(Color.RED);
		g.drawString("Nb villes : " + posRepr.size(),(int) (cote*0.05), (int)(cote*0.1));
		
		if (chemin != null) {
			synchronized(posRepr) {
				synchronized(chemin) {
					g.setColor(Color.CYAN); // sauf le 1er/dernier qui est en cyan
					Point pos = posRepr.get(chemin.get(0));
					g.fillRect(pos.x-coteVille, pos.y-coteVille, 1+coteVille*2, 1+coteVille*2);
					afficherChemin(chemin, g);
					g.setColor(Color.GREEN);
					tempStr = java.lang.Double.toString(coutTotal);
					if (tempStr.length()>8)
						tempStr = tempStr.substring(0, 8);
					g.drawString("Cout total : " + tempStr,(int) (cote*0.05), (int)(cote*0.05));
					
				}
			}
		}
		
		
		g.setColor(new Color(238, 238, 238));
		if (getWidth() > getHeight())
			g.fillRect(cote, 0, getWidth()-cote, cote);
		else
			g.fillRect(0, cote, cote, getHeight()-cote);
		
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
	
	public synchronized void setChemin(Chemin chemin) {
		this.chemin = chemin;
		coutTotal = chemin.coutTotal();
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

	public void setPosVille(ArrayList<Point2D.Double> posVilles){
		this.posVilles = posVilles; 
	}
	
	public void setposRepr(ArrayList<Point> posRepr){
		
		this.posRepr = posRepr; 
	}
	
	public ArrayList<Point2D.Double> getPosVille(){
		
		return this.posVilles; 
	}
	
	public ArrayList<Point> getPosRepr(){
		return this.posRepr; 
	}
	
	public Chemin getChemin(){
		return this.chemin; 
	}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		
		

		int cote = (getWidth() > getHeight()) ? getHeight() : getWidth();
		double coeffCote = bordsCamera.width/cote;
		
		Point2D.Double centreCam;
		double coteCamera;
		if (e.getWheelRotation()<0) {
			coteCamera = bordsCamera.width*0.8;
			centreCam = new Point2D.Double(
					(bordsCamera.x+e.getX()*coeffCote + bordsCamera.getCenterX())*0.5,
					(bordsCamera.y+e.getY()*coeffCote + bordsCamera.getCenterY())*0.5);
		} else {
			coteCamera = bordsCamera.width*1.25;
			centreCam = new Point2D.Double(
					(bordsCamera.x+bordsCamera.width-e.getX()*coeffCote + 7*bordsCamera.getCenterX())*0.125,
					(bordsCamera.y+bordsCamera.width-e.getY()*coeffCote + 7*bordsCamera.getCenterY())*0.125);
		}
		/*System.out.println("e.getPoint = " + e.getPoint());
		System.out.println("centreCam = " + centreCam);*/
		
		
		bordsCamera = new Rectangle.Double (
				centreCam.x - 0.5*coteCamera,
				centreCam.y - 0.5*coteCamera,
				coteCamera,
				coteCamera);
		
		majPosRepr();
	}
}
