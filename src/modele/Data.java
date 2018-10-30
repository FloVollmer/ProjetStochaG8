package modele;

public class Data {
	
	private int n;
	private Element[][] listeDonnees;

	public Data() {
		this.listeDonnees = new Element[n][n];
	}
	

	public Data(int n) {
		this.n= n;
		this.listeDonnees = new Element[n][n];
	}
	
	public Data(int u, int v) {
		this.listeDonnees = new Element[u][v];
	}
	
	
	public Element[][] getListeDonnees() {
		return listeDonnees;
	}
	public void setListeDonnees(Element[][] listeDonnees) {
		this.listeDonnees = listeDonnees;
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
	public double coutTotal() {
		double resultat = 0;
		for(int i = 0; i<n ; i++) {
			for(int j = 0; j<n ; j++) {
				resultat += listeDonnees[i][j].getCout()*listeDonnees[i][j].getX();
			}
		}
		return resultat;
	}
	
	public void addElement(Element e, int i, int j) {
		this.listeDonnees[i][j].setCout(e.getCout());
		this.listeDonnees[i][j].setId(e.getId());;
		this.listeDonnees[i][j].setX(e.getX());
	}
	
	public Element[][] transposeeListe(){
		Element[][] t = new Element[n][n];
		for(int i = 0; i<n ; i++) {
			for(int j = 0; j<n ; j++) {
				t[i][j]=listeDonnees[j][i];
			}
		}
		return t;
	}
}
