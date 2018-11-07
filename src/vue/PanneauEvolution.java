package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PanneauEvolution extends JPanel implements ComponentListener, MouseListener, MouseMotionListener  {
	
	ArrayList<Double> temperatures = new ArrayList<Double>();
	ArrayList<Double> resultats = new ArrayList<Double>();
	private long tempsDepart = -1;
	private long dernierTemps = -1;
	
	public PanneauEvolution (FenetreRendu fenetreRendu) {

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(new Color(63, 63, 63));
		
		/*temperatures.add(100.0);
		for (int i=0; i<100; ++i) {
			temperatures.add(temperatures.get(temperatures.size()-1)*0.9);
		}*/
		
	}
	
	@Override
	public void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		/*++i;
		g.setColor(new Color((15*i)%256, (12*i+80)%256, (9*i+160)%256));
		g.fillRect(0, 0, getWidth(), getHeight());*/


		if (tempsDepart == -1 ) // S'il n'y a pas de probleme en cours de resolution
			return;

		Font font = new Font("Arial", Font.PLAIN, getWidth()/48);
		g.setFont(font);
		g.setColor(Color.WHITE);
		String tempStr;
		long temps = (dernierTemps!=-1) ? dernierTemps : System.currentTimeMillis() - tempsDepart;
		if (temps < 1000)
			tempStr = Long.toString(temps) + " ms";
		else if (temps < 60000)
			tempStr = Long.toString(temps/1000) + "." + Long.toString(temps%1000) + " s";
		else
			tempStr = Long.toString(temps/60000) + " m " + Long.toString((temps/1000)%60) + " s";
		g.drawString("Temps : " + tempStr,(int) (getWidth()*0.8), (int)(getWidth()*0.1));

		g.setColor(Color.WHITE);
		g.drawString("Nb de " + (temperatures.size()>0 ? "paliers" : "solve") + " : " + resultats.size(),
				(int) (getWidth()*0.8), (int)(getWidth()*0.075));

		if (resultats.size() < 1) // S'il n'y a pas encore de resultats
			return;
		
		//System.out.println("yeah");
		g.setColor(Color.GREEN);
		tempStr = java.lang.Double.toString(resultats.get(resultats.size()-1));
		if (tempStr.length()>8)
			tempStr = tempStr.substring(0, 8);
		g.drawString("Cout " + (dernierTemps==-1 ? "actuel" : "final") +  " : " + tempStr,(int) (getWidth()*0.8), (int)(getWidth()*0.025));

		
		if (resultats.size() < 2) // s'il n'y a pas de quoi afficher des courbes
			return;

		tempStr = java.lang.Double.toString(resultats.get(0));
		if (tempStr.length()>8)
			tempStr = tempStr.substring(0, 8);
		g.drawString("Cout initial : " + tempStr,(int) (getWidth()*0.5), (int)(getWidth()*0.025));
		
		double maxY = temperatures.size()>0 ? temperatures.get(0) : resultats.get(0);
		for (int i=0; i<resultats.size(); ++i) {
			if (resultats.get(i) > maxY)
				maxY = resultats.get(i);
		}
		
		if (temperatures.size() > 0) {
			g.setColor(Color.ORANGE);
			tempStr = java.lang.Double.toString(temperatures.get(0));
			if (tempStr.length()>8)
				tempStr = tempStr.substring(0, 8);
			g.drawString("T initiale : " + tempStr,(int) (getWidth()*0.5), (int)(getWidth()*0.05));
			tempStr = java.lang.Double.toString(temperatures.get(temperatures.size()-1));
			if (tempStr.length()>8)
				tempStr = tempStr.substring(0, 8);
			g.drawString("T " + (dernierTemps==-1 ? "actuelle" : "finale") +  " : " + tempStr,(int) (getWidth()*0.8), (int)(getWidth()*0.05));Polygon courbeT = new Polygon();
			courbeT.addPoint(getWidth(), getHeight());
			courbeT.addPoint(0, getHeight());

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for (int i=0; i<temperatures.size(); ++i) {
				courbeT.addPoint(
						(int) (getWidth()*i/(temperatures.size()-1)),
						(int) (getHeight()*(1-temperatures.get(i)/maxY)));
			}
			
			g.setColor(new Color(180, 120, 0));
			g.fillPolygon(courbeT);
		}


		Polygon courbeRes = new Polygon();
		courbeRes.addPoint(getWidth(), getHeight());
		courbeRes.addPoint(0, getHeight());
		
		for (int i=0; i<resultats.size(); ++i) {
			courbeRes.addPoint(
					(int) (getWidth()*i/(resultats.size()-1)),
					(int) (getHeight()*(1-resultats.get(i)/maxY)));
		}

		g.setColor(Color.GREEN);
		g.drawPolygon(courbeRes);

	}

	public void demarrerTimer() {
		tempsDepart = System.currentTimeMillis();
		dernierTemps = -1;
	}
	
	public void arreterTimer() {
		dernierTemps = System.currentTimeMillis() - tempsDepart;
		System.out.println("nb Resultats = " + resultats.size());
		System.out.println("temps = " + dernierTemps);
	}
	
	public void reinitTimer() {
		dernierTemps = tempsDepart = -1;;
	}

	public void addTemperature(double temperature) {
		temperatures.add(temperature);
	}
	
	public void reinitDonnees() {
		temperatures = new ArrayList<Double>();
		resultats = new ArrayList<Double>();
		System.out.println("On reinitialise");
	}

	public void addResultat(double resultat) {
		resultats.add(resultat);
		System.out.println("nb Resultats = " + resultats.size());
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
