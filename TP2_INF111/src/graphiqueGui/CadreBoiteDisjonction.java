package graphiqueGui;

import javax.swing.JFrame;

import disjoncteurs.Boite;

/**
 * Représente la fenêtre d'une application qui simule
 * une boîte de disjonction dans le cadre du tp2 INF111.
 *  
 * @author Pierre Bélisle 
 * 
 * @autres auteurs :  Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam 
 *
 * @version Copyright H2023
 */
public class CadreBoiteDisjonction extends JFrame implements Runnable{
	
	// constante pour le nombre d'amperes fourni a la boite creee
	public static  final int MAX_AMPERE_DEFAUT  = 200;
	
	
	/**
	 * Methode obligatoire a implementer permettant d'executer le programme et
	 * de mettre en place les parametres essentiels du cadre 
	 */
	@Override
	public void run() {
		
		initComposants();
	}
	
	/**
	 * Methode initialisant les composants du cadre
	 */
	public void initComposants() {
		
		// Créer une boîte de disjoncteurs (re : tp1) remplie aléatoirement.
		Boite boite = new Boite(MAX_AMPERE_DEFAUT);
		boite.remplirAlea();
		
		// Creer un panneau principal pour le cadre et la barre de menu
		PanneauPrincipal panneauPrincipal = new PanneauPrincipal(boite);
		
	    // Mettre un titre au cadre.
		this.setTitle("Boite de disjonction intelligente");
		
		// Mettre la fenêtre plein écran.
		this.setExtendedState(MAXIMIZED_BOTH);
		
		// Fermer l’application lors d’un clic sur X.
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Utiliser le panneau principal décrit après qui remplace le panneau de contenu.
		this.setContentPane(panneauPrincipal);
		
		// Utiliser le menu decrit apres comme barre de menu du cadre
		this.setJMenuBar(new MenuBar(panneauPrincipal));
		
		// Mettre le cadre visible.	
		this.setVisible(true);
	}
}

