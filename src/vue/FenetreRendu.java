package vue;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JFrame;

import modele.Arc;
import modele.Chemin;
import modele.Cplex;
import modele.VoyageurCommerce;
import modele.RecuitSimulePVC;



@SuppressWarnings("serial")
public class FenetreRendu extends JFrame implements KeyListener, MouseListener {
	
	private PanneauMap panneauMap;
	private PanneauEvolution panneauEvolution;
	private RecuitSimulePVC recuiseur;
	private Cplex cplex;
	
	public FenetreRendu(int recuitOuCplex) {
		
		setPreferredSize(new Dimension(1280, 720));
		setTitle("Voyageur de commerce");
	    //setLocationRelativeTo(null);
	    addKeyListener(this);
		addMouseListener(this);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	   
	    Parseur parseur = new Parseur("Ressources\\a280.xml");
		
		VoyageurCommerce pb = new VoyageurCommerce(parseur.getCouts()[0].length);
		
		for(int i = 0; i< parseur.getCouts().length ; i++) {
			for(int j = 0; j< parseur.getCouts()[0].length ; j++) {
				Arc arc = new Arc(parseur.getVilles()[i], parseur.getVilles()[j]);
				arc.setCout(parseur.getCouts()[i][j]);
				pb.getData().getListeDonnees()[i][j] = arc;
			}
		}
		
		
		
		Chemin chemin = new Chemin(parseur.getCouts());
		//pb.setXInitiaux(parseur.getVilles().length);
		//setContentPane(panneauMap = new PanneauMap(this, parseur.getPosVilles(), chemin));
		setContentPane(panneauEvolution = new PanneauEvolution(this));
		pack();
		setVisible(true);
		pb.setChemin(chemin);
	    pb.setPanneauMap(panneauMap);
	    pb.genererContraintes();
	    
		
		//Si Recuit
		if(recuitOuCplex == 0) {
		    recuiseur = new RecuitSimulePVC(pb);
		    recuiseur.setFenetre(this);
		    recuiseur.setPanneauEvol(panneauEvolution);
			//recuiseur.afficherDonnees();
			recuiseur.optimiser();
		}
		
		//Si Cplex
		else {
		    cplex = new Cplex(parseur,pb,false);
		    cplex.setFenetre(this);
		    cplex.run();
		}
	    
	  
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
		
		FenetreRendu fenetre = new FenetreRendu(0);
		
		
		
		while (true) {
			//fenetre.repaint();
			//System.out.println("Affichage");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}