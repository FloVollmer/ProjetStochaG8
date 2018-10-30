package modele;

import java.util.Random;

public class Chemin {
	
	private static Random rand = new Random();
	private double[][] villes;
	private int[] sommets;
	
	public Chemin (double[][] villes, int[] sommets) {
		this.villes = villes;
		this.sommets = sommets;
	}
	
	public Chemin (Chemin chemin) {
		this.villes = chemin.villes;
		this.sommets = new int[chemin.length()];
		for (int i=0; i<sommets.length; ++i)
			this.sommets[i] = chemin.sommets[i];
	}

	public Chemin (double[][] villes) {
		this.villes = villes;
		sommets = new int [villes.length];
		for (int i=0; i<sommets.length; ++i)
			sommets[i] = i;
	}
	
	public static Chemin cloner(Chemin chemin) {
		return new Chemin(chemin);
	}
	
	public void changerChemin(int nbInversions) {
		inv2opt();
		//for (int i=0; i<nbInversions; ++i)
		//	inverser2villes();
	}
	
	public void inv2opt() {
		
		int ville1 = rand.nextInt(sommets.length);
		int ville2 = rand.nextInt(sommets.length-1);
		if (ville2 >= ville1)
			++ville2;
		
		int iMax = ville2-ville1/2;
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
		return villes[ville1][ville2];
	}
	
	public int length() {
		return sommets.length;
	}
	
	public int get(int i) {
		return sommets[i];
	}
	
}
