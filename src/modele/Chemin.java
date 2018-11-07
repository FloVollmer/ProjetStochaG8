package modele;

import java.util.Random;

public class Chemin {
	
	private static Random rand = new Random();
	private double[][] couts;
	private int[] sommets;
	
	
	public Chemin (double[][] couts, int[] sommets) {
		this.couts = couts;
		this.sommets = sommets;
	}
	
	public Chemin(int nbVilles) {
		sommets = new int[nbVilles];
		for(int i = 0; i<sommets.length; i++)
			sommets[i] = i;
	}
	
	public Chemin (Chemin chemin) {
		this.couts = chemin.couts;
		this.sommets = new int[chemin.length()];
		for (int i=0; i<sommets.length; ++i)
			this.sommets[i] = chemin.sommets[i];
	}

	public Chemin (double[][] couts) {
		this.couts = couts;
		sommets = new int [couts.length];
		for (int i=0; i<sommets.length; ++i)
			sommets[i] = i;
	}
	
	public Chemin(Element[][] listeDonnees, boolean reinitX) {
		//System.out.println("taille donnees = " + listeDonnees.length);
		
		couts = new double[listeDonnees.length][listeDonnees.length];
		for (int j=0; j<listeDonnees.length; ++j) {
			for (int i=0; i<listeDonnees.length; ++i) {
				System.out.print("  " + (reinitX ? (i==(j+1)%listeDonnees.length ? 1 : 0) : listeDonnees[j][i].getX()));
				couts[j][i] = listeDonnees[j][i].getCout();
			}
			System.out.println();
		}
		
			
		sommets = new int[listeDonnees.length];
		int j = 0;
		int i;
		int k = 0;
		do {
			i = 0;
			while ((reinitX ? (i==(j+1)%listeDonnees.length ? 1 : 0) : listeDonnees[j][i].getX()) != 1) {
				++i;
			}
			sommets[k] = i;
			j = i;
			++k;
		} while (i != 0);
		
		for (int i2=0; i2<listeDonnees.length; ++i2)
			System.out.print(" " + sommets[i2]);
		System.out.println();
			
	}

	public int[] getSommets() {
		return sommets;
	}

	public void setSommets(int[] sommets) {
		this.sommets = sommets;
	}
	
	public static Chemin cloner(Chemin chemin) {
		return new Chemin(chemin);
	}
	
	public void changerChemin(int nbInversions) {
		inv2opt();
	}
	
	public void inv2opt() {
		
		int ville1 = rand.nextInt(sommets.length);
		int ville2 = rand.nextInt(sommets.length-1);
		if (ville2 >= ville1)
			++ville2;
		
		int iMax = (ville2-ville1)/2;
		if (iMax < 0)
			iMax += sommets.length;
		
		for (int i=0; i<iMax; ++i) {
			int i1 = ville1 + i;
			if (i1 >= sommets.length)
				i1 -= sommets.length;
			
			int i2 = ville2 - i;
			if (i2 < 0)
				i2 += sommets.length;
			
			int temp = sommets[i1];
			sommets[i1] = sommets[i2];
			sommets[i2] = temp;
		}
	}
	
	public void inverser2villes() {
		// On inverse deux villes choisies aleatoirement
		// On ne choisit jamais la premiere (et derniere) ville
		// car l'endroit ou commence la boucle n'a pas d'importance
		int ville1 = rand.nextInt(sommets.length);
		int ville2 = rand.nextInt(sommets.length-1);
		if (ville2 >= ville1)
			++ville2;
		int temp = sommets[ville1];
		sommets[ville1] = sommets[ville2];
		sommets[ville2] = temp;
	}
	
	public int coutTotal () {
		int cout = 0;
		//afficherChemin(chemin);
		for (int k=0; k<sommets.length-1; ++k) {
			cout += getCoutArc(sommets[k], sommets[k+1]);
		}
		cout += getCoutArc(sommets[sommets.length-1], sommets[0]);
		//System.out.println("Coût total = " + cout);
		return cout;
	}
	
	
	public double getCoutArc(int ville1, int ville2) {
		//System.out.println("Long. arc = " + villes[ville1][ville2]);
		return couts[ville1][ville2];
	}
	
	public int length() {
		return sommets.length;
	}
	
	public int get(int i) {
		return sommets[i];
	}
	
}
