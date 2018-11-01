package modele;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
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
	
	public Cplex(Parseur parseur, ProgLineaire pb, boolean b) {
		maxOuMin = b;
		this.pb = pb;
		dimension =  pb.getData().getListeDonnees().length;	
		couts  = new double[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
		couts = parseur.getCouts();
		this.lx = new int[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
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
						if(i!=j && modele.getValue(x[i][j])==1)
							this.lx[i][j] = 1;
						else 
							this.lx[i][j] = 0;
					}
				}
			}
			System.out.println("Coût total : " + modele.getObjValue());
		}
		catch(IloException e) {	
			e.printStackTrace();
		}
	}
	
	public void optimize() {
		try {
			IloLinearNumExpr expr = modele.linearNumExpr();
			for(int i = 0; i<iteratif.getChemin().size()-1 ; i++) {
				expr.addTerm(1.0, x[iteratif.getChemin().get(i)][iteratif.getChemin().get(i+1)]);
			}
			expr.addTerm(1.0, x[iteratif.getChemin().get(iteratif.getChemin().size()-1)][iteratif.getChemin().get(0)]);	
			modele.addLe(expr, iteratif.getChemin().size()-1);
			//Solve
			boolean solved = modele.solve();
			if(solved) {
				for(int i = 0; i<dimension ; i++) {
					for(int j = 0; j<dimension ; j++) {
						if(i!=j && modele.getValue(x[i][j])==1)
							this.lx[i][j] = 1;
						else 
							this.lx[i][j] = 0;
					}
				}
			}
			System.out.println("Coût total : " + modele.getObjValue());
		}
		catch(IloException e) {	
			e.printStackTrace();
		}
	}
	
	public boolean isSousTours() {
		boolean isSousTours = false;
		iteratif.setX(lx);
		iteratif.passageAuChemin();
		iteratif.afficherChemin();
		if(iteratif.getChemin().size() < x[0].length) {
			System.out.println("ccccccccccccccccccccccccccc");
			System.out.println(iteratif.getChemin().size());
			System.out.println(x[0].length);
			isSousTours = true;
		}
		return isSousTours;
	}
	
	public void run() {
		init();
		boolean bool = isSousTours();
		while(bool) {
			optimize();
			bool = isSousTours();
		}
		pb.updateListeDonnees(lx);
		fenetre.repaint();
		System.out.println("CPLEX finished successfully !");
	}
	
}
