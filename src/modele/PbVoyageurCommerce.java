package modele;

import java.util.Random;

import vue.PanneauMap;

public class PbVoyageurCommerce extends ProgLineaire {
	private double[][] arcs;
	Chemin chemin;
	Chemin candidat;
	Chemin meilleur;
	
	PanneauMap panneauMap;
	
	public PbVoyageurCommerce(double[][] arcs) {
		this.arcs = arcs;
	}
	
	public PbVoyageurCommerce(int nbVilles) {
		arcs = new double[nbVilles][nbVilles];
		Random rand = new Random();
		for (int j=0; j<nbVilles; ++j)
			for (int i=0; i<nbVilles; ++i)
				arcs[j][i] = (i!=j ? rand.nextInt(19)+1 : 0);
	}
	
	@Override
	public void mouvement() {
		candidat = Chemin.cloner(chemin);
		candidat.inv2opt();
	}
	
	@Override
	public boolean validerMouvement() {
		chemin = candidat;
		
		return false;
	}
	
	@Override
	public float trouverSolutionInitiale() {
		// Construction du chemin initial, qui est juste les villes dans l'ordre du tableau
		chemin = new Chemin(arcs);
		meilleur = Chemin.cloner(chemin);
		return chemin.coutTotal();
	}

	@Override
	public void revenirAuMeilleur() {
		chemin = Chemin.cloner(meilleur);
	}
	
	@Override
	public void nouveauMeilleur() {
		meilleur = Chemin.cloner(chemin);
		if (panneauMap != null) {
			panneauMap.setChemin(chemin);
		}
	}
	
	@Override
	public float fonctionObjectif() {
		return chemin.coutTotal();
	}
	
	@Override
	public float fctObjCandidat() {
		return candidat.coutTotal();
	}

	@Override
	public int getTailleDonnees() {
		return arcs.length*arcs.length;
	}
	
	@Override
	public void afficherDonnees() {

		for (int j=0; j<arcs.length; ++j) {
			for (int i=0; i<arcs[j].length; ++i)
				System.out.print((arcs[j][i]>9 ? " " : "  ") + arcs[j][i] + ".");
			System.out.println();
		}
		
	}
	
	public void setPanneauMap(PanneauMap panneauMap) {
		this.panneauMap = panneauMap;
	}
	

}
