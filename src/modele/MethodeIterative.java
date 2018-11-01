package modele;

import java.util.ArrayList;

public class MethodeIterative {

	private int[][] x;
	private ArrayList<Integer> chemin;
	private static int compteur = 0;
	
	public MethodeIterative() {
		
	}
	
	public int[][] getX() {
		return x;
	}

	public void setX(int[][] x) {
		this.x = x;
	}
	
	public ArrayList<Integer> getChemin() {
		return chemin;
	}

	public void setChemin(ArrayList<Integer> chemin) {
		this.chemin = chemin;
	}


	public void passageAuChemin() {
		chemin = new ArrayList<Integer>();
		chemin.add(0);
		fromXToChemin(0);
		compteur = 0;
	}
	
	public void fromXToChemin(int i) {
		if(compteur < x.length) {
			compteur+=1;
			for(int j = 0; j<x[0].length ; j++) {
				if(x[i][j] == 1 && !(chemin.contains(j))) {
					chemin.add(j);
					fromXToChemin(j);
				}
			}
		}
	}
	
	
	public void afficherChemin() {	
		System.out.println("Affichage chemin :");
		for(int i = 0; i<chemin.size() ; i++) {
			System.out.println(chemin.get(i)+ " ");
		}
	}
}
