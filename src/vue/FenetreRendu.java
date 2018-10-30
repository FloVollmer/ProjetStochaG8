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

<<<<<<< HEAD

=======
import javax.imageio.ImageIO;
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
import javax.swing.JFrame;

import modele.Arc;
import modele.Chemin;
import modele.VoyageurCommerce;
import modele.RecuitSimulePVC;
<<<<<<< HEAD


=======
import modele.Ville;
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad

@SuppressWarnings("serial")
public class FenetreRendu extends JFrame implements KeyListener, MouseListener {
	
	private PanneauMap panneauMap;
	private RecuitSimulePVC recuiseur;
	
	public FenetreRendu() {
		
		setPreferredSize(new Dimension(1280, 720));
		setTitle("Voyageur de commerce");
	    //setLocationRelativeTo(null);
	    addKeyListener(this);
		addMouseListener(this);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
<<<<<<< HEAD
	   
	    Parseur parseur = new Parseur("Ressources\\testMap.png");
		
	    setContentPane(panneauMap = new PanneauMap(this, parseur.getPosVilles(), new Chemin(parseur.getCouts())));
	    pack();
	    setVisible(true);
		
		
		VoyageurCommerce pb = new VoyageurCommerce(parseur.getCouts()[0].length);
		
		for(int i = 0; i< parseur.getCouts().length ; i++) {
			for(int j = 0; j< parseur.getCouts()[0].length ; j++) {
				Arc arc = new Arc(parseur.getVilles()[i], parseur.getVilles()[j]);
				arc.setCout(parseur.getCouts()[i][j]);
=======
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
		
		Ville[] villes = new Ville[posVilles.size()];
		for(int i = 0; i<villes.length ; i++) {
			villes[i] = new Ville(i,posVilles.get(i));
		}
		
		int[][] couts = new int[posVilles.size()][posVilles.size()];

		for (int j=0; j<couts.length; ++j)
			for (int i=0; i<couts.length; ++i) {
				if (i==j)
					couts[j][i] = 0;
				else {
					couts[j][i] = (int) Math.sqrt(
							(posVilles.get(i).x - posVilles.get(j).x) * (posVilles.get(i).x - posVilles.get(j).x) + 
							(posVilles.get(i).y - posVilles.get(j).y) * (posVilles.get(i).y - posVilles.get(j).y));
				}
			}
		
		VoyageurCommerce pb = new VoyageurCommerce(couts[0].length);
		for(int i = 0; i< couts.length ; i++) {
			for(int j = 0; j< couts[0].length ; j++) {
				Arc arc = new Arc(villes[i], villes[j]);
				arc.setCout(couts[i][j]);
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
				pb.getData().getListeDonnees()[i][j] = arc;
			}
		}
		
<<<<<<< HEAD
		Chemin chemin = new Chemin(parseur.getCouts());
		pb.setXInitiaux(parseur.getVilles().length);
		setContentPane(panneauMap = new PanneauMap(this, parseur.getPosVilles(), chemin));
		pack();
		setVisible(true);
=======
		Chemin chemin = new Chemin(couts);
		pb.setXInitiaux(villes.length);
		
		setContentPane(panneauMap = new PanneauMap(this, imgMap_o, posVilles, chemin));
		pack();
		setVisible(true);
		
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
		pb.setChemin(chemin);
	    pb.setPanneauMap(panneauMap);
	    pb.genererContraintes();
	    recuiseur = new RecuitSimulePVC(pb);
	    recuiseur.setFenetre(this);
//		recuiseur.afficherDonnees();
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