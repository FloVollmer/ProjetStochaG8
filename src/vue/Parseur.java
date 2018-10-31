package vue;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import modele.Arc;
import modele.Chemin;
import modele.RecuitSimulePVC;
import modele.Ville;
import modele.VoyageurCommerce;

public class Parseur {
	
	public ArrayList<Point2D.Double> posVilles = new ArrayList<Point2D.Double>();
	public double[][] couts;
	public Ville[] villes;
	
	public Parseur(String nomFichier) {
		
		String extension = nomFichier.substring(nomFichier.length()-3);
		
		if (extension.equals("png")) {
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
			
			couts = new double[posVilles.size()][posVilles.size()];

			for (int j=0; j<couts.length; ++j)
				for (int i=0; i<couts.length; ++i) {
					if (i==j)
						couts[j][i] = 0;
					else {
						couts[j][i] = (int) Math.sqrt(
								(posVilles.get(i).x - posVilles.get(j).x) * (posVilles.get(i).x - posVilles.get(j).x) + 
								(posVilles.get(i).y - posVilles.get(j).y) * (posVilles.get(i).y - posVilles.get(j).y));
					}
				}
			
			villes = new Ville[posVilles.size()];
			for(int i = 0; i<villes.length ; i++) {
				villes[i] = new Ville(i,posVilles.get(i));
			}
			
		} else if (extension.equals("xml")) {
			try {

				File fXmlFile = new File(nomFichier);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(fXmlFile);
		    	doc.getDocumentElement().normalize();

		    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    			
		    	NodeList vertices = doc.getElementsByTagName("vertex");
		    	couts = new double[vertices.getLength()][vertices.getLength()];

		    	for (int j=0; j<vertices.getLength(); ++j) {

		    		Node vertex = vertices.item(j);
		    				
		    		if (vertex.hasChildNodes()) {
		    			NodeList arcs = vertex.getChildNodes();

		    			for (int i=0; i<arcs.getLength(); ++i) {
		    				Node arc = arcs.item(i);
		    				if (arc.getNodeType() == Node.ELEMENT_NODE) {
			    				int iVille = Integer.parseInt(arc.getTextContent());
		    					if (arc.hasAttributes()) {
		    						couts[j][iVille] = (j!=iVille) ? Double.parseDouble(arc.getAttributes().item(0).getNodeValue()) : 0;
		    					}
		    				}
		    			}
		    		}
		    	}
		  
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		if (villes == null) {
			/* On fait un calcul matriciel pour passer des couts aux coordonnees */
			
			double[][] M = new double[couts.length][couts.length];
			
			for (int i = 0; i < M.length; i++){
				for (int j = 0; j < M.length; j++)
	            	M[i][j] = ((couts[0][j])*(couts[0][j]) + (couts[i][0])*(couts[i][0]) - (couts[i][j])*(couts[i][j]))*0.5;
			}
	       
			Matrix m = new Matrix(M);
			EigenvalueDecomposition e = m.eig();
			Matrix D = e.getD();
	       
			for (int i=0; i<D.getColumnDimension(); ++i)
				for (int j=0; j<D.getRowDimension(); ++j)
					D.set(i, j, Math.sqrt(D.get(i, j)));
	       
			Matrix x = e.getV().times(D);
			for (int i=0; i<couts.length; ++i)
				posVilles.add(new Point.Double(x.get(i, couts.length-2), x.get(i, couts.length-1)));
			
			villes = new Ville[posVilles.size()];
			for(int i = 0; i<villes.length ; i++) {
				villes[i] = new Ville(i, posVilles.get(i));
			}
		}
		
		
		
		

	}
	
	public ArrayList<Point2D.Double> getPosVilles() {
		return posVilles;
	}
	
	public double[][] getCouts() {
		return couts;
	}
		
	
	public Ville[] getVilles() {
		return villes;
	}

	public void setVilles(Ville[] villes) {
		this.villes = villes;
	}
	
	

	public static void main(String[] args) {
		

	    Parseur parseur = new Parseur("Ressources\\att48.xml");
	    
		VoyageurCommerce pb = new VoyageurCommerce(parseur.getCouts()[0].length);
		
	    for(int i = 0; i< parseur.getCouts().length ; i++) {
			for(int j = 0; j< parseur.getCouts()[0].length ; j++) {
				Arc arc = new Arc(parseur.getVilles()[i], parseur.getVilles()[j]);
				arc.setCout(parseur.getCouts()[i][j]);
				pb.getData().getListeDonnees()[i][j] = arc;
			}
		}
		
		Chemin chemin = new Chemin(parseur.getCouts());
		pb.setXInitiaux(parseur.getVilles().length);
		pb.setChemin(chemin);
	    pb.genererContraintes();
	    RecuitSimulePVC recuiseur = new RecuitSimulePVC(pb);
		recuiseur.afficherDonnees();
		recuiseur.optimiser();
		
		
	}

}
