package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import modele.Chemin;

public class PanneauMap extends JPanel implements ComponentListener, MouseListener, MouseMotionListener {
	private BufferedImage imgMap_o;
	private  ArrayList<Point> posVilles;
	private Chemin chemin;
	private int tailleTuile;
	
	public PanneauMap (FenetreRendu fenetreRendu, BufferedImage imgMap_o, ArrayList<Point> posVilles, Chemin chemin) {
		
		this.imgMap_o = imgMap_o;
		this.posVilles = posVilles;
		this.chemin = chemin;

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(new Color(32, 32, 32));
		
		
<<<<<<< HEAD
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
=======
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
	}
	
	@Override
	public void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		//g.drawImage(imgMap_o, 0, 0, null);
		
		tailleTuile = 
				(getHeight()/imgMap_o.getHeight() > getWidth()/imgMap_o.getWidth())
				? getWidth()/imgMap_o.getWidth() : getHeight()/imgMap_o.getHeight();
		
		for (int j=0; j< imgMap_o.getHeight(); ++j) {
			for (int i=0; i< imgMap_o.getHeight(); ++i) {
				g.setColor(new Color (imgMap_o.getRGB(i, j)));
				g.fillRect(i*tailleTuile, j*tailleTuile, tailleTuile, tailleTuile);
			}
		}
		
		afficherChemin(chemin, g);
		
	}
	
	public void afficherChemin(Chemin chemin, Graphics2D g) {
		g.setColor(Color.GREEN);
		for (int i=0; i<chemin.length()-1; ++i) {
			g.drawLine(
					posVilles.get(chemin.get(i)).x*tailleTuile + tailleTuile/2,
					posVilles.get(chemin.get(i)).y*tailleTuile + tailleTuile/2,
					posVilles.get(chemin.get(i+1)).x*tailleTuile + tailleTuile/2,
					posVilles.get(chemin.get(i+1)).y*tailleTuile + tailleTuile/2);
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
