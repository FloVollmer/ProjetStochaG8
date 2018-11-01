package controler;

import modele.*; 
import vue.*; 
import java.awt.ActiveEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JLabel; 
import java.io.File; 


public class ControlerButtonSolution {
	
	private JLanceSolution solution; 
	private VoyageurCommerce pb; 
	private RecuitSimule recuit; 
	private PanneauMap map; 
	private Parseur parseur; 
	private FenetreRendu rend; 
	private Cplex cplex; 
	private JLabel nomfichier; 
	
	public ControlerButtonSolution(){
		
		//this.solution = null; 
		//this.pb = null; 
		
		
	}
	
	public void LancerRecuit(FenetreRendu rendu){
		
		this.solution = rendu.getLanceSolution(); 
		this.parseur = rendu.getParseur(); 
		this.map = rendu.getPanneauMap(); 
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
				recuit.setPanneauEvol(rend.getPanneauEvolution()); 
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
			    cplex.run();
			    System.out.println("lancement bouton cplex");
				
			}
		});
	}
	
	public void ReLancerSolver(FenetreRendu rendu){
		
		
	}

	
	public void ChargerFichier(FenetreRendu rendu){
		this.parseur=rendu.getParseur(); 
		this.map = rendu.getPanneauMap(); 
		this.rend = rendu; 
		this.rend.getButtonChargeFile().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				/*JFileChooser file = new JFileChooser(new File(".")); 
				File monfile; 
				if(file.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					  
					parseur = new Parseur(file.getSelectedFile().getPath()); 
					map = new PanneauMap(rend,parseur.getPosVilles(),new Chemin(parseur.getCouts()));
					rend.repaint(); 
					
				}*/
				parseur= new Parseur("ressources/att48.xml"); 
				
				map = new PanneauMap(rend,parseur.getPosVilles(),new Chemin(parseur.getCouts()));
				map.repaint(); 
				map.validate(); 
				
			}
		}); 
	}
	
	public static void main(String args[]){
		
		FenetreRendu rendu = new FenetreRendu(); 
		ControlerButtonSolution  controlerS = new ControlerButtonSolution(); 
		controlerS.LancerRecuit(rendu); 
		controlerS.LancerCPLEX(rendu);
		controlerS.ChargerFichier(rendu); 
		while (true) {
			rendu.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}
}
