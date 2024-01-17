package graphiqueGui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import disjoncteurs.Boite;

/**
 * Represente le panneau principal affichant le contenu dans le cadre du TP2 du
 * cours INF111
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 * 
 */
public class PanneauPrincipal extends JPanel {

	// on stocke la boite recue dans un attribut et on cree un attribut pour 
	// chacun des sous-panneaux du panneau principal
	private Boite boite;
	private PanneauInfoBoite panneauInfoBoite;
	private PanneauDisjoncteurs panneauDisjoncteurs;
	
	/**
	 * Constructeur du panneau principal
	 * @param boite boite avec laquelle on veut faire le panneau
	 */
	public PanneauPrincipal(Boite boite) {
		
		this.boite = boite;
		
		initComposants();
	}
	
	/**
	 * Methode initialisant les composants du panneau principal
	 */
	public void initComposants() {
		
		// changement de mise en plan pour bien positionner les sous-panneaux
		this.setLayout(new BorderLayout());
		
		// On vide le panneau
		this.removeAll();
		
		// on cree et on ajoute le panneau info boite a gauche
		panneauInfoBoite = new PanneauInfoBoite(boite);
		add(panneauInfoBoite, BorderLayout.LINE_START);
		
		// on cree et on ajoute le panneau disjoncteur au centre
		panneauDisjoncteurs = new PanneauDisjoncteurs(boite, panneauInfoBoite);
		add(panneauDisjoncteurs, BorderLayout.CENTER);
	}
	
	
	/**
	 * Accesseur de la boite
	 */
	public Boite getBoite() {
		
		return boite;
	}
	
	
	/**
	 * Mutateur de la boite
	 */
	public void setBoite(Boite boite){
		
		this.boite = boite;
	}
}
