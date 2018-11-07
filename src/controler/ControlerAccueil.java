package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetreRendu;
import vue.JAccueil; 

public class ControlerAccueil {
	
	private FenetreRendu frendu;
	private JAccueil faccueil; 
	public ControlerAccueil(JAccueil accueil, FenetreRendu fenetre){
		
		modeClient(accueil, fenetre); 
		modeDev(accueil,fenetre); 
	}
	
	public void modeClient(JAccueil accueil,FenetreRendu fenetre){
		faccueil=accueil; 
		frendu =fenetre; 
		faccueil.getButtonClient().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("button mode client");
				faccueil.setVisible(false); 
				faccueil.dispose(); 
				FenetreRendu rendu = new FenetreRendu(); 
				rendu.getParametre().setVisible(false); 
				frendu = rendu; 
				ControlerButtonSolution csolution = new ControlerButtonSolution(); 
				csolution.ChargerFichier(frendu); 
				csolution.LancerCPLEX(frendu); 
				csolution.LancerRecuit(frendu); 
				csolution.LancerRecuitPVCStochastique(frendu);
				csolution.LancerCPLEXStochastique(rendu);
				
			}
		}); 
	}
	
	public void modeDev(JAccueil fenetre, FenetreRendu rendu){
		
		faccueil= fenetre; 
		frendu = rendu; 
		faccueil.getButtonDev().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				faccueil.setVisible(false); 
				faccueil.dispose(); 
				FenetreRendu rendu = new FenetreRendu(); 
				frendu = rendu; 
				ControlerButtonSolution csolution = new ControlerButtonSolution(); 
				csolution.ChargerFichier(frendu); 
				csolution.LancerCPLEX(frendu); 
				csolution.LancerRecuit(frendu); 
				csolution.LancerRecuitPVCStochastique(frendu); 
				csolution.LancerCPLEXStochastique(frendu); 
				ControlerParametre cparametre = new ControlerParametre(); 
				cparametre.InitValeurDefaut(csolution.getRecuit(),frendu.getParametre()); 
				
			}
		});
	}
	
	public FenetreRendu getFenetre(){
		return this.frendu; 
	}
	
	public static void lancePrograme(ControlerAccueil accueil){
		
		while (true) {
			accueil.getFenetre().repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public  static void  main(String args[]){
		
		JAccueil  accueil = new JAccueil(); 
		FenetreRendu rendu = null;
		ControlerAccueil  controlerAccueil = new ControlerAccueil(accueil,rendu); 
		
		//lancePrograme(controlerAccueil); 
		
	}
	
	
}
