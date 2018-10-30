package modele;

<<<<<<< HEAD
=======
import java.util.Random;

>>>>>>> ce663278ecb7f098fd9d04c94fb40e45b0243fad
import vue.PanneauMap;

public class VoyageurCommerce extends ProgLineaire {
	
	private int nbVilles;
	Chemin chemin;
	Chemin candidat;
	Chemin meilleur;
	
	PanneauMap panneauMap;
	
	public VoyageurCommerce() {
		this.setData(new Data());
	}
	
	public VoyageurCommerce(Data arcs) {
		this.nbVilles = arcs.getListeDonnees().length;
		this.setData(arcs);
	}
	
	public VoyageurCommerce(int nbVilles) {
		this.nbVilles = nbVilles;
		this.setData(new Data(nbVilles));
		this.getData().setN(nbVilles);
	}
	
	public void setXInitiaux(int nbVilles) {
		for (int i=0; i<nbVilles; i++) {
			for (int j=0; j<nbVilles; j++) {
				if(i==j)
					this.getData().getListeDonnees()[i][j].setX(0); //L'arc n'existe pas
				else if(i == nbVilles-1 && j==0)
					this.getData().getListeDonnees()[i][j].setX(1);
				else if(j==i+1)
					this.getData().getListeDonnees()[i][j].setX(1);
				else
					this.getData().getListeDonnees()[i][j].setX(0);
			}
		}
	}
	
	public Chemin getChemin() {
		return chemin;
	}

	public void setChemin(Chemin chemin) {
		this.chemin = chemin;
	}

	
	public int getNbVilles() {
		return nbVilles;
	}

	public void setNbVilles(int nbVilles) {
		this.nbVilles = nbVilles;
	}

	
	//TODO : méthode recupererDonnees()
	
	public void genererContraintes() {
		int[][] l = new int[1][this.getData().getListeDonnees().length];
		for(int i = 0; i<this.getData().getN();i++) {
			l[0][i]=1;
		}
		this.setContraintes(l);
		this.setSecondMembres(l);
	}
	
	@Override
	public void mouvement() {
		candidat = Chemin.cloner(chemin);
		candidat.inv2opt();
	}
	
	@Override
	public boolean validerMouvement() {
		chemin = candidat;
		
		return false;
	}
	
	@Override
	public float trouverSolutionInitiale() {
		// Construction du chemin initial, qui est juste les villes dans l'ordre du tableau
		meilleur = Chemin.cloner(chemin);
		return chemin.coutTotal();
	}

	@Override
	public void revenirAuMeilleur() {
		chemin = Chemin.cloner(meilleur);
	}
	
	@Override
	public void nouveauMeilleur() {
		meilleur = Chemin.cloner(chemin);
		if (panneauMap != null) {
			panneauMap.setChemin(chemin);
		}
	}
	
	@Override
	public float fonctionObjectif() {
		return chemin.coutTotal();
	}
	
	@Override
	public float fctObjCandidat() {
		return candidat.coutTotal();
	}

	@Override
	public int getTailleDonnees() {
		return this.getData().getListeDonnees().length*this.getData().getListeDonnees()[0].length;
	}
	
	@Override
	public void afficherDonnees() {

		for (int j=0; j<this.getData().getListeDonnees().length; ++j) {
			for (int i=0; i<this.getData().getListeDonnees()[j].length; ++i)
				System.out.print((this.getData().getListeDonnees()[j][i].getCout()>9 ? " " : "  ") + this.getData().getListeDonnees()[j][i].getCout() + ".");
			System.out.println();
		}
		
	}
	
	public void setPanneauMap(PanneauMap panneauMap) {
		this.panneauMap = panneauMap;
	}
	
	@Override
	public boolean verifierContraintes() {
		if(chemin != null)
			return true;
		else {
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
	}
	
	@Override
	public void updateListeDonnees() {
		//On met à 0 les x pour les mettre à jour
		for(int i = 0; i< this.getData().getListeDonnees().length ; i++) {
			for(int j = 0; j< this.getData().getListeDonnees()[0].length ; j++) {
				this.getData().getListeDonnees()[i][j].setX(0);
			}
		}
		//On met à jour avec le nouveau chemin
		for (int k=0; k<this.getChemin().getSommets().length-1; k++) {
			this.getData().getListeDonnees()[this.getChemin().getSommets()[k]][this.getChemin().getSommets()[k+1]].setX(1);
		}
		this.getData().getListeDonnees()[this.getChemin().getSommets()[this.getChemin().getSommets().length-1]][this.getChemin().getSommets()[0]].setX(1);
	}
		
}
