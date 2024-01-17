package graphiqueGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import disjoncteurs.Disjoncteur;
import hierarchieAppareils.AppareilAbstrait;

/**
 * Classe de la fenetre modale apparaissant lorsque l'utilisateur clique sur un
 * bouton interrupteur. Cette fenetre affiche les appareils du disjoncteur
 * selectionne et permet d'en supprimer.
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */
public class FenetreAppareils {
	
	// constantes pour les dimensions choisies des boutons
	public static final int LARGEUR_BTN = 45;
	public static final int HAUTEUR_BTN = 35;
	
	// fenetre modale
	private JDialog dialog;
	
	// panneau representant chaque appareil
	private JPanel panneauHorizontal = new JPanel();
	
	// panneau du contenu de la fenetre
	private JPanel panneauVertical;
	
	// attributs stockant les informations du disjoncteur selectionne
	private Disjoncteur disjoncteur;
	private AppareilAbstrait[] liste;
	
	
	/**
	 * Constructeur par copie d'attributs de la classe FenetreAppareils
	 * @param disjoncteur disjoncteur dont on veut afficher les appareils
	 */
	public FenetreAppareils(Disjoncteur disjoncteur) {
		
		this.disjoncteur = disjoncteur;
		liste = disjoncteur.getTabAppareils();
		
		initDialog(disjoncteur);
	}

	
	/**
	 * Methode decidant s'il faut creer une fenetre ou non
	 */
	private void initDialog(Disjoncteur disjoncteur) {
		
		// si le disjoncteur n'a aucun appareil, on informe l'utilisateur
		if (liste == null) {
			
			messageListeVide();
		}
		// sinon, on initalise la fenetre, on la remplit et on l'affiche
		else {
			
			initFenetre();
			
			remplirFenetre(disjoncteur);
			
			dialog.setVisible(true);
		}
	}
	
	
	/**
	 * Methode affichant un message a l'utilisateur qu'il n'y a pas d'appareils
	 */
	private void messageListeVide() {
		
		JOptionPane.showMessageDialog(null, "Il n'y a aucun appareil associe a"
				 + " ce disjoncteur.");
	}

	
	/**
	 * Methode initialisant les parametres de la fenetre a afficher
	 */
	private void initFenetre() {
		
		// Instancier la fenetre
		dialog = new JDialog();
		panneauHorizontal.removeAll();
		
		// Mettre le titre de la fenetre
		dialog.setTitle("Liste des appareils");
		
		// Mettre les dimensions de la fenetre
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setSize(dim);
		
		// Mettre la fenetre modale
		dialog.setModal(true);
		
		// Utiliser le panneau vertical décrit après comme panneau de contenu.
		panneauVertical = (JPanel) dialog.getContentPane();
		
		// Mise en page du panneau vertical
		panneauVertical.setLayout(new BoxLayout(panneauVertical, 
														  BoxLayout.PAGE_AXIS));
		// Mise en page du panneau horizontal
		panneauHorizontal.setLayout(new FlowLayout());
	}
	
	
	/**
	 * Methode remplissant la fenetre avec tous les appareils du disjoncteur
	 */
	private void remplirFenetre(Disjoncteur disjoncteur) {
		
		/*
		 * STRATEGIE : On parcourt la liste d'appareil du disjoncteur en creant
		 * un panneau horizontal pour chaque appareil que l'on ajoute au panneau
		 * vertical (principal).
		 */
		
		// On met a jour la liste des appareils apres une suppression
		this.liste = disjoncteur.getTabAppareils();
		
		// Si la liste n'est pas null, on la remplit
		if (liste != null) {
		// boucle parcourant la liste d'appareil
			for (int i = 0; i < liste.length; i++) {
				
				this.panneauHorizontal = new JPanel();
				
				ajouterTypeAppareil(liste[i]);
				ajouterInfosAppareil(liste[i]);
				ajouterXrouge(i);
				
				// ajout du panneau horizontal au panneau vertical (principal)
				panneauVertical.add(panneauHorizontal);
			}
		}
	}
	
	
	/**
	 * Methode ajoutant le checkbox indiquant de quel type d'appareil il s'agit	
	 * @param appareil appareil a evaluer
	 */
	public void ajouterTypeAppareil(AppareilAbstrait appareil) {
		
		// Instacier le checkbox
		JCheckBox type = new JCheckBox("variateur", appareil.estAllume());
		type.addActionListener(new EcouteurCheckBox(appareil));
		
		// Si cet un appareil a variateur, on ajoute la boite
		if (appareil.aVariateur()) {
			
			panneauHorizontal.add(type);
		}
		// Si cet un appareil a interrupteur, on ajoute la boite en indiquant
		// interrupteur comme texte de la boite
		else {
			
			type.setText("interrupteur");
			panneauHorizontal.add(type);
		}
	}
	
	
	/**
	 * Methode ajoutant les informations a afficher a propos de l'appareil
	 * @param appareil appareil a evaluer
	 */
	public void ajouterInfosAppareil(AppareilAbstrait appareil) {
		
		// Creation des etiquettes
		JLabel etiqCategorie = new JLabel(" " + appareil.getCategorie());
		JLabel etiqEmplacement = new JLabel(" " + appareil.getEmplacement());
		JLabel etiqAmpere = new JLabel(" " + appareil.getAmpere());
		JLabel etiqTension = new JLabel(" " + appareil.getTension());
		
		// Ajoute des etiquettes
		panneauHorizontal.add(etiqCategorie);
		panneauHorizontal.add(etiqEmplacement);
		panneauHorizontal.add(etiqAmpere);
		panneauHorizontal.add(etiqTension);
	}
	
	
	/**
	 * Methode ajoutant le bouton rouge permettant de supprimer un appareil
	 * @param ieme position de l'appareil courant
	 * @param panneau panneau horizontal courant
	 */
	public void ajouterXrouge(int ieme) {
		
		// Instanciation et mise en place des parametres du bouton
		JButton rougeX = new JButton("X");
		rougeX.setBackground(Color.RED);
		tailleBtnFenetreModale(rougeX);
		rougeX.addActionListener(new EcouteurBtnFenetreModale(ieme));
		
		// Ajout du bouton sur le panneau horizontal
		panneauHorizontal.add(rougeX);		
	}
	
	
	/**
	 * Methode ajustant la taille des boutons selon les dimensions choisies
	 * arbitrairement
	 * @param btn bouton que l'on veut ajuster
	 */
	private void tailleBtnFenetreModale(JButton btn) {
		
		btn.setMinimumSize(new Dimension(LARGEUR_BTN, HAUTEUR_BTN));
		btn.setPreferredSize(new Dimension(LARGEUR_BTN, HAUTEUR_BTN));
		btn.setMaximumSize(new Dimension(LARGEUR_BTN, HAUTEUR_BTN));
	}
	
	
	/**
	 * Classe interne servant au listener du checkbox permettant d'eteindre ou
	 * d'allumer un appareil d'un disjoncteur. La classe implemente 
	 * ActionListener.
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class EcouteurCheckBox implements ActionListener {
		
		// On stocke l'appareil a ajuster
		private AppareilAbstrait appareil;
		
		/**
		 * Constructeur par copie d'attributs du listener du checkbox
		 * @param appareil appareil a ajuster
		 */
		public EcouteurCheckBox(AppareilAbstrait appareil) {
			
			this.appareil = appareil;
		}
		
		/**
		 * Methode expliquant quoi faire lorsque l'on clique sur le checkbox
		 */
		public void actionPerformed(ActionEvent e) {
			
			appareil.setEtat(Math.abs(appareil.getEtat() - 1));
		}
	}
	
	
	/**
	 * Classe interne servant au listener du bouton rouge permettant de
	 * supprimer un disjoncteur. La classe implemente ActionListener.
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class EcouteurBtnFenetreModale implements ActionListener {
		
		// Attributs stockant la position de l'appareil et le panneau de cet 
		// appareil
		private int ieme;
		
		
		/**
		 * Constructeur par copie d'attribut du listener des boutons rouges
		 * @param ieme position de l'appareil dans la liste du disjoncteur
		 * @param panneau_a_supprimer panneau horizontal associe a l'appareil
		 */
		public EcouteurBtnFenetreModale(int ieme){
			
			this.ieme = ieme;
		}
		
		
		/**
		 * Methode expliquant quoi faire lorsque le bouton est clique
		 */
		public void actionPerformed (ActionEvent e) {
			
			// On retire l'appareil de la liste du disjoncteur
			disjoncteur.retirerAppareil(ieme);
			
			// On met a jour les panneaux
			miseAjourFenetre();
		}
		
		
		/**
		 * Methode mettant a jour les panneaux
		 */
		private void miseAjourFenetre() {
			
			panneauVertical.removeAll();
			remplirFenetre(disjoncteur);
			panneauVertical.validate();
			panneauVertical.repaint();
		}
	}	
}
