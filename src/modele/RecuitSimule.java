package modele;

import java.util.Random;

import vue.FenetreRendu;

public abstract class RecuitSimule extends Solveur {

	private double invTemperature;
	private double TMin;
	private double meilleurCout;
	private int nbIterations;
	private int palliersDepuisMeilleur;
	private double coeffDecroissanceT = 0.9;
	private double seuilAcceptationTemperature = 0.8;
	private double tauxAcceptation;
	private double seuilAcceptation = 0.2;
	private int nbMouvements;
	private int nbPaliersArretStagnation = 100;
	private double proba;
	private boolean maxOuMin;
	private ProgLineaire pb;
	private Random rand;
	
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
	
	public abstract double fairePallier(float temperature);
	public abstract void optimiser();
}
