package vue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

public class MyButton extends JButton {

	
	public MyButton(String nomButton){
		
		super(nomButton); 
		this.setHorizontalAlignment(SwingConstants.CENTER); 
		this.setHorizontalTextPosition(SwingConstants.CENTER); 
		this.setFocusPainted(false); 
		
	}
	
	
	public static void main(String args[]){
		
		MyButton test = new MyButton("test1"); 
		MyButton test2 = new MyButton("test2"); 
		JFrame frame = new JFrame(); 
		frame.setTitle("test Image"); 
		frame.setSize(1000, 400); 
		//placer la fenetre au centre 
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		
		frame.add(test);
		frame.add(test2); 
		frame.setVisible(true); 
	}
}

