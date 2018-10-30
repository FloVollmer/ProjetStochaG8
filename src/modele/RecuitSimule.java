package modele;

import java.util.Random;

import vue.FenetreRendu;

public abstract class RecuitSimule extends Solveur {

	protected double invTemperature;
	protected double TMin;
	protected double meilleurCout;
	protected int nbIterations;
	protected int palliersDepuisMeilleur;
	protected double coeffDecroissanceT = 0.9;
	protected double seuilAcceptationTemperature = 0.8;
	protected double tauxAcceptation;
	protected double seuilAcceptation = 0.2;
	protected int nbMouvements;
	protected int nbPaliersArretStagnation = 100;
	protected double proba;
	protected boolean maxOuMin;
	protected ProgLineaire pb;
	protected Random rand;
<<<<<<< HEAD
	
	public double getTMin() {
		return TMin;
	}
	public void setTMin(double tMin) {
		TMin = tMin;
	}
	public double getMeilleurCout() {
		return meilleurCout;
	}
	public void setMeilleurCout(double meilleurCout) {
		this.meilleurCout = meilleurCout;
	}
	public int getNbIterations() {
		return nbIterations;
	}
	public void setNbIterations(int nbIterations) {
		this.nbIterations = nbIterations;
	}
	public int getPalliersDepuisMeilleur() {
		return palliersDepuisMeilleur;
	}
	public void setPalliersDepuisMeilleur(int palliersDepuisMeilleur) {
		this.palliersDepuisMeilleur = palliersDepuisMeilleur;
	}
	public FenetreRendu getFenetre() {
		return this.getFenetre();
	}
=======
	
	public Random getRand() {
		return rand;
	}
	public void setRand(Random rand) {
		this.rand = rand;
	}
	public double getInvTemperature() {
		return invTemperature;
	}
	public void setInvTemperature(double invTemperature) {
		this.invTemperature = invTemperature;
	}
	public double getTMin() {
		return TMin;
	}
	public void setTMin(double tMin) {
		TMin = tMin;
	}
	public double getMeilleurCout() {
		return meilleurCout;
	}
	public void setMeilleurCout(double meilleurCout) {
		this.meilleurCout = meilleurCout;
	}
	public int getNbIterations() {
		return nbIterations;
	}
	public void setNbIterations(int nbIterations) {
		this.nbIterations = nbIterations;
	}
	public int getPalliersDepuisMeilleur() {
		return palliersDepuisMeilleur;
	}
	public void setPalliersDepuisMeilleur(int palliersDepuisMeilleur) {
		this.palliersDepuisMeilleur = palliersDepuisMeilleur;
	}
	public FenetreRendu getFenetre() {
		return this.getFenetre();
	}
	
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
	public double getCoeffDecroissanceT() {
		return coeffDecroissanceT;
	}
	public void setCoeffDecroissanceT(double coeffDecroissanceT) {
		this.coeffDecroissanceT = coeffDecroissanceT;
	}
	public double getSeuilAcceptationTemperature() {
		return seuilAcceptationTemperature;
	}
	public void setSeuilAcceptationTemperature(double seuilAcceptationTemperature) {
		this.seuilAcceptationTemperature = seuilAcceptationTemperature;
	}
	public double getTauxAcceptation() {
		return tauxAcceptation;
	}
	public void setTauxAcceptation(double tauxAcceptation) {
		this.tauxAcceptation = tauxAcceptation;
	}
	public double getSeuilAcceptation() {
		return seuilAcceptation;
	}
	public void setSeuilAcceptation(double seuilAcceptation) {
		this.seuilAcceptation = seuilAcceptation;
	}
	public int getNbMouvements() {
		return nbMouvements;
	}
	public void setNbMouvements(int nbMouvements) {
		this.nbMouvements = nbMouvements;
	}
	public int getNbPaliersArretStagnation() {
		return nbPaliersArretStagnation;
	}
	public void setNbPaliersArretStagnation(int nbPaliersArretStagnation) {
		this.nbPaliersArretStagnation = nbPaliersArretStagnation;
	}
	public double getProba() {
		return proba;
	}
	public void setProba(double proba) {
		this.proba = proba;
	}
	public boolean isMaxOuMin() {
		return maxOuMin;
	}
	public void setMaxOuMin(boolean maxOuMin) {
		this.maxOuMin = maxOuMin;
	}
	public ProgLineaire getPb() {
		return pb;
	}
	public void setPb(ProgLineaire pb) {
		this.pb = pb;
	}

	
	public void afficherChemin(int[] chemin) {
		for (int i=0; i<chemin.length; ++i)
			System.out.print(Character.toString((char)(chemin[i]+'a')) + " -> ");
		System.out.println(Character.toString((char)(chemin[0]+'a')));
	}
	
	public void afficherDonnees() {
		this.getPb().afficherDonnees();
	}
	
	public void optimiser() {


		//double tauxPrec = 1;
		double tauxAct = 1;
		int iPallier = 0;
		float temperature = this.getPb().trouverSolutionInitiale() * 1.25f;
		this.setPalliersDepuisMeilleur(0);
		
		double accceptationTemperatureInitiale = fairePallier(temperature);
		while (accceptationTemperatureInitiale < this.getSeuilAcceptationTemperature()) {
			temperature = 2*temperature;
			accceptationTemperatureInitiale = fairePallier(temperature);
		}
		
<<<<<<< HEAD
		double accceptationTemperatureInitiale = fairePallier(temperature);
		while (accceptationTemperatureInitiale < this.getSeuilAcceptationTemperature()) {
			temperature = 2*temperature;
			accceptationTemperatureInitiale = fairePallier(temperature);
		}
		
		while (this.getPalliersDepuisMeilleur() < nbPaliersArretStagnation) {
=======
		while (this.getPalliersDepuisMeilleur() < this.getNbPaliersArretStagnation()) {
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
			System.out.println();
			//System.out.println();
			System.out.println("   ***   PALLIER " + (++iPallier) + "   ***");
			System.out.println("temperature = " + temperature);
			//System.out.println();
			//tauxPrec = tauxAct;
			tauxAct = fairePallier(temperature);
			System.out.println("Taux d'acceptation : " + tauxAct);
			temperature *= this.getCoeffDecroissanceT();
			if (tauxAct < this.getSeuilAcceptation()) {
				this.setPalliersDepuisMeilleur(this.getPalliersDepuisMeilleur()+1);
			}
		}
		
		pb.updateListeDonnees();
		
		System.out.println("Meilleur coût final : " + this.getMeilleurCout() + "\n");	
		System.out.println("Recuit Simulé terminé !");
	
	}
	
	public double fairePallier(float temperature) {
		
<<<<<<< HEAD
		setMeilleurCout(this.getPb().fonctionObjectif());
		double cout = this.getMeilleurCout();
		invTemperature = 1 / temperature; // on evite de faire trop de divisions car c'est une operation couteuse
		tauxAcceptation = 0;
		nbMouvements = 0;
		
		for(int i=0; i<this.getPb().getTailleDonnees(); ++i) {
			pb.mouvement();
			pb.verifierContraintes();
			float temp = rand.nextFloat();
			//System.out.println("Diff coûts = " + (pb.fctObjCandidat()-cout));
			if (this.getPb().fctObjCandidat() < cout) {
				//System.out.println("Chemin accepté");
				pb.validerMouvement();
				cout = pb.fonctionObjectif();
				++nbMouvements;
				if (cout < meilleurCout) {
=======
		this.setMeilleurCout(this.getPb().fonctionObjectif());
		double cout = this.getMeilleurCout();
		this.setInvTemperature(1 / temperature); // on evite de faire trop de divisions car c'est une operation couteuse
		this.setTauxAcceptation(0);
		this.setNbMouvements(0);
		
		for(int i=0; i<this.getPb().getTailleDonnees(); ++i) {
			this.getPb().mouvement();
			this.getPb().verifierContraintes();
			float temp = this.getRand().nextFloat();
			//System.out.println("Diff coûts = " + (pb.fctObjCandidat()-cout));
			if (this.getPb().fctObjCandidat() < cout) {
				//System.out.println("Chemin accepté");
				this.getPb().validerMouvement();
				cout = this.getPb().fonctionObjectif();
				this.setNbMouvements(this.getNbMouvements()+1);
				if (cout < this.getMeilleurCout()) {
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
					System.out.println("Nouveau meilleur cout : " + cout);
					this.getPb().nouveauMeilleur();
					this.setMeilleurCout(cout);
					this.setPalliersDepuisMeilleur(0);
				}
			} else if (Math.exp(-(this.getPb().fctObjCandidat()-cout)*this.getInvTemperature()) > temp) {
				//System.out.println(Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) + " > " + temp);
				//System.out.println("Chemin accepté");
<<<<<<< HEAD
				pb.validerMouvement();
				cout = pb.fonctionObjectif();
				++nbMouvements;
=======
				this.getPb().validerMouvement();
				cout = this.getPb().fonctionObjectif();
				this.setNbMouvements(this.getNbMouvements()+1);
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
			} else {
				//System.out.println(Math.exp(-(pb.fctObjCandidat()-cout)*invTemperature) + " <= " + temp);
				//System.out.println("Chemin refusé");
			}
				
		}
		
<<<<<<< HEAD
		return (double) ((double) nbMouvements/(double) pb.getTailleDonnees());
=======
		return (double) ((double) this.getNbMouvements()/(double) this.getPb().getTailleDonnees());
>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
	}
}
