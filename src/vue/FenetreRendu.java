package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import modele.Chemin;
import modele.PbVoyageurCommerce;
import modele.RecuitSimule;

@SuppressWarnings("serial")
public class FenetreRendu extends JFrame implements KeyListener, MouseListener {
	
	private PanneauMap panneauMap;
	private RecuitSimule recuiseur;
	
	public FenetreRendu() {
		
		setPreferredSize(new Dimension(1280, 720));
		setTitle("Voyageur de commerce");
	    //setLocationRelativeTo(null);
	    addKeyListener(this);
		addMouseListener(this);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    BufferedImage imgMap_o = null;
		try {
			imgMap_o = ImageIO.read(new File("Ressources\\testMap.png"));
		} catch (IOException e) {
		}
		
		ArrayList<Point> posVilles = new ArrayList<Point>();
		
		for (int j=0; j< imgMap_o.getHeight(); ++j) {
			for (int i=0; i< imgMap_o.getHeight(); ++i) {
				if (imgMap_o.getRGB(i, j) == Color.RED.hashCode()) {
					//System.out.println("Ville en " + i + " ; " + j);
					posVilles.add(new Point(i, j));
				}
			}
		}
		
		int[][] villes = new int[posVilles.size()][posVilles.size()];

		for (int j=0; j<villes.length; ++j)
			for (int i=0; i<villes.length; ++i) {
				if (i==j)
					villes[j][i] = 0;
				else {
					villes[j][i] = (int) Math.sqrt(
							(posVilles.get(i).x - posVilles.get(j).x) * (posVilles.get(i).x - posVilles.get(j).x) + 
							(posVilles.get(i).y - posVilles.get(j).y) * (posVilles.get(i).y - posVilles.get(j).y));
				}
			}
				
	    
	    setContentPane(panneauMap = new PanneauMap(this, imgMap_o, posVilles, new Chemin(villes)));
	    pack();
	    setVisible(true);
	    PbVoyageurCommerce pb = new PbVoyageurCommerce(villes);
	    pb.setPanneauMap(panneauMap);
	    recuiseur = new RecuitSimule(pb);
	    recuiseur.setFenetre(this);
		recuiseur.afficherDonnees();
		recuiseur.optimiser();
	}
	
	public void actualiserChemin(Chemin chemin) {
		panneauMap.setChemin(chemin);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}

	@Override
	public void mouseClicked(MouseEvent event) {}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}
	
	public static void main(String[] args) {
		
		FenetreRendu fenetre = new FenetreRendu();
		
		
		
		while (true) {
			fenetre.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}