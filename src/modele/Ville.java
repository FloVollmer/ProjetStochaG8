package modele;

import java.awt.geom.Point2D;

public class Ville {

	private int numero;
	public Point2D.Double coordonnees = new Point2D.Double();
	
	public Ville() {}
	
	public Ville(int numero, Point2D.Double coordonnees) {
		this.numero = numero;
		this.coordonnees = coordonnees;
	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Point2D.Double getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Point2D.Double coordonnees) {
		this.coordonnees = coordonnees;
	}

	
	
}
