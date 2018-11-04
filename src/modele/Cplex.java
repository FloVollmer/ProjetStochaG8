package modele;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import vue.PanneauEvolution;
import vue.Parseur;

public class Cplex extends Solveur {

	private boolean maxOuMin;
	private ProgLineaire pb;
	private int dimension;
	private IloCplex modele;
	private int[][] lx;
	private IloNumVar[][] x;
	private double[][] couts;
	private MethodeIterative iteratif = new MethodeIterative();
	protected PanneauEvolution panneauEvolution = null;
	
	public Cplex(Parseur parseur, ProgLineaire pb, boolean b) {
		maxOuMin = b;
		this.pb = pb;
		dimension =  pb.getData().getListeDonnees().length;	
		couts  = new double[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
		couts = parseur.getCouts();
		this.lx = new int[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
	}
	
	public void setPanneauEvol(PanneauEvolution panneauEvolution) {
		this.panneauEvolution = panneauEvolution;
	}
	
	public void init() {
		x = new IloNumVar[dimension][];
		try {
			//Modele
			modele  = new IloCplex();
			//Variables
			for(int i = 0; i<dimension; i++)
				x[i] = modele.boolVarArray(dimension);
			//Objectif 
			IloLinearNumExpr objectif = modele.linearNumExpr();
			for(int i = 0; i<dimension; i++) {
				for(int j = 0; j<dimension; j++) {
					objectif.addTerm(couts[i][j], x[i][j]);
				}
			}
			//Si maximisation
			if(maxOuMin)
				modele.addMaximize(objectif);
			//Si minimisation
			else
				modele.addMinimize(objectif);
			//Contraintes
			for(int j = 0; j<dimension; j++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				//On n'entre qu'une fois par la ville j
				for(int i = 0; i<dimension; i++) {
					if(i!=j)
						expr.addTerm(1.0, x[i][j]);		
				}
				modele.addEq(expr, 1.0);
			}
			for(int i = 0; i<dimension; i++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				//On ne sort qu'une fois par la ville i
				for(int j = 0; j<dimension; j++) {
					if(i!=j)
						expr.addTerm(1.0, x[i][j]);		
				}
				modele.addEq(expr, 1.0);
			}
			//Solve
			boolean solved = modele.solve();
			if(solved) {
				for(int i = 0; i<dimension ; i++) {
					for(int j = 0; j<dimension ; j++) {
						if(i!=j && Math.abs(modele.getValue(x[i][j])-1.0)<=0.1)
							this.lx[i][j] = 1;
						else 
							this.lx[i][j] = 0;
					}
				}
			}
			System.out.println("Coût total : " + modele.getObjValue());
			iteratif.setX(lx);
			iteratif.remplirVilles();
		}
		catch(IloException e) {	
			e.printStackTrace();
		}
	}
	
	public void optimize() {

		try {
			//Pour chaque sous-tour
			for(int i = 0; i<iteratif.getChemin().size() ; i++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				for(int j = 0; j<iteratif.getChemin().get(i).size() ; j++) {
					for(int k = 0; k<iteratif.getChemin().get(i).size() ; k++) {
						if(k!=j)
							expr.addTerm(1.0, x[iteratif.getChemin().get(i).get(j)][iteratif.getChemin().get(i).get(k)]);
					}
				}
				modele.addLe(expr, iteratif.getChemin().get(i).size()-1);
			}
			//Solve
			boolean solved = modele.solve();
			if(solved) {
				for(int i = 0; i<dimension ; i++) {
					for(int j = 0; j<dimension ; j++) {
						if(i!=j && Math.abs(modele.getValue(x[i][j])-1.0)<=0.1)
							this.lx[i][j] = 1;
						else 
							this.lx[i][j] = 0;
					}
				}
			}
			System.out.println("Coût total : " + modele.getObjValue());
			if (panneauEvolution != null) {
				//panneauEvolution.reinitDonnees();
				panneauEvolution.addResultat(modele.getObjValue());
			}
		}
		catch(IloException e) {	
			e.printStackTrace();
		}
	}
	
	public boolean isSousTours() {
		boolean isSousTours = false;
		iteratif.setX(lx);
		iteratif.passageAuChemin();
		if(iteratif.getChemin().size() > 1) {
			isSousTours = true;
		}
		return isSousTours;
	}
	
	public void run() {
		init();
		if (panneauEvolution != null) {
			panneauEvolution.reinitDonnees();
			panneauEvolution.demarrerTimer();
		}
		boolean bool = isSousTours();
		while(bool) {
			optimize();
			bool = isSousTours();
		}
		pb.updateListeDonnees(lx);
		if (panneauEvolution != null) {
			panneauEvolution.arreterTimer();
		}
		fenetre.repaint();
		System.out.println("CPLEX finished successfully !");
	}
	
}
