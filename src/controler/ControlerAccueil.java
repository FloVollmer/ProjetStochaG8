package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.*; 
import vue.*; 

public class ControlerAccueil {
	
	private FenetreRendu frendu;
	private JAccueil faccueil; 
	public ControlerAccueil(){
		
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
				frendu =rendu; 
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
			}
		});
	}
	
	
	
	public  static void  main(String args[]){
		
		JAccueil  accueil = new JAccueil(); 
		FenetreRendu rendu = null; 
		ControlerAccueil  controlerAccueil = new ControlerAccueil(); 
		
		controlerAccueil.modeClient(accueil,rendu); 
		controlerAccueil.modeDev(accueil, rendu); 
		
	}
	
	
}
