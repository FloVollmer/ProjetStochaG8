package modele;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import vue.PanneauEvolution;
import vue.Parseur;

public class CplexStochastique extends Cplex {
	
	private double alpha = 0.95;
	private double Z; 
	private double majoration = 0.25;
	private double[][] sqrtvarcov;
	private IloNumVar[][] Xv;
	private double[][] moyennes;
	protected PanneauEvolution panneauEvolution = null;
	
	public CplexStochastique(Parseur parseur, ProgLineaire pb, boolean b) {
		super(parseur,pb,b);	
		sqrtvarcov = new double[dimension][dimension];
		for(int i = 0; i<sqrtvarcov.length ; i++) {
			for(int j = 0; j<sqrtvarcov[0].length ; j++) {
				sqrtvarcov[i][j] = Math.sqrt(0.2*couts[i][j]);
			}
		}
		moyennes = new double[dimension][dimension];
		for(int i = 0; i < dimension ; i++) {
			for(int j = 0; j < dimension ; j++) {
				moyennes[i][j] = couts[i][j];
			}
		}
	}
	
	public void setPanneauEvol(PanneauEvolution panneauEvolution) {
		this.panneauEvolution = panneauEvolution;
	}
	
	public void initialisation() {
		Xv = new IloNumVar[dimension][];
		try {
			//Modele
			modele  = new IloCplex();
			//Variables
			for(int i = 0; i<dimension; i++)
				Xv[i] = modele.boolVarArray(dimension);
			//Objectif 
			IloLinearNumExpr objectif = modele.linearNumExpr();
			for(int i = 0; i<dimension; i++) {
				for(int j = 0; j<dimension; j++) {
					objectif.addTerm(couts[i][j], Xv[i][j]);
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
						expr.addTerm(1.0, Xv[i][j]);		
				}
				modele.addEq(expr, 1.0);
			}
			for(int i = 0; i<dimension; i++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				//On ne sort qu'une fois par la ville i
				for(int j = 0; j<dimension; j++) {
					if(i!=j)
						expr.addTerm(1.0, Xv[i][j]);		
				}
				modele.addEq(expr, 1.0);
			}
			//Inegalite stochastique
			IloLinearNumExpr expr = modele.linearNumExpr();
			NormalDistribution d = new NormalDistribution(0,1);
			double q = d.inverseCumulativeProbability(alpha);
			for(int i = 0; i<dimension; i++) {
				for(int j = 0; j<dimension; j++) {
					expr.addTerm(q*sqrtvarcov[i][j]+moyennes[i][j], Xv[i][j]);
				}
			}	
			modele.addLe(expr, Z);
			//Solve
			boolean solved = modele.solve();
			if(solved) {
				for(int i = 0; i<dimension ; i++) {
					for(int j = 0; j<dimension ; j++) {
						if(i!=j && Math.abs(modele.getValue(Xv[i][j])-1.0)<=0.1)
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
	
	public void optimisation() {
		try {
			//Pour chaque sous-tour
			for(int i = 0; i<iteratif.getChemin().size() ; i++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				for(int j = 0; j<iteratif.getChemin().get(i).size() ; j++) {
					for(int k = 0; k<iteratif.getChemin().get(i).size() ; k++) {
						if(k!=j)
							expr.addTerm(1.0, Xv[iteratif.getChemin().get(i).get(j)][iteratif.getChemin().get(i).get(k)]);
					}
				}
				modele.addLe(expr, iteratif.getChemin().get(i).size()-1);
			}
			//Solve
			boolean solved = modele.solve();
			if(solved) {
				for(int i = 0; i<dimension ; i++) {
					for(int j = 0; j<dimension ; j++) {
						if(i!=j && Math.abs(modele.getValue(Xv[i][j])-1.0)<=0.1)
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
	
	@Override
	public void lancer() {
		this.run();
		Z = (1.0+majoration)*this.getCoutTotal();
		iteratif = new MethodeIterative();
		initialisation();
		boolean bool = isSousTours();
		if (panneauEvolution != null) {
			panneauEvolution.reinitDonnees();
			panneauEvolution.demarrerTimer();
		}
		while(bool) {
			optimisation();
			bool = isSousTours();
		}
		pb.updateListeDonnees(lx);
		if (panneauEvolution != null) {
			panneauEvolution.arreterTimer();
		}
		fenetre.repaint();
		System.out.println("Stochastic CPLEX finished successfully !");
	}

}
