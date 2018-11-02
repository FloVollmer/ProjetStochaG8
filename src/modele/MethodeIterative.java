package modele;

import java.util.ArrayList;

public class MethodeIterative {

	private int[][] x;
	private ArrayList<ArrayList<Integer>> chemin;
	private ArrayList<Boolean> villes = new ArrayList<Boolean>();
	
	public MethodeIterative() {
		
	}
	
	public int[][] getX() {
		return x;
	}

	public void setX(int[][] x) {
		this.x = x;
	}
	
	public ArrayList<ArrayList<Integer>> getChemin() {
		return chemin;
	}

	public void setChemin(ArrayList<ArrayList<Integer>> chemin) {
		this.chemin = chemin;
	}

	public ArrayList<Boolean> getVilles() {
		return villes;
	}

	public void setVilles(ArrayList<Boolean> villes) {
		this.villes = villes;
	}

	public void remplirVilles() {
		for(int i = 0; i< x[0].length ; i++)
			villes.add(false);
	}
	
	public void reinitialiserVilles() {
		for(int i = 0; i< villes.size() ; i++)
			villes.set(i, false);
	}
	
	//Retourne le plus petit numéro des villes non visitées si existe
	//Retorune -1 sinon
	public int getFirstIndice() {
		int res = -1;
		for(int i = 0; i<villes.size() ; i++) {
			if(!villes.get(i))
				res = i;
		}
		return res;
	}
	
	public void passageAuChemin() {
		reinitialiserVilles();
		chemin = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> l = new ArrayList<Integer>();
		chemin.add(l);
		chemin.get(0).add(0);
		villes.set(0, true);
		fromXToChemin(0,0);
	}
	
	//i = ville étudiée actuellement
	//u = indice de la liste dans la liste des sous-tours (chemin)
	public void fromXToChemin(int i, int u) {
		for(int j = 0; j<x[0].length ; j++) {
			if(x[i][j] == 1 && !villes.get(j)) {
				villes.set(j, true);
				chemin.get(u).add(j);
				fromXToChemin(j,u);
			}
			else if(x[i][j] == 1 && villes.get(j)) {
				int a = getFirstIndice();
				if(a!=-1) {
					villes.set(a, true);
					ArrayList<Integer> l = new ArrayList<Integer>();
					chemin.add(l);
					chemin.get(u+1).add(a);
					fromXToChemin(a,u+1);
				}
			}
		}
	}
	
	
	public void afficherChemin() {	
		System.out.println("Affichage chemin :");
		for(int i = 0; i<chemin.size() ; i++) {
			int nombre = i+1;
			System.out.println("Sous tours n° " + nombre);
			for(int j=0; j<chemin.get(i).size(); j++) {
				System.out.println(chemin.get(i).get(j));
			}
		}
	}
}
