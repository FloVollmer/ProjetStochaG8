package vue;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent; 
import javax.swing.JFrame;
import javax.swing.JPanel; 

public class JLanceSolution extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -963224236003957645L;
	private MyButton buttonRecuit; 
	private MyButton buttonCplex; 
	private MyButton buttonArret; 
	private MyButton buttonRelance;
	private MyButton buttonRecuitStoka; 
	private MyButton buttonCplexStoka;
	
	
	public JLanceSolution(){
		
		buttonRecuit = new MyButton("Solution Recuit"); 
		buttonCplex = new MyButton("Solution CPLEX"); 
		buttonArret = new MyButton("Arreter la solution"); 
		buttonRelance = new MyButton("Relancer la solution"); 
		this.buttonRecuitStoka = new MyButton("Recuit Stochastique"); 
		this.buttonCplexStoka = new MyButton("CPLEX Stochastique"); 
		
		/*this.setLayout(new GridLayout(2,2));
		this.add(this.InitPanel(buttonRecuit)); 
		this.add(this.InitPanel(this.buttonCplex)); 
		this.add(this.InitPanel(this.buttonArret)); 
		this.add(this.InitPanel(this.buttonRelance)); */
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); 
		this.add(this.panelX2(this.buttonRecuit,this.buttonCplex)); 
		this.add(this.panelX2(this.buttonRecuitStoka,this.buttonCplexStoka)); 
		//this.add(this.panelX2(this.buttonArret,this.buttonRelance)); 
		
	}
	
	private JPanel InitPanel(JComponent component){
		
		JPanel panel = new JPanel(); 
		JPanel pane = new JPanel();
		JPanel pane1 = new JPanel(); 
		JPanel pane2 = new JPanel(); 
		JPanel pane3 = new JPanel(); 
		
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane1.setLayout(new BoxLayout(pane1,BoxLayout.X_AXIS)); 
		pane2.setLayout(new BoxLayout(pane2,BoxLayout.X_AXIS));
		pane3.setLayout(new BoxLayout(pane3,BoxLayout.Y_AXIS)); 
		
		pane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10,10));
		pane1.setBorder(BorderFactory.createEmptyBorder(5,10,5,10)); 
		pane2.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		pane3.setBorder(BorderFactory.createEmptyBorder(5,10,10,10)); 
		
		panel.setLayout(new BorderLayout()); 
		panel.add(pane,BorderLayout.PAGE_START); 
		panel.add(component,BorderLayout.CENTER);
		panel.add(pane2,BorderLayout.LINE_START); 
		panel.add(pane1,BorderLayout.LINE_END); 
		panel.add(pane3,BorderLayout.PAGE_END); 
		return panel; 
	}
	
	public MyButton getButtonRecuit(){
		return this.buttonRecuit; 
	}
	
	public MyButton getButtonCPLEX(){
		return this.buttonCplex; 
	}
	
	public MyButton getButtonArret(){
		return this.buttonArret; 
	}
	
	public MyButton getButtonRelance(){
		return this.buttonRelance; 
	}
	
	public MyButton getButtonRecuitStocha(){
		return this.buttonRecuitStoka; 
	}
	
	public MyButton getButtonCPLEXStocha(){
		return this.buttonCplexStoka; 
	}
	
private JPanel panelX2(JComponent component, JComponent component1){
		
		JPanel panel  = new JPanel();
		JPanel pane = new JPanel(); 
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS)); 
		pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS)); 
		panel.add(component); 
		panel.add(pane); 
		panel.add(component1); 
		
		return panel; 
	}
	
	
	
	
	public static void main(String args[]){
		
		JFrame frame = new JFrame(); 
		frame.setTitle("test Button"); 
		frame.setSize(1000, 400); 
		//placer la fenetre au centre 
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		JLanceSolution lance = new JLanceSolution(); 
		
		//frame.add(parametre);
		frame.setContentPane(lance); 
		frame.setVisible(true); 
		
		
		
	}
}
