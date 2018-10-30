package modele;

import java.util.ArrayList;

public class Arc extends Element {

	private Ville ville1;
	private Ville ville2;
	
	public Arc(Ville ville1, Ville ville2) {
		this.ville1 = ville1;
		this.ville2 = ville2;
		ArrayList<Integer> l = new ArrayList<Integer>(2);
		l.add(0, this.getVille1().getNumero());
		l.add(1, this.getVille2().getNumero());
		this.setId(l); 
	}
	
	public Ville getVille1() {
		return ville1;
	}
	public void setVille1(Ville ville1) {
		this.ville1 = ville1;
	}
	public Ville getVille2() {
		return ville2;
	}
	public void setVille2(Ville ville2) {
		this.ville2 = ville2;
	}

	public void calculCout() {
		this.setCout(Math.sqrt(Math.pow(this.getVille2().getCoordonnees().getX() -this.getVille1().getCoordonnees().getX(), 2)+Math.pow(this.getVille2().getCoordonnees().getY()-this.getVille1().getCoordonnees().getY(), 2)));
	}
	
}
