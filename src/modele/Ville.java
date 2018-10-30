package modele;

import java.awt.Point;
import java.util.ArrayList;

public class Ville {

	private int numero;
	public Point coordonnees = new Point();
	
	public Ville() {}
	
	public Ville(int numero, Point coordonnees) {
		this.numero = numero;
		this.coordonnees = coordonnees;
	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Point getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Point coordonnees) {
		this.coordonnees = coordonnees;
	}
	
	
}
