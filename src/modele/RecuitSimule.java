package modele;
import java.util.Random;

import vue.FenetreRendu;

public class RecuitSimule {
	
	private Random rand;
	private double invTemperature;
	private double meilleurCout;
	private int palliersDepuisMeilleur;
	private FenetreRendu fenetre = null;
	private double coeffDecroissanceT = 0.9;
	private double seuilAcceptation = 0.2;
	private ProgLineaire pb;
	
	public RecuitSimule(ProgLineaire pb) {
		this.pb = pb;
		rand = new Random();
	}
	
	
	public void setFenetre(FenetreRendu fenetre) {
		this.fenetre = fenetre;
	}
	
	public void optimiser() {


		//double tauxPrec = 1;
		double tauxAct = 1;
		int iPallier = 0;
		meilleurCout = pb.trouverSolutionInitiale();
		double temperature =  1.25f * meilleurCout;
		palliersDepuisMeilleur = 0;
		
		while (palliersDepuisMeilleur < 100) {
			System.out.println();
			//System.out.println();
			System.out.println("   ***   PALLIER " + (++iPallier) + "   ***");
			System.out.println("temperature = " + temperature);
			//System.out.println();
			//tauxPrec = tauxAct;
			tauxAct = fairePallier(temperature);
			System.out.println("Taux d'acceptation : " + tauxAct);
			temperature *= coeffDecroissanceT;
			if (tauxAct < seuilAcceptation) {
				++palliersDepuisMeilleur;
			}
		}
		
		System.out.println("Meilleur coût final : " + meilleurCout);	
		
	}
	
	public double fairePallier(double temperature) {
		
		double cout = pb.fonctionObjectif();
		invTemperature = 1 / temperature; // on evite de faire trop de divisions car c'est une operation couteuse
		double tauxAcceptation = 0;
		
		for(int i=0; i<pb.getTailleDonnees(); ++i) {
			pb.mouvement();
			float temp = rand.nextFloat();
			//System.out.println("Diff coûts = " + (pb.fctObjCandidat()-cout));
			if (pb.fctObjCandidat() < cout) {
				//System.out.println("Chemin accepté");
				pb.validerMouvement();
				cout = pb.fonctionObjectif();
				++tauxAcceptation;
				if (cout < meilleurCout) {
					System.out.println("Nouveau meilleur cout : " + cout);
					pb.nouveauMeilleur();
					meilleurCout = cout;
					palliersDepuisMeilleur = 0;
				}
			} else if (Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) > temp) {
				//System.out.println(Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) + " > " + temp);
				//System.out.println("Chemin accepté");
				pb.validerMouvement();
				cout = pb.fonctionObjectif();
				++tauxAcceptation;
			} else {
				//System.out.println(Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) + " <= " + temp);
				//System.out.println("Chemin refusé");
			}
				
		}
		
		return tauxAcceptation/pb.getTailleDonnees();
	}
	
	public void afficherChemin(int[] chemin) {
		for (int i=0; i<chemin.length; ++i)
			System.out.print(Character.toString((char)(chemin[i]+'a')) + " -> ");
		System.out.println(Character.toString((char)(chemin[0]+'a')));
	}
	
	public void afficherDonnees() {
		pb.afficherDonnees();
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
		
		PbVoyageurCommerce pb = new PbVoyageurCommerce(250);
		pb.afficherDonnees();
		RecuitSimule recuiseur = new RecuitSimule(pb);
		recuiseur.optimiser();
		
		
		return;
	}
	
}
