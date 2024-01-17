package utile;
import javax.swing.JOptionPane;

import disjoncteurs.Boite;
import disjoncteurs.Disjoncteur;

/**
 * Classe qui contient les SP pour g�rer les boutons d'options
 * de menu.
 * 
 * S'il y a ajout de bouton, il faut modifier cette classe et y ajouter
 * le comportement d�sir�.
 * 
 * @author Pierre B�lisle
 * @version H2023
 *
 */
public class UtilitaireGestionMenu {

	// Extension choisie arbitrairement pour les noms de fichier contenant
	// une bo�te.
	public static final String EXTENSION_BOITE = "bte";
	
	public static final String DESC_EXTENSION =
			"*."+EXTENSION_BOITE;

	/**
	 * L'utilisateur a quitt�, on lui demande si c'est bien ce qu'il veut et 
	 * s'il veut sauvegarder avant de quitter.
	 * 
	 * return Si l'utilisateur poursuit dans sa d�marche de quitter.
	 */
	public static boolean veutSortir(Boite boite){

		// Sera mis � vrai si l'utilisateur quitte pour de bon
		boolean sortie = false;
		
		/*
		 * La sollicitation est r�alis�e avec JOptionPane et la
		 * sauvegarde par la proc�dure locale.
		 */
		String reponse = 
				JOptionPane.showInputDialog(
						"D�sirez-vous r�ellement quitter (o/n? )");

		if(reponsePositive(reponse)){

			reponse = 
					JOptionPane.showInputDialog(
							"D�sirez-vous sauvegarder avant de  quitter (o/n? )");

			if(reponsePositive(reponse)){
				sauvegarderBoite(boite);
			}

			sortie = true;
		}		

		return sortie;
	}

	/**
	 * Ajoute un disjoncteur � la bo�te.
	 * 	
	 * @param boite
	 */
	public static void ajouterDisjoncteur(Boite boite, int colonne, int ligne){

		// Sert � obtenir les infos du disjoncteur aajouter.

		// Selement s'il y en a de disponible
		if(boite.emplacementEncoreDisponible()){

			int tension;
			int ampere =  UtilitaireEntreeSortie.ampereValide();

			if(ampere != 0){

				tension = UtilitaireEntreeSortie.tensionValide();

				if(tension != 0){

					// Doit �tre r�cup�r� et valid�.
					Disjoncteur d = new Disjoncteur(ampere, tension);

					boite.ajouterDisjoncteur(colonne, ligne, d);	
				}

				else{

					JOptionPane.showMessageDialog(null,
							"Il n'y a plus de place dans la bo�te.  d�sol�!");
				}
			}
		}					
	}

	/**
	 * Ajoute une demande � un disjoncteur.  Si la demande est trop grande, 
	 * le disjoncteur est �teint.
	 * 
	 * @param boite  La boite � consid�rer.
	 */
	public static void ajouterDemande(Boite boite){
		
		// On commence par saisir la position du disjoncteur et ensuite
		// l'amp�rage du disjoncteur.  L'utilisateur peut annuler en tout temps.
		double demande;
		int ligne;
		int colonne = 
				UtilitaireEntreeSortie.entierValide("Entrez un num�ro de colonne", 
						1, 
						Boite.NB_COLONNES);
		
		// Si l'utilisateur n'a pas annul�, on saisit le num�ro de ligne.
		if(colonne != 0){
			
			ligne = UtilitaireEntreeSortie.entierValide("Entrez un num�ro de ligne", 
					1, 
					Boite.NB_LIGNES_MAX);
			
			// Si l'utilisateur n'a pas annul�, on saisit l'amp�rage.
			if(ligne != 0){

				demande = 
						UtilitaireEntreeSortie.reelValide("Entrez l'amp�rage � ajouter au disjoncteur");
				
				//Si l'utilisateur n'a pas annul�, on ajoute la demande en A au disjoncteur.
				if(demande != Double.NaN){
					
					
					// On ne fait rien si la demande est de 0.
					if(demande > 0){
						
						boite.ajouterDemande(colonne - 1,  ligne - 1, demande);
					}
					else if(demande < 0){
						
						boite.retirerPuissance(colonne - 1,  ligne - 1, demande);
					}
				}

				
			}
		}		
	}

	// �vite la r�p�tition de cette �valuation bool�enne.
	private static boolean reponsePositive(String reponse){
		
		boolean sortie = false;

		if(reponse != null){
			
			sortie = reponse.equals("o") || reponse.equals("O") ||
					reponse.toUpperCase().equals("OUI");
		}


		return sortie;
	}

	/**
	 * Sert � l'interaction avec l'utilisateur pour obtenir le nom du fichier 
	 * de sauvegarde et sa validation.
	 * 
	 * @return La boite r�cup�rer ou null.
	 */
	public static Boite recupererBoite() {

		/*
		 * Strat�gie : On utilise l'utilitaireFichierpour obtenir un nom de fichier
		 * valide.  Si le nom est null on avise avec JOptionPane.  M�me chose 
		 * pour la boite si la r�f�rence re�ue est nulle.
		 */
		Boite boite = null;

		String nomFic = UtilitaireFichier.obtenirNomFic(DESC_EXTENSION,
				EXTENSION_BOITE, 
				UtilitaireFichier.OUVRE);

		if(nomFic != null){
			
			boite = UtilitaireFichier.recupererBoite(nomFic);				
		}

		if(boite == null){
			
			JOptionPane.showMessageDialog(null, 
					"Probl�me de r�cup�ration, r�essayer svp.");
		}

		return boite;
	}

	/**
	 * Sert � l'interaction avec l'utilisateur pour obtenir le nom du fichier 
	 * de sauvegarde et sa validation.
	 * 
	 * @param La bo�te � sauvegarder.
	 */
	public static void sauvegarderBoite(Boite boite) {

		/*
		 * Strat�gie.  On utilise obtenirFic de l'utilitaire fichier pour obtenir un 
		 * nom valide et on le sauvegarde aussi � l'aide de l'utilitaire.  
		 * S'il y a un probl�me, on avise avec JOptionPane.
		 */
		String nomFic = UtilitaireFichier.obtenirNomFic(DESC_EXTENSION,
				EXTENSION_BOITE, 
				UtilitaireFichier.SAUVE);

		if(nomFic == null){
			
			JOptionPane.showMessageDialog(null, 
					"Probl�me de sauvegarde, r�essayer svp.");
		}

		else{
			
			UtilitaireFichier.sauvegarderBoite(boite, nomFic);
			UtilitaireFichier.sauvegarderDsFichierTexte(boite);
		}		
	}
}
