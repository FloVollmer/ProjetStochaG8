package modele;

import vue.FenetreRendu;

public abstract class Solveur {

	protected FenetreRendu fenetre = null;
	
	public void setFenetre(FenetreRendu fenetre) {
		this.fenetre = fenetre;
	}
}
