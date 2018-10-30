package vue;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Parseur {
	
	public ArrayList<Point2D.Double> posVilles = new ArrayList<Point2D.Double>();
	public double[][] arcs;
	
	public Parseur(String nomFichier) {

	    BufferedImage imgMap_o = null;
		try {
			imgMap_o = ImageIO.read(new File(nomFichier));
		} catch (IOException e) {
		}
		
		for (int j=0; j< imgMap_o.getHeight(); ++j) {
			for (int i=0; i< imgMap_o.getWidth(); ++i) {
				if (imgMap_o.getRGB(i, j) == Color.RED.hashCode()) {
					//System.out.println("Ville en " + i + " ; " + j);
					posVilles.add(new Point.Double(i, j));
				}
			}
		}
		
		arcs = new double[posVilles.size()][posVilles.size()];

		for (int j=0; j<arcs.length; ++j)
			for (int i=0; i<arcs.length; ++i) {
				if (i==j)
					arcs[j][i] = 0;
				else {
					arcs[j][i] = (int) Math.sqrt(
							(posVilles.get(i).x - posVilles.get(j).x) * (posVilles.get(i).x - posVilles.get(j).x) + 
							(posVilles.get(i).y - posVilles.get(j).y) * (posVilles.get(i).y - posVilles.get(j).y));
				}
			}
	}
	
	public ArrayList<Point2D.Double> getPosVilles() {
		return posVilles;
	}
	
	public double[][] getArcs() {
		return arcs;
	}
	
	
	
}
