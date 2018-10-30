package modele;

public abstract class ProgLineaire {

	protected int[][] contraintes;
	protected int[][] secondMembres;
	protected Data data;

	public ProgLineaire () {
	}
	
	public int[][] getContraintes() {
		return contraintes;
	}


	public void setContraintes(int[][] contraintes) {
		this.contraintes = contraintes;
	}


	public int[][] getSecondMembres() {
		return secondMembres;
	}


	public void setSecondMembres(int[][] secondMembres) {
		this.secondMembres = secondMembres;
	}

	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public void afficherListe(int[][] liste, int k) {
		if(k == 1) {
			for(int i=0 ; i<liste.length ; i++) {
				for(int j=0 ; j<liste[0].length ; j++) {
					System.out.println(" " + liste[i][j]);
				}
				System.out.println("\n");
			}
		}
	}
	
	public boolean equals(int[][] l1, int[][] l2) {
		boolean resultat = true;
		for(int i=0 ; i<l1.length ; i++) {
			for(int j=0 ; j<l1[0].length ; j++) {
				if(l1[i][j]!=l2[i][j])
					resultat = false;
			}
		}
		return resultat;
	}
	
	public boolean verifierContraintes() {
		int[][] resultat = new int[contraintes.length][data.getListeDonnees()[0].length];
		int[][] resultat2 = new int[contraintes.length][data.getListeDonnees()[0].length];
		Element[][] transposeeData = data.transposeeListe(); 
		if(contraintes[0].length == data.getListeDonnees().length) {
			for(int i=0 ; i<contraintes.length ; i++) {
				for(int j=0 ; j<data.getListeDonnees()[0].length; j++) {
					for(int k = 0; k<contraintes[0].length ; k++) {
						resultat[i][j]+=contraintes[i][k]*data.getListeDonnees()[k][j].getX();
						resultat2[i][j]+=contraintes[i][k]*transposeeData[k][j].getX();
					}
				}
			}
		}
		if(equals(resultat,secondMembres) && equals(resultat2,secondMembres)) {
			return true;
		}
		else {
			return false;
		}
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

	public abstract void updateListeDonnees();
}
