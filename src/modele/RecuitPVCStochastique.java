package modele;

import org.apache.commons.math3.distribution.NormalDistribution;

public class RecuitPVCStochastique extends RecuitSimulePVC {

	private double alpha = 0.95; 
	private double majoration = 0.25;
	private double[][] sqrtvarcov;
	private double[] Xv;
	private double[] moyennes;
	private int dimension;
	private double coutDeterministe;
	
	public RecuitPVCStochastique(ProgLineaire pb) {
		super(pb);
		dimension = pb.getData().getListeDonnees().length;
		sqrtvarcov = new double[dimension*dimension][dimension*dimension];
		for(int i = 0; i<sqrtvarcov.length ; i++) {
			for(int j = 0; j< sqrtvarcov[0].length ; j++) {
				if(i==j)
					sqrtvarcov[i][j] = Math.sqrt(0.2*pb.getData().getListeDonnees()[i/dimension][i%dimension].getCout());
				else //On suppose que les variables aléatoires sont indépendantes
					sqrtvarcov[i][j] = 0;
			}
		}
		int k = 0;
		Xv = new double[dimension*dimension];
		for(int i = 0; i < dimension ; i++) {
			for(int j = 0; j < dimension ; j++) {
				Xv[i*k+j] = pb.getData().getListeDonnees()[i][j].getX();
			}
			k++;
		}
		k = 0;
		moyennes = new double[dimension*dimension];
		for(int i = 0; i < dimension ; i++) {
			for(int j = 0; j < dimension ; j++) {
				moyennes[i*k+j] = pb.getData().getListeDonnees()[i][j].getCout();
			}
			k++;
		}
	}
	public double norme2(double[] x) {
		double res = 0;
		for(int i = 0; i< x.length ; i++)
			res += Math.pow(x[i], 2);
		return Math.sqrt(res);
	}
	
	public double[] produit(double[][] m, double[] v) {
		double[] res = new double[v.length];
		double S = 0;
		for(int i = 0; i<res.length ; i++) {
			for(int j = 0; j<m.length ; j++) {
				S+=m[i][j]*v[j];
			}
			res[i] = S;
			S = 0;
		}
		return res;
	}
	
	public double produitScalaire(double[] u, double[] v) {
		double res = 0;
		for(int i = 0; i< u.length ; i++) {
			res += u[i]*v[i];
		}
		return res;
	}
	
	public boolean isConditionStochastiqueVerified() {
		NormalDistribution d = new NormalDistribution(0,1);
		double q = d.inverseCumulativeProbability(alpha);
		double norme = norme2(produit(sqrtvarcov,Xv));
		double produitScalaire = produitScalaire(moyennes, Xv);
		return((produitScalaire+norme*q)<=((1+majoration)*coutDeterministe));
	}
	
	public void updateXv() {
		int k = 0;
		Xv = new double[dimension*dimension];
		for(int i = 0; i < dimension ; i++) {
			for(int j = 0; j < dimension ; j++) {
				Xv[i*k+j] = pb.getData().getListeDonnees()[i][j].getX();
			}
			k++;
		}
	}
		
	public void optimiserStochastique() {
		this.optimiser();
		coutDeterministe = this.getMeilleurCout();
		//double tauxPrec = 1;
		double tauxAct = 1;
		int iPallier = 0;
		meilleurCout = pb.trouverSolutionInitiale();
		double temperature =  1.25f * meilleurCout;
		palliersDepuisMeilleur = 0;
		
		double accceptationTemperatureInitiale = fairePallierStochastique(temperature);
		while (accceptationTemperatureInitiale < this.getSeuilAcceptationTemperature()) {
			temperature *= 2;
			accceptationTemperatureInitiale = fairePallier(temperature);
		}
		
		meilleurCout = pb.trouverSolutionInitiale();
		
		while (this.getPalliersDepuisMeilleur() < nbPaliersArretStagnation) {
			if (panneauEvolution != null) {
				panneauEvolution.addTemperature(temperature);
				panneauEvolution.addResultat(meilleurCout);
			}
			fenetre.repaint();
			System.out.println();
			System.out.println("   ***   PALLIER " + (++iPallier) + "   ***");
			System.out.println("temperature = " + temperature);
			tauxAct = fairePallier(temperature);
			System.out.println("Taux d'acceptation : " + tauxAct);
			temperature *= coeffDecroissanceT;
			if (tauxAct < seuilAcceptation) {
				++palliersDepuisMeilleur;
			}
				
		}
		pb.updateListeDonnees();
		
		System.out.println("Meilleur coût final : " + this.getMeilleurCout() + "\n");	
		System.out.println("Recuit Simulé Stochastique terminé !");
	
	}
	
	public double fairePallierStochastique(double temperature) {
		setMeilleurCout(this.getPb().fonctionObjectif());
		double cout = this.getMeilleurCout();
		invTemperature = 1 / temperature; // on evite de faire trop de divisions car c'est une operation couteuse
		tauxAcceptation = 0;
		nbMouvements = 0;
		
		for(int i=0; i<this.getPb().getTailleDonnees(); ++i) {
			pb.mouvement();	
			pb.updateWithCandidat();
			updateXv();
			
			float temp = rand.nextFloat();
			//System.out.println("Diff coûts = " + (pb.fctObjCandidat()-cout));
			if(isConditionStochastiqueVerified()) {
				if (this.getPb().fctObjCandidat() < cout) {
					//System.out.println("Chemin accepté");
					pb.validerMouvement();
					cout = pb.fonctionObjectif();
					++nbMouvements;
					if (cout < meilleurCout) {
						//System.out.println("Nouveau meilleur cout : " + cout);
						pb.nouveauMeilleur();
						meilleurCout = cout;
						palliersDepuisMeilleur = 0;
					}
				} else if (Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) > temp) {
					//System.out.println(Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) + " > " + temp);
					//System.out.println("Chemin accepté");
					pb.validerMouvement();
					cout = pb.fonctionObjectif();
					++nbMouvements;
				} else {
					//System.out.println(Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) + " <= " + temp);
					//System.out.println("Chemin refusé");
				}
			}		
		}
		
		return (double) ((double) nbMouvements/(double) pb.getTailleDonnees());
	}
	
	
}
