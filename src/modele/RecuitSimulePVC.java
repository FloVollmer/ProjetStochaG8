package modele;
import java.util.Random;

<<<<<<< HEAD
public class RecuitSimulePVC extends RecuitSimule {
	
	public RecuitSimulePVC(ProgLineaire pb) {
		this.pb = pb;
		rand = new Random();
	}
		
=======
import vue.FenetreRendu;

public class RecuitSimulePVC extends RecuitSimule {
	
	public RecuitSimulePVC(ProgLineaire pb) {
		this.setPb(pb);
		this.setRand(new Random());
	}
	
	
	public static void main(String[] args) {
		
		// Exemple de réseau de villes : les villes a, b et d sont particulierement proches
		// Ici, les distances sont les mêmes dans les deux sens
		// 0 signifie que le chemin n'existe pas
		int villes[][] = new int[][] {
			{0, 2, 6, 1},	// distances de la ville a aux autres villes
			{2, 0, 7, 1}, 	// distances de la ville b aux autres villes
			{6, 7, 0, 5},	// distances de la ville c aux autres villes
			{1, 1, 5, 0}};	// distances de la ville d aux autres villes
			
		// Autre exemple : a et d sont dans une region plus coûteuse à rejoindre qu'a quitter
		int villes2[][] = new int[][] {
			{0, 2, 4, 2},
			{3, 0, 1, 6},
			{5, 1, 0, 5},
			{2, 4, 3, 0}};
		
		// 3eme exemple : villes regroupées en 3 zones qui sont a b c, d e, et f.
		int villes3[][] = new int [][] {
			{0, 1, 3, 4, 4, 7},
			{1, 0, 4, 3, 4, 6},
			{3, 4, 0, 5, 4, 7},
			{5, 4, 6, 0, 1, 3},
			{6, 5, 5, 1, 0, 4},
			{9, 8, 10, 4, 5, 0},
		};
		
		VoyageurCommerce pb = new VoyageurCommerce(100);
		pb.afficherDonnees();
		RecuitSimulePVC recuiseur = new RecuitSimulePVC(pb);
		recuiseur.optimiser();
		
		
		return;
	}
	
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
}
