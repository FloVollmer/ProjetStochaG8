package modele;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import vue.PanneauEvolution;
import vue.Parseur;

public class Cplex extends Solveur {

	protected boolean maxOuMin;
	protected ProgLineaire pb;
	protected int dimension;
	protected IloCplex modele;
	protected int[][] lx;
	protected IloNumVar[][] x;
	protected double[][] couts;
	protected MethodeIterative iteratif = new MethodeIterative();
	protected double coutTotal;
	protected PanneauEvolution panneauEvolution = null;
	
	public Cplex(Parseur parseur, ProgLineaire pb, boolean b) {
		maxOuMin = b;
		this.pb = pb;
		dimension =  pb.getData().getListeDonnees().length;	
		couts  = new double[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
		couts = parseur.getCouts();
		this.lx = new int[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
	}
	
	public Cplex() {
		
	}
	
	public double getCoutTotal() {
		return coutTotal;
	}

	public void setCoutTotal(double coutTotal) {
		this.coutTotal = coutTotal;
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

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public IloCplex getModele() {
		return modele;
	}

	public void setModele(IloCplex modele) {
		this.modele = modele;
	}

	public int[][] getLx() {
		return lx;
	}

	public void setLx(int[][] lx) {
		this.lx = lx;
	}

	public IloNumVar[][] getX() {
		return x;
	}

	public void setX(IloNumVar[][] x) {
		this.x = x;
	}

	public double[][] getCouts() {
		return couts;
	}

	public void setCouts(double[][] couts) {
		this.couts = couts;
	}

	public MethodeIterative getIteratif() {
		return iteratif;
	}

	public void setIteratif(MethodeIterative iteratif) {
		this.iteratif = iteratif;
	}

	public void setX(int i, IloNumVar[] x) {
		this.x[i] = x;
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
			coutTotal = modele.getObjValue();
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
			coutTotal = modele.getObjValue();
			System.out.println("Coût total : " + modele.getObjValue());
			if (panneauEvolution != null) {
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
		boolean bool = isSousTours();
		if (panneauEvolution != null) {
			panneauEvolution.reinitDonnees();
			panneauEvolution.demarrerTimer();
		}
		while(bool) {
			optimize();
			bool = isSousTours();
		}
		pb.updateListeDonnees(lx);
		if (panneauEvolution != null) {
			panneauEvolution.arreterTimer();
		}
		fenetre.repaint();
		System.out.println("Deterministic CPLEX finished successfully !");
	}
	
	public void lancer() {}
}
