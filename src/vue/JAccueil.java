package vue;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;


@SuppressWarnings("serial")
public class JAccueil extends JFrame {

	private MyButton mode_dev; 
	private MyButton mode_client; 
	private MyImage image; 
	private JLabel choixmode; 
	
	public JAccueil(){
	
		mode_dev = new MyButton("Mode developpement"); 
		mode_client = new MyButton("Mode client"); 
		image = new MyImage("ressources/pvc.png"); 
		choixmode = new JLabel("Choisissez le mode de lancement"); 
		
		
		JPanel panel = new JPanel(); 
		panel.setLayout(new GridLayout()); 
		panel.add(this.CenterPanel(this.mode_dev)); 
		panel.add(this.CenterPanel(this.mode_client)); 
		
		JPanel pane = new JPanel(); 
		pane.setLayout(new GridLayout()); 
		pane.add(this.image); 
		
		JPanel panelTitre = new JPanel(); 
		panelTitre.setLayout(new GridLayout()); 
		panelTitre.add(this.CenterPanel2(this.choixmode)); 
		
		JPanel main = new JPanel(); 
		main.setLayout(new BorderLayout());
		main.add(panelTitre,BorderLayout.PAGE_START); 
		main.add(pane,BorderLayout.CENTER);
		main.add(panel,BorderLayout.PAGE_END); 
		
		//JAccueil page = new JAccueil(); 
				JFrame frame = new JFrame(); 
		this.setTitle("Projet Stochastique G8"); 
		this.setSize(1000, 400); 
				//frame.setPreferredSize(new Dimension(1280, 720));
				//placer la fenetre au centre 
	    this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(main); 
		this.setVisible(true); 
		
	}
	
	public MyButton getButtonDev(){
		
		return this.mode_dev; 
	}
	
	public MyButton getButtonClient(){
		return this.mode_client; 
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
	
	
	
	

	private JPanel CenterPanel1(JComponent component){
		
		JPanel center = new JPanel(); 
		JPanel top = new JPanel(); 
		JPanel bottom = new JPanel(); 
		JPanel left = new JPanel(); 
		JPanel right = new JPanel(); 
		
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
		top.setBorder(BorderFactory.createEmptyBorder(50,50, 50, 50)); 
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS)); 
		bottom.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS)); 
		left.setBorder(BorderFactory.createEmptyBorder(50, 155, 50, 155)); 
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS)); 
		right.setBorder(BorderFactory.createEmptyBorder(50, 155, 50, 155)); 
		
		center.setLayout(new BorderLayout()); 
		center.add(top,BorderLayout.PAGE_START); 
		center.add(bottom,BorderLayout.PAGE_END); 
		center.add(left,BorderLayout.LINE_START); 
		center.add(right,BorderLayout.LINE_END); 
		center.add(component,BorderLayout.CENTER); 
		
		return center; 
	}
	
	private JPanel CenterPanel2(JComponent component){
		
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
		left.setBorder(BorderFactory.createEmptyBorder(10, 350, 10,10)); 
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS)); 
		right.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10)); 
		
		center.setLayout(new BorderLayout()); 
		center.add(top,BorderLayout.PAGE_START); 
		center.add(bottom,BorderLayout.PAGE_END); 
		center.add(left,BorderLayout.LINE_START); 
		center.add(right,BorderLayout.LINE_END); 
		center.add(component,BorderLayout.CENTER); 
		
		return center; 
	}
	
	public static void main(String[] args){
		
		JAccueil accueil = new JAccueil(); 
	}
}

