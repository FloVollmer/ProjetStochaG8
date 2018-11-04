package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import modele.Arc;
import modele.Chemin;
import modele.Cplex;
import modele.RecuitSimule;
import modele.RecuitSimulePVC;
import modele.VoyageurCommerce;
import vue.FenetreRendu;
import vue.JLanceSolution;
import vue.PanneauEvolution;
import vue.PanneauMap;
import vue.Parseur; 


public class ControlerButtonSolution {
	
	private JLanceSolution solution; 
	private VoyageurCommerce pb; 
	private RecuitSimule recuit; 
	private PanneauMap map; 
	private Parseur parseur; 
	private FenetreRendu rend; 
	private PanneauEvolution evolution;
	private Cplex cplex; 
	private JLabel nomfichier; 
	
	public ControlerButtonSolution(){
		
		Thread thread = new Thread() {
			@Override
			public synchronized void run() {
				while (true) {
					map.paintImmediately(map.getBounds());
					evolution.paintImmediately(evolution.getBounds());
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
	

	public void LancerRecuit(FenetreRendu rendu){
		
		this.solution = rendu.getLanceSolution(); 
		this.parseur = rendu.getParseur(); 
		this.map = rendu.getPanneauMap();
		this.evolution = rendu.getPanneauEvolution();
		this.rend = rendu; 
		this.solution.getButtonRecuit().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				pb = new VoyageurCommerce(parseur.getCouts()[0].length);
				
				for(int i = 0; i< parseur.getCouts().length ; i++) {
					for(int j = 0; j< parseur.getCouts()[0].length ; j++) {
						Arc arc = new Arc(parseur.getVilles()[i], parseur.getVilles()[j]);
						arc.setCout(parseur.getCouts()[i][j]);
						pb.getData().getListeDonnees()[i][j] = arc;
					}
				}
				pb.setXInitiaux(parseur.getVilles().length); 
				pb.setChemin(new Chemin(parseur.getCouts())); 
				pb.setPanneauMap(map); 
				pb.genererContraintes(); 
				
				recuit = new RecuitSimulePVC(pb); 
				recuit.setFenetre(rend);
				recuit.setPanneauEvol(evolution);
				recuit.optimiser(); 
				
				
			}
		}); 
	}

	public void LancerCPLEX(FenetreRendu rendu){
		this.solution = rendu.getLanceSolution(); 
		this.parseur = rendu.getParseur(); 
		this.map = rendu.getPanneauMap(); 
		this.rend = rendu; 
		
		this.solution.getButtonCPLEX().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				pb = new VoyageurCommerce(parseur.getCouts()[0].length);
				for(int i = 0; i< parseur.getCouts().length ; i++) {
					for(int j = 0; j< parseur.getCouts()[0].length ; j++) {
						Arc arc = new Arc(parseur.getVilles()[i], parseur.getVilles()[j]);
						arc.setCout(parseur.getCouts()[i][j]);
						pb.getData().getListeDonnees()[i][j] = arc;
					}
				}
				pb.setXInitiaux(parseur.getVilles().length); 
				pb.setChemin(new Chemin(parseur.getCouts())); 
				pb.setPanneauMap(map); 
				pb.genererContraintes(); 
				
				cplex = new Cplex(parseur,pb,false);
			    cplex.setFenetre(rend);
				cplex.setPanneauEvol(evolution);
			    cplex.run();
			    System.out.println("lancement bouton cplex");

			}
		});
	}
	
	public void ReLancerSolver(FenetreRendu rendu){
		
		
	}

	
	public void ChargerFichier(FenetreRendu rendu){
		
		this.rend = rendu; 
		this.rend.getButtonChargeFile().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				JFileChooser dialogue = new JFileChooser(new File(".")); 
				if(dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
					
					parseur = new Parseur(dialogue.getSelectedFile().getPath());
					map.setPosVilles(parseur.getPosVilles(), new Chemin(parseur.getCouts()));
					evolution.reinitDonnees();
					evolution.reinitTimer();
					//rend.setPanneauMap(map);
					rend.setLabelNomFichier(dialogue.getSelectedFile().getName());
					map.paintImmediately(map.getBounds());
					//rend.repaint(); 
					
					rend.pack(); 
					rend.setSize(1280, 720);
				}
				
				
			}
		}); 
		
		
	}
	
	public RecuitSimule getRecuit(){
		return this.recuit; 
	}
	
	public static void main(String args[]){
		
		FenetreRendu rendu = new FenetreRendu(); 
		ControlerButtonSolution  controlerS = new ControlerButtonSolution(); 
		controlerS.LancerRecuit(rendu); 
		controlerS.LancerCPLEX(rendu);
		controlerS.ChargerFichier(rendu); 
		
	}
}
