package graphiqueGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import disjoncteurs.Boite;
import disjoncteurs.Disjoncteur;
import hierarchieAppareils.AppareilAbstrait;
import utile.UtilitaireGestionMenu;

/**
 * Represente un panneau qui montre une representation des disjoncteurs dans
 * leur etat (allume ou eteint). Cette classe herite de JPanel.
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */

public class PanneauDisjoncteurs extends JPanel {

	// constantes pour les dimensions choisies des boutons
	public static final int LARGEUR_BTN = 200;
	public static final int HAUTEUR_BTN = 30;
	
	// constantes pour les dimensions du grid layout des interrupteurs
	public static final int NB_LIGNES_INTERRUPTEURS = 1;
	public static final int NB_COLONNES_INTERRUPTEURS = 2;
	
	// on stocke la boite recue
	private Boite boite;
	
	// On garde une reference d'un disjoncteurGui pour pouvoir remplir la boite
	private DisjoncteurGui dGui;
	
	// On garde la reference du panneauInfoBoite pour pouvoir le mettre a jour
	private PanneauInfoBoite panneauInfoBoite;
	
	// On garde une reference de ce tableau pour pouvoir le mettre a jour
	private JPanel panneauDisjoncteur;
	
	/**
	 * Constructeur du panneau disjoncteur
	 * @param boite boite contenant les disjoncteurs que l'on desire afficher
	 * @param panneau PanneauInfoBoite du Gui
	 */
	public PanneauDisjoncteurs(Boite boite, PanneauInfoBoite panneau) {
		
		this.boite = boite;
		this.panneauInfoBoite = panneau;
		this.panneauDisjoncteur = this;
		
		remplirBoite(boite);
	}
	
	/**
	 * Methode ajoutant les boutons necessaires selon les disjoncteurs contenus
	 * dans la boite
	 * @param boite boite contenant les disjoncteurs affiches
	 */
	private void remplirBoite(Boite boite) {
		
		/*
		 * STRATEGIE : Parcourir le tableau 2D de la boite et ajouter les bons
		 * boutons selon le disjoncteur present a chaque coordonnee
		 */
		
		// On enleve d'abord tout pour pouvoir reconstruire le panneau
		this.removeAll();
		
		// changement de mise en plan pour afficher les disjoncteurs en grille
		this.setLayout(new GridLayout(Boite.NB_LIGNES_MAX, Boite.NB_COLONNES));
		
		// On met la couleur de fond au rose
		this.setBackground(Color.WHITE);
		
		// boucle parcourant le tableau de la boite
		for (int i = 0; i < Boite.NB_LIGNES_MAX; i++) {
			
			for (int j = 0; j < Boite.NB_COLONNES; j++) {
				
				// on instancie un Disjoncteur Gui et on l'ajoute au panneau
				dGui = new DisjoncteurGui(boite.getDisjoncteur(j, i), j, i);
				add(dGui.getPanneau());
			}
		}
	}
	
	
	
	/***************************************************************************
	 * INTERRUPTEUR GUI
	 **************************************************************************/
	
	/**
	 * Classe interne regroupant les boutons ayant rapport avec les
	 * interrupteurs des disjoncteurs. Cette classe herite de JPanel.
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class InterrupteurGui extends JPanel {
		
		// On stocke le disjoncteur auquel on associe les interrupteurs
		private Disjoncteur disjoncteur;
		
		// attribut pour stocker l'etat du disjoncteur
		private int etat;
		
		
		/**
		 * Constructeur de l'interrupteur Gui
		 * @param disjoncteur disjoncteur auquel on associe les interrupteurs
		 */
		public InterrupteurGui(Disjoncteur disjoncteur) {
			
			this.disjoncteur = disjoncteur;
			this.etat = disjoncteur.getEtat();
			
			ajouterBoutons(etat);
		}
		
		
		/**
		 * Methode creant et ajoutant les differents boutons selon l'etat du
		 * disjoncteur
		 * @param etat etat du disjoncteur en question
		 */
		private void ajouterBoutons(int etat) {
			
			// changement de mise en plan pour que les boutons soit colles
			this.setLayout(new GridLayout(NB_LIGNES_INTERRUPTEURS,
													NB_COLONNES_INTERRUPTEURS));
			
			// Creation des differents boutons interrupteur
			JButton BtnNoir = creerBouton(Color.BLACK);
			JButton BtnRouge = creerBouton(Color.RED);			
			JButton BtnBlanc = creerBouton(Color.WHITE);
			
			// si le disjoncteur est eteint, on ajoute un bouton rouge et noir
			if (etat == Disjoncteur.ETEINT) {
				
				add(BtnRouge);
				add(BtnNoir);
			}
			// sinon, on ajoute un bouton noir et un blanc
			else {
				
				add(BtnNoir);
				add(BtnBlanc);
			}
		}
		
		
		/**
		 * Methode creant un bouton fait pour les interrupteurGui
		 * @param couleur couleur desire du bouton
		 * @return le nouveau bouton cree avec les bons parametres
		 */
		private JButton creerBouton(Color couleur) {
			
			// Instanciation d'un nouveau bouton
			JButton btn = new JButton();
			
			// Mettre la bonne couleur au bouton
			btn.setBackground(couleur);
			
			// Mettre la bonne taille au bouton
			tailleBtnInterrupteur(btn);
			
			// Ajouter l'ecouteur des boutons interrupteurs a ce bouton
			btn.addActionListener(new EcouteBtnInterrupteur(disjoncteur));
			
			return btn;
		}
		
		
		/**
		 * Methode permettant de fixer la taille des boutons selon les tailles 
		 * choisies arbitrairement
		 * @param btn bouton dont on veut changer les dimensions
		 */
		private void tailleBtnInterrupteur(JButton btn) {
			
			btn.setMinimumSize(new Dimension(LARGEUR_BTN/2, HAUTEUR_BTN));
			btn.setPreferredSize(new Dimension(LARGEUR_BTN/2, HAUTEUR_BTN));
			btn.setMaximumSize(new Dimension(LARGEUR_BTN/2, HAUTEUR_BTN));
		}
		
		
		/***********************************************************************
		 * ECOUTEUR BOUTON INTERRUPTEUR
		 **********************************************************************/ 
		 
		/**
		 * Classe interne a InterrupteurGui pour gerer les listeners des boutons
		 * associe aux interrupteurs. La classe implemente ActionListener.
		 * 
		 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
		 */
		private class EcouteBtnInterrupteur implements ActionListener {
			
			// On stocke le disjoncteur associe a l'interrupteur
			private Disjoncteur disjoncteur;
			
			/**
			 * Constructeur par copie d'attributs de EcouteurBtnInterrupteur
			 * @param disjoncteur disjoncteur associe aux interrupteurs
			 */
			public EcouteBtnInterrupteur(Disjoncteur disjoncteur) {
				
				this.disjoncteur = disjoncteur;
			}
			
			
			/**
			 * Methode expliquant quoi faire lorsque les boutons interrupteurs
			 * sont cliques
			 */
			public void actionPerformed(ActionEvent e) {
				
				// Afficher la fenetre des appareils de ce disjoncteur
				new FenetreAppareils(disjoncteur);
				
				// Mettre a jour les panneaux
				miseAjourPanneaux();
			}
			
			
			/**
			 * Methode permettant de mettre a jour les panneaux
			 */
			public void miseAjourPanneaux() {
				
				// Refaire le panneauInfoBoite avec les nouvelles donnees
				panneauInfoBoite.initComposants();
				
				panneauInfoBoite.validate();
				panneauInfoBoite.repaint();
				
				// Refaire le panneauDisjoncteur en remplissant la boite encore
				remplirBoite(boite);
				panneauDisjoncteur.validate();
				panneauDisjoncteur.repaint();
			}
		}
	}	
	
	
	
	/***************************************************************************
	 * DISJONCTEUR GUI
	 **************************************************************************/
	
	/**
	 * Classe regroupant les boutons des disjoncteurs en tant que tel
	 * 
	 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
	 */
	private class DisjoncteurGui {
		
		// on stocke le disjoncteur dans un attribut et on cree un JPanel
		private JPanel panneau;
		private Disjoncteur disjoncteur;
		
		// On stocke la position du disjoncteur dans la boite
		private int colonne;
		private int ligne;
		
		/**
		 * Constructeur par copie d'attributs du disjoncteur Gui
		 * @param disjoncteur disjoncteur en question
		 * @param colonne colonne de sa position dans la boite
		 * @param ligne ligne de sa position dans la boite
		 */
		public DisjoncteurGui(Disjoncteur disjoncteur, int colonne,
																int ligne) {
			
			this.disjoncteur = disjoncteur;
			this.colonne = colonne;
			this.ligne = ligne;
			
			// on instancie notre attribut comme JPanel
			this.panneau = new JPanel();
			
			obtenirBtn(disjoncteur);
		}
		
		
		/**
		 * Accesseur du JPanel cree dans la classe
		 * @return le panneau avec son contenu
		 */
		public JPanel getPanneau() {
			
			return panneau;
		}
		
		
		/**
		 * Methode instanciant et ajoutant les boutons selon les conditions du
		 * disjoncteur
		 * @param disjoncteur disjoncteur que l'on desire afficher
		 */
		private void obtenirBtn(Disjoncteur disjoncteur) {
			
			// on stocke la nouvelle reference des disjoncteurs
			this.disjoncteur = disjoncteur;
			
			// on remet a jour l'etat du disjoncteur (si on eteint un appareil)
			if (disjoncteur != null) {
				
				disjoncteur.miseAJourEtat();
			}
			
			// on vide le panneau pour pouvoir ajouter les nouveaux composants
			panneau.removeAll();
			
			// On met la couleur de fond a rose
			panneau.setBackground(Color.WHITE);
			
			// instanciation des boutons avec leur couleur et taille
			JButton BtnVide = new JButton();
			tailleBtnDisjoncteur(BtnVide);
			BtnVide.addActionListener(new EcouteurBtnDonnees(this));
			
			JButton BtnGris = new JButton();
			BtnGris.setBackground(Color.GRAY);
			tailleBtnDisjoncteur(BtnGris);
			
			// s'il n'y a pas de disjoncteur on ajoute un bouton vide et un gris
			if (disjoncteur == null) {
				
				panneau.add(BtnVide);
				panneau.add(BtnGris);
			}
			// sinon, on ajoute les donnees du disjoncteur et on ajoute son
			// panneau d'interrupteurs
			else {
				
				BtnVide.setText(donneesDis(disjoncteur));
				panneau.add(BtnVide);
				
				panneau.add(new InterrupteurGui(disjoncteur));
			}
		}
		
		
		/**
		 * Methode creant la chaine de caractere a mettre sur le bouton du 
		 * disjoncteur
		 * @param disjoncteur disjoncteur que l'on desire afficher
		 * @return la chaine de caractere des donnees de ce disjoncteur
		 */
		private String donneesDis(Disjoncteur disjoncteur) {
			
			return disjoncteur.getAmpere() + "A/" + disjoncteur.getTension()
			+ "V/" + disjoncteur.getPuissanceEnWatt() + "W";
		}
		
		
		/**
		 * Methode ajustant la taille des boutons selon les dimensions choisies
		 * arbitrairement
		 * @param btn boutons que l'on veut ajuster
		 */
		private void tailleBtnDisjoncteur(JButton btn) {
			
			btn.setMinimumSize(new Dimension(LARGEUR_BTN, HAUTEUR_BTN));
			btn.setPreferredSize(new Dimension(LARGEUR_BTN, HAUTEUR_BTN));
			btn.setMaximumSize(new Dimension(LARGEUR_BTN, HAUTEUR_BTN));
		}
		
		
		/***********************************************************************
		 * ECOUTEUR BTN DONNEES
		 **********************************************************************/ 
		 
		/**
		 * Classe interne a DisjoncteurGui pour gerer les listeners des boutons
		 * associe aux disjoncteurs. La classe implemente ActionListener.
		 * 
		 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
		 */
		
		private class EcouteurBtnDonnees implements ActionListener {
			
			// On stocke le disjoncteurGui qui a ete clique
			private DisjoncteurGui dGui;
			
			
			/**
			 * Constructeur par copie d'attributs de EcouteurBtnDonnees
			 * @param dGui Le Disjoncteur ayant ete clique
			 */
			public EcouteurBtnDonnees(DisjoncteurGui dGui) {
				
				this.dGui = dGui;
			}
			
			
			/**
			 * Methode expliquant quoi faire lorsqu'un bouton disjoncteur a ete
			 * clique
			 */
			public void actionPerformed(ActionEvent e) {
				
				// Si le disjoncteur est null, on propose dans ajouter un
				if (dGui.disjoncteur == null) {
					
					UtilitaireGestionMenu.ajouterDisjoncteur(boite, 
													  dGui.colonne, dGui.ligne);
					
					miseAjourPanneaux();
				}
				// S'il y a un disjoncteur, on permet d'ajouter un appareil
				else {
					
					// Obtention de l'appareil ajoute par l'utilisateur
					AppareilAbstrait nouvelAppareil = 
											FenetreSaisieAppareil.getAppareil();
					
					// Si l'utilisateur n'a pas annule, on continue
					if (nouvelAppareil != null) {
						
						// Si la tension de l'appareil est egale a celle du
						// disjoncteur, on l'ajoute a la liste du disjoncteur
						if (nouvelAppareil.getTension() == 
												dGui.disjoncteur.getTension()) {
							
							dGui.disjoncteur.ajouterAppareil(nouvelAppareil);
							miseAjourPanneaux();
						}
						// Sinon, on informe l'utilisateur que ce n'est pas egal
						else {
							
							JOptionPane.showMessageDialog(null, "La tension de "
						 + "l'appareil doit etre egale a celle du disjoncteur");
						}
					}
				}
			}
			
			
			/**
			 * Methode permettant de mettre a jour les panneaux
			 */
			public void miseAjourPanneaux() {
				
				// On ajoute les nouveaux boutons de disjoncteur
				dGui.obtenirBtn(boite.getDisjoncteur(dGui.colonne, 
										 dGui.ligne));
				
				// On met a jour les etiquettes d'infos de la boite
				panneauInfoBoite.initComposants();
				
				panneauInfoBoite.validate();
				panneauInfoBoite.repaint();
				
				panneau.validate();
				panneau.repaint();
			}
		}
	}
}
	
	
	

