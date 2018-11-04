package controler;
import modele.*; 
import vue.*; 

import java.awt.Event;
import javax.swing.event.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener; 
import javax.swing.event.DocumentEvent; 

public class ControlerParametre {

	private double defTemp = 20; 
	private double defTauxAcceptation = 0.2; 
	private double defCoefficientTemperature = 1.29; 
	private int defIteration = 200; 
	private double defSeuilArret = 0.2; 
	private double defPalierArret = 100; 
	
	JParametre cparametre; 
	RecuitSimule crecuit; 
	
	public ControlerParametre(){
		
		
	}
	
	public void  InitValeurDefaut(RecuitSimule recuit,JParametre parametre){
		this.cparametre=parametre; 
		this.crecuit = recuit; 
		
				cparametre.getTemperature().setText(Double.toString(defTemp));
				cparametre.getTauxAcception().setText(Double.toString(defTauxAcceptation)); 
				cparametre.getCoef().setText(Double.toString(defCoefficientTemperature)); 
				cparametre.getNbIteration().setText(Double.toString(defIteration)); 
				cparametre.getSeuil().setText(Double.toString(defSeuilArret)); 
				cparametre.getPalierArret().setText(Double.toString(defPalierArret)); 
				
		this.getTauxAcceptation(recuit, parametre); 
		this.getTemperature(recuit, parametre); 
		this.getCoefficientTemperature(recuit, parametre); 
		this.getNbIteration(recuit, parametre); 
		this.getPaliersArret(recuit, parametre); 
		this.getSeuilArret(recuit, parametre); 
	
		
		
	}
	
	public void ParametreDefault(RecuitSimule recuit,JParametre parametre){
		this.cparametre=parametre; 
		this.crecuit = recuit; 
		this.cparametre.getMyButton().addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				cparametre.getTemperature().setText(Double.toString(defTemp));
				cparametre.getTauxAcception().setText(Double.toString(defTauxAcceptation)); 
				cparametre.getCoef().setText(Double.toString(defCoefficientTemperature)); 
				cparametre.getNbIteration().setText(Double.toString(defIteration)); 
				cparametre.getSeuil().setText(Double.toString(defSeuilArret)); 
				cparametre.getPalierArret().setText(Double.toString(defPalierArret)); 
				
			}
		}); 
	}
	
	public void getTemperature(RecuitSimule recuit, JParametre parametre){
		
		this.cparametre = parametre; 
		this.crecuit = recuit; 
		
		this.cparametre.getTemperature().getDocument().addDocumentListener(new DocumentListener(){
			
			public void changedUpdate(DocumentEvent e){
				crecuit.setTMin(Double.parseDouble(cparametre.getTemperature().getText())); 
			}
			
			public void insertUpdate(DocumentEvent e){
				crecuit.setTMin(Double.parseDouble(cparametre.getTemperature().getText()));
			}
			
			public void removeUpdate(DocumentEvent e){
				crecuit.setTMin(0.); 
			}
			
		}); 
		
	}
	
	public void getTauxAcceptation(RecuitSimule recuit, JParametre parametre){
		
		this.cparametre = parametre; 
		this.crecuit = recuit; 
		this.cparametre.getTauxAcception().getDocument().addDocumentListener(new DocumentListener(){
			
			public void changedUpdate(DocumentEvent e){
				
				crecuit.setTauxAcceptation(Double.parseDouble(cparametre.getTauxAcception().getText())); 
			}
			
			public void insertUpdate(DocumentEvent e){
				
				crecuit.setTauxAcceptation(Double.parseDouble(cparametre.getTauxAcception().getText()));
			}
			
			public void removeUpdate(DocumentEvent e){
				
				crecuit.setTauxAcceptation(0.); 
			}
		}); 
	}

	public void getCoefficientTemperature(RecuitSimule recuit, JParametre parametre){
		
		this.cparametre = parametre; 
		this.crecuit = recuit; 
		
		this.cparametre.getCoef().getDocument().addDocumentListener(new DocumentListener(){
			
			public void changedUpdate(DocumentEvent e){
				
				crecuit.setCoeffDecroissanceT(Double.parseDouble(cparametre.getCoef().getText())); 
				
			}
			
			public void insertUpdate(DocumentEvent e){
				
				crecuit.setCoeffDecroissanceT(Double.parseDouble(cparametre.getCoef().getText()));
			}
			
			public void removeUpdate(DocumentEvent e){
				
				crecuit.setCoeffDecroissanceT(0.); 
			}
		}); 
	}

	public void getNbIteration(RecuitSimule recuit, JParametre parametre){
		
		this.crecuit = recuit; 
		this.cparametre = parametre; 
		
		cparametre.getNbIteration().getDocument().addDocumentListener(new DocumentListener(){
			
			public void changedUpdate(DocumentEvent e){
				
				crecuit.setNbIterations(Integer.parseInt(cparametre.getNbIteration().getText())); 
			}
			
			public void insertUpdate(DocumentEvent e){
				
				crecuit.setNbIterations(Integer.parseInt(cparametre.getNbIteration().getText())); 
			}
			
			public void removeUpdate(DocumentEvent e){
				crecuit.setNbIterations(0); 
			}
		}); 
	}
	
	

	public void getSeuilArret(RecuitSimule recuit, JParametre parametre){
		
		this.cparametre = parametre; 
		this.crecuit = recuit; 
		this.cparametre.getSeuil().getDocument().addDocumentListener(new DocumentListener(){
			
			public void changedUpdate(DocumentEvent e){
				
				crecuit.setSeuilAcceptation(Double.parseDouble(cparametre.getSeuil().getText())); 
			}
			
			public void insertUpdate(DocumentEvent e){
				
				crecuit.setSeuilAcceptation(Double.parseDouble(cparametre.getSeuil().getText())); 
			}
			
			public void removeUpdate(DocumentEvent e){
				
				crecuit.setSeuilAcceptation(0.); 
			}
		}); 
	}

	public void getPaliersArret(RecuitSimule recuit, JParametre parametre){
		
		this.cparametre = parametre; 
		this.crecuit = recuit; 
		
		this.cparametre.getPalierArret().getDocument().addDocumentListener(new DocumentListener(){
			
			public void changedUpdate(DocumentEvent e){
				
				crecuit.setNbPaliersArretStagnation(Integer.parseInt(cparametre.getPalierArret().getText())); 
			}
			
			public void insertUpdate(DocumentEvent e){
				
				crecuit.setNbPaliersArretStagnation(Integer.parseInt(cparametre.getPalierArret().getText())); 
			}
			
			public void removeUpdate(DocumentEvent e){
				crecuit.setNbPaliersArretStagnation(0); 
			}
		}); 
	}
	
	
	public static void main(String[] args){
		
		FenetreRendu rendu = new FenetreRendu(); 
		 
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
