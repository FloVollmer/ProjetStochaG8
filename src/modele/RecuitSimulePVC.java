package modele;
import java.util.Random;

public class RecuitSimulePVC extends RecuitSimule {
	
	public RecuitSimulePVC(ProgLineaire pb) {
		this.pb = pb;
		rand = new Random();
		//On est en minimisation
		this.setMaxOuMin(false);
	}
		
}
