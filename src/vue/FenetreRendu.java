package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Chemin;



@SuppressWarnings("serial")
public class FenetreRendu extends JFrame implements KeyListener, MouseListener {
	
	private PanneauMap panneauMap;
	private PanneauEvolution panneauEvolution;
	
	private JParametre parametre;
	private JLanceSolution lanceSolution;
	private MyButton chargeFichier;
	private JLabel nomfichier;
	private Parseur parseur;
	
	
	
	public FenetreRendu() {
		
		
		this.chargeFichier = new MyButton("Charger un fichier");
		this.parametre = new JParametre();
		this.lanceSolution = new JLanceSolution();
		this.nomfichier = new JLabel();
		this.parseur = new Parseur("ressources/test15villes.png");
		this.panneauMap = new PanneauMap(this, parseur.getPosVilles(), new Chemin(parseur.getCouts()));
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
		top.add(this.chargeFichier);
		top.add(Box.createRigidArea(new Dimension(15,0)));
		top.add(this.nomfichier);
		
		JPanel panelGauche = new JPanel();
		panelGauche.setLayout(new BoxLayout(panelGauche,BoxLayout.Y_AXIS));
		panelGauche.add(this.lanceSolution);
		panelGauche.add(this.panelEspace());
		panelGauche.add(this.parametre);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout());
		center.add(this.panneauMap);
		center.add(panelGauche);
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(top,BorderLayout.PAGE_START);
		main.add(center,BorderLayout.CENTER);
		
		
		
		this.setTitle("Voyageur de commerce");
		this.setSize(new Dimension(1280, 720));
		//placer la fenetre au centre
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		addMouseListener(this);
		
		
		//frame.add(parametre);
		 this.setContentPane(main);
		 this.setVisible(true);
	  
	}
	
	public void actualiserChemin(Chemin chemin) {
		panneauMap.setChemin(chemin);
	}

	
	public JParametre getParametre(){
		return this.parametre; 
	}
	
	public JLanceSolution getLanceSolution(){
		return this.lanceSolution; 
	}
	
	public MyButton getButtonChargeFile(){
		return this.chargeFichier; 
	}
	
	
	public Parseur getParseur(){
		
		return this.parseur; 
	}
	

	public JPanel panelEspace(){
	
		JPanel top = new JPanel(); 
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS)); 
		top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		return top; 
	}
	
	public PanneauEvolution getPanneauEvolution(){
		
		return this.panneauEvolution; 
	}
	
	public PanneauMap getPanneauMap(){
		return this.panneauMap; 
	}
	
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}

	@Override
	public void mouseClicked(MouseEvent event) {}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}
	
	public static void main(String[] args) {
		
		FenetreRendu fenetre = new FenetreRendu();
		
		
		
		while (true) {
			fenetre.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}