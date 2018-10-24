package modele;

public abstract class ProgLineaire {
	
	public ProgLineaire () {
		
	}
	
	public abstract void mouvement();
	
	public abstract boolean validerMouvement();
	
	public abstract float trouverSolutionInitiale();
	
	public abstract void revenirAuMeilleur();
	
	public abstract void nouveauMeilleur();
	
	public abstract float fonctionObjectif();
	
	public abstract float fctObjCandidat();
	
	public abstract int getTailleDonnees();
	
	public abstract void afficherDonnees();

}
