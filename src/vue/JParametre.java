package vue;

import java.awt.*; 

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.JComponent; 

public class JParametre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//bouton pour le remettre les valeur initial 
	private MyButton bouttonParametre; 
	
	// champs pour recupere les valeurs
	private JTextField tempField; 
	private JTextField tauxAcceptionField; 
	private JTextField coefTField; 
	private JTextField nbIterationField; 
	private JTextField seuilArretField; 
	private JTextField palierArretField; 
	
	//Texte donnant le nom aau champs 
	private JLabel tempLabel; 
	private JLabel tauxAcceptionLabel; 
	private JLabel coefTLabel; 
	private JLabel nbIterationLabel; 
	private JLabel seuilArretLabel; 
	private JLabel palierArretLabel; 
	

	
	public JParametre(){
		super(); 
		
		this.bouttonParametre = new MyButton("Parametres recuit par defaut"); 
		
		this.tempField = new JTextField(); 
		this.tauxAcceptionField = new JTextField(); 
		this.coefTField = new JTextField(); 
		this.nbIterationField = new JTextField(); 
		this.seuilArretField = new JTextField(); 
		this.palierArretField = new JTextField(); 
		
		this.tempLabel = new JLabel("Temperature Inital"); 
		this.tauxAcceptionLabel = new JLabel("Taux d'acception initial minimum en %"); 
		this.coefTLabel = new JLabel("Coeff decroissance de T"); 
		this.nbIterationLabel = new JLabel("Nombre d'iteration/palier"); 
		this.seuilArretLabel = new JLabel("Seuil d'arret"); 
		this.palierArretLabel = new JLabel("Palier d'arret"); 
		
		
		JPanel panel = new JPanel(); 
		panel.setLayout(new GridLayout(6,2)); 
		panel.add(this.tempLabel);
		panel.add(this.HorizontalPanel(this.tempField)); 
		
		panel.add(this.tauxAcceptionLabel); 
		panel.add(this.HorizontalPanel(this.tauxAcceptionField)); 
		
		panel.add(this.coefTLabel); 
		panel.add(this.HorizontalPanel(this.coefTField)); 
		
		panel.add(this.nbIterationLabel); 
		panel.add(this.HorizontalPanel(this.nbIterationField)); 
		
		panel.add(this.seuilArretLabel); 
		panel.add(this.HorizontalPanel(this.seuilArretField)); 
		
		panel.add(this.palierArretLabel);
		panel.add(this.HorizontalPanel(this.palierArretField)); 
		 
		 
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); 
		this.add(panel); 
		this.add(this.bouttonParametre); 
		
	
		
	}
	
	
	
	/**
	 * La fonction retourne le component MyButton
	 * @return
	 */
	public MyButton getMyButton(){
		
		return this.bouttonParametre; 
	}
	
	public JTextField getTemperature(){
		
		return this.tempField; 
	}
	
	public JTextField getTauxAcception(){
		return this.tauxAcceptionField; 
	}
	
	public JTextField getCoef(){
		return this.coefTField; 
	}
	
	public JTextField getNbIteration(){
		return this.nbIterationField; 
	}

	public JTextField getSeuil(){
		return this.seuilArretField; 
	}
	
	public JTextField getPalierArret(){
		return this.palierArretField; 
	}
	
	private JPanel CenterPanel(JComponent component){
		
		JPanel center = new JPanel(); 
		JPanel top = new JPanel(); 
		JPanel bottom = new JPanel(); 
		JPanel left = new JPanel(); 
		JPanel right = new JPanel(); 
		
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
		top.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10)); 
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS)); 
		bottom.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS)); 
		left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS)); 
		right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		
		center.setLayout(new BorderLayout()); 
		center.add(top,BorderLayout.PAGE_START); 
		center.add(bottom,BorderLayout.PAGE_END); 
		center.add(left,BorderLayout.LINE_START); 
		center.add(right,BorderLayout.LINE_END); 
		center.add(component,BorderLayout.CENTER); 
		
		return center; 
	}
	
	private JPanel HorizontalPanel(JComponent component){
		
		JPanel top = new JPanel(); 
		JPanel bottom = new JPanel(); 
		JPanel main = new JPanel(); 
		
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS)); 
		top.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS)); 
		bottom.setBorder(BorderFactory.createEmptyBorder(5,10,5,10)); 
		
		main.setLayout(new BorderLayout()); 
		main.add(top,BorderLayout.PAGE_START); 
		main.add(bottom,BorderLayout.PAGE_END); 
		main.add(component,BorderLayout.CENTER); 
		
		return main; 
	}
	
	private JPanel Vertical(JComponent component){
		
		JPanel panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS)); 
		panel.add(component); 
		return panel; 
	}

	public static void main(String args[]){
		
		JFrame frame = new JFrame(); 
		frame.setTitle("test Paramètre"); 
		frame.setSize(1000, 400); 
		//placer la fenetre au centre 
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		JParametre parametre = new JParametre(); 
		
		//frame.add(parametre);
		 frame.setContentPane(parametre); 
		frame.setVisible(true); 
	}
	
	
	
}
