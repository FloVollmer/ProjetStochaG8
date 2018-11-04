package vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.*; 
import java.awt.*;


@SuppressWarnings("serial")
public class MyImage extends JPanel {

	private ImageIcon image; 
	
	public MyImage(String pathFile){
		super(); 
		this.setLayout(new GridBagLayout()); 
		this.image = new ImageIcon(pathFile);
		
	}
	
	public void setImage(ImageIcon image){
		this.image = image; 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0,0,this.getWidth(), this.getHeight(), this);
		}
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame(); 
		frame.setTitle("test Image"); 
		frame.setSize(1000, 400); 
		//placer la fenetre au centre 
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		MyImage image = new MyImage("ressources/pvc.png");
		frame.add(image);
		frame.setVisible(true); 
	}
}
