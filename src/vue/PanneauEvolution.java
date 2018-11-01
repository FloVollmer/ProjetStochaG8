package vue;

import java.awt.Color;
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
	
	public PanneauEvolution (FenetreRendu fenetreRendu) {

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(new Color(32, 32, 32));
		
		/*temperatures.add(100.0);
		for (int i=0; i<100; ++i) {
			temperatures.add(temperatures.get(temperatures.size()-1)*0.9);
		}*/
		
	}
	
	@Override
	public void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		
		if (resultats.size() < 2)
			return;

		Polygon courbeT = new Polygon();
		courbeT.addPoint(getWidth(), getHeight());
		courbeT.addPoint(0, getHeight());

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double maxY = temperatures.get(0);
		for (int i=0; i<resultats.size(); ++i) {
			if (resultats.get(i) > maxY)
				maxY = resultats.get(i);
		}
		
		for (int i=0; i<temperatures.size(); ++i) {
			courbeT.addPoint(
					(int) (getWidth()*i/(temperatures.size()-1)),
					(int) (getHeight()*(1-temperatures.get(i)/maxY)));
		}
		
		g.setColor(new Color(180, 120, 0));
		g.fillPolygon(courbeT);

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
	


	public void addTemperature(double temperature) {
		temperatures.add(temperature);
	}

	public void addResultat(double resultat) {
		resultats.add(resultat);
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
