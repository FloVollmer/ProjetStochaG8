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
	private int[][] x;
	private double[][] couts;
	
	public Cplex(Parseur parseur, ProgLineaire pb, boolean b) {
		maxOuMin = b;
		this.pb = pb;
		dimension =  pb.getData().getListeDonnees().length;	
		couts  = new double[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
		couts = parseur.getCouts();
		this.x = new int[pb.getData().getListeDonnees().length][pb.getData().getListeDonnees()[0].length];
	}
	
	public void run() {
		try {
			//Modele
			modele  = new IloCplex();
			//Variables
			IloNumVar [][] x = new IloNumVar[dimension][];
			for(int i = 0; i<dimension; i++)
				x[i] = modele.boolVarArray(dimension);
			IloNumVar[] u = modele.numVarArray(dimension, 0, Double.MAX_VALUE);
			//Objectif 
			IloLinearNumExpr objectif = modele.linearNumExpr();
			for(int i = 0; i<dimension; i++) {
				for(int j = 0; j<dimension; j++) {
					if(i!=j)
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
			for(int i = 1; i< dimension; i++) {
				for(int j = 1; j<dimension; j++) {
					if(i!=j) {
						IloLinearNumExpr expr = modele.linearNumExpr();
						expr.addTerm(1.0, u[i]);
						expr.addTerm(-1.0, u[j]);
						expr.addTerm(dimension-1, x[i][j]);
						modele.addLe(expr, dimension-2);
					}
				}
			}
			//Solve
			boolean solved = modele.solve();
			if(solved) {
				System.out.println("\nStatus : " + modele.getStatus());
				for(int i = 0; i<dimension ; i++) {
					for(int j = 0; j<dimension ; j++) {
						if(i!=j && modele.getValue(x[i][j])==1)
							this.x[i][j] = 1;
						else 
							this.x[i][j] = 0;
					}
				}
			}
			modele.end();
			pb.updateListeDonnees(this.x);
			System.out.println("CPLEX finished successfully");
		}
		
		catch(IloException e) {	
			e.printStackTrace();
		}
	}
	
}
