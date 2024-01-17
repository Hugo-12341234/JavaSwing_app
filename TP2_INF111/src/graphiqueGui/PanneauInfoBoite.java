package graphiqueGui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import disjoncteurs.Boite;

/**
 * Represente un panneau qui montre les données générales d’une boîte de 
 * disjoncteurs
 *  
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */

public class PanneauInfoBoite extends JPanel {

	// on stocke la boite recue dans cet attribut
	private Boite boite;
	
	/**
	 * Constructeur du panneau info boite
	 * @param boite boite contenant les infos que l'on desire affiche
	 */
	public PanneauInfoBoite(Boite boite) {
		
		this.boite = boite;
		
		initComposants();
	}
	
	/**
	 * Methode qui instancie et ajoute toutes les etiquettes voulu a la gauche
	 * du panneau principal
	 */
	public void initComposants() {
		
		// On enleve les composants pour permettre de reconstruire le panneau
		this.removeAll();
		
		// On met la couleur de fond a rose
		this.setBackground(Color.LIGHT_GRAY);
		
		// changement de mise en plan pour pouvoir ecrire en liste verticale
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// on instancie les etiquettes avec les accesseurs de la boite
		JLabel etiqUps = new JLabel(" UPS : " +
										 String.valueOf(boite.getMaxAmperes()));
		
		JLabel etiqConsommation = new JLabel(" Consommation : " +
						    String.valueOf(boite.getConsommationTotalEnWatt()));
		
		JLabel etiqNbDis = new JLabel(" Nombre de disjoncteurs : " + 
									 String.valueOf(boite.getNbDisjoncteurs()));
		
		JLabel etiqNbDisEntree = new JLabel(" Nombre de disjoncteurs entree : " 
							 + String.valueOf(boite.getNbDisjoncteursEntree())
							 + " ");
		
		JLabel etiqNbDisPhase = new JLabel(" Nombre de disjoncteurs phase : " +
								String.valueOf(boite.getNbDisjoncteursPhase()));
		
		// on ajoute toutes les etiquettes dans le panneau
		add(etiqUps);
		add(etiqConsommation);
		add(etiqNbDis);
		add(etiqNbDisEntree);
		add(etiqNbDisPhase);
	}
}
