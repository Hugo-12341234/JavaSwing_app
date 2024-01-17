package graphiqueGui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import disjoncteurs.Boite;
import utile.UtilitaireESFichier;
import utile.UtilitaireEntreeSortie;

/**
 * Classe representant la barre de menu du cadre du GUI
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */

public class MenuBar extends JMenuBar {

	// On stocke le panneauPrincipal
	private PanneauPrincipal panneauPrincipal;

	
	/**
	 * Constructeur par copie d'attributs de la barre de menu
	 * @param panneauPrincipal panneauPrincipal du cadre
	 */
	public MenuBar(PanneauPrincipal panneauPrincipal) {
		
		this.panneauPrincipal = panneauPrincipal;
		
		initComposants();
	}
	
	
	/**
	 * Methode permettant d'initialiser les composants de la barre de menu
	 */
	private void initComposants() {
		
		// Instancier le menu
		JMenu menu = new JMenu("Fichier");
		
		// Creer les items
		creerItems(menu);
		
		// Ajouter le menu a la barre de menu
		this.add(menu);
		
		// Mettre la couleur de fond
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	
	/**
	 * Methode instanciant tous les items du menu et les ajoutant a ce menu
	 * @param menu menu auquel on ajoute les items
	 */
	private void creerItems(JMenu menu) {
		
		// Instancier les items
		JMenuItem nouvelleBoite = new JMenuItem("Nouvelle boite");
		nouvelleBoite.addActionListener(new EcouteurNouvelleBoite());
		
		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		ouvrir.addActionListener(new EcouteurOuvrir());
		
		JMenuItem sauvegarder = new JMenuItem("Sauvegarder");
		sauvegarder.addActionListener(new EcouteurSauvegarder());
		
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new EcouteurQuitter());
		
		// Ajouter les items au menu
		menu.add(nouvelleBoite);
		menu.add(ouvrir);
		menu.add(sauvegarder);
		menu.add(quitter);
	}
	
	
	/**
	 * Methode permettant de mettre a jour le panneau principal afin de pouvoir
	 * afficher la nouvelle boite
	 */
	private void miseAJourPanneau() {
		
		panneauPrincipal.initComposants();
		panneauPrincipal.validate();
		panneauPrincipal.repaint();
	}
	
	
	
	/**
	 * Classe interne gerant l'ecouteur du bouton de nouvelle boite.
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class EcouteurNouvelleBoite implements ActionListener {
		
		/**
		 * Methode expliquant quoi faire lorsque le bouton est clique
		 */
		public void actionPerformed(ActionEvent e) {
			
			// Demander l'amperage de la boite
			int ampere = UtilitaireEntreeSortie.entierValide
					("Entrez l'amperage de la boite", Boite.AMPERAGE_MIN,
					Boite.AMPERAGE_MAX);
			
			// Creer une nouvelle boite et la remplir
			Boite boite = new Boite(ampere);
			boite.remplirAlea();
			
			// Remplacer la boite du panneau principal par cette nouvelle boite
			panneauPrincipal.setBoite(boite);
			
			// Mettre a jour le panneau
			miseAJourPanneau();
		}
	}
	
	
	/**
	 * Classe interne gerant l'ecouteur du bouton d'ouvrir une boite sauvegardee
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class EcouteurOuvrir implements ActionListener {
		
		/**
		 * Methode expliquant quoi faire lorsque le bouton est clique
		 */
		public void actionPerformed(ActionEvent e) {
			
			// Remplacer la boite du panneau principal par la boite recuperee
			panneauPrincipal.setBoite(UtilitaireESFichier.recupererBoite());
			
			// Mettre a jour le panneau principal
			miseAJourPanneau();
		}
	}
	
	
	/**
	 * Classe interne gerant l'ecouteur du bouton de sauvegarde d'une boite
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class EcouteurSauvegarder implements ActionListener {
		
		/**
		 * Methode expliquant quoi faire lorsque le bouton est clique
		 */
		public void actionPerformed(ActionEvent e) {
			
			// Sauvegarder la boite dans le projet
			UtilitaireESFichier.sauvegarderBoite(panneauPrincipal.getBoite());
			
			// Mettre a jour le panneau principal
			miseAJourPanneau();
		}
	}
	
	
	/**
	 * Classe interne gerant l'ecouteur du bouton de quitter le programme
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class EcouteurQuitter implements ActionListener {
		
		/**
		 * Methode expliquant quoi faire lorsque le bouton est clique
		 */
		public void actionPerformed(ActionEvent e) {
			
			// On sort du systeme
			System.exit(0);
		}
	}
}
