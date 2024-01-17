package utile;

import javax.swing.JOptionPane;

import disjoncteurs.Boite;

/*
 * Sert essentiellement � ouvrir les dialogues et � appeler la bonne m�thode
 * selon l'option choisie qui doit �tre valide.
 * 
 * @author Pierre B�lisle
 * @version Copyright H2023
 */
public class UtilitaireESFichier {


	// Extension choisie arbitrairement pour les noms de fichier contenant
	// une bo�te.
	public static final String EXTENSION_BOITE = "bte";

	// Sert pour le filtre des extension de fichier de JFileChooser.
	public static final String DESC_EXTENSION =
			"*."+EXTENSION_BOITE;

	/**
	 * Sert � l'interaction avec l'utilisateur pour obtenir le nom du fichier
	 * de sauvegarde et sa validation.
	 *
	 * @return La boite r�cup�rer ou null.
	 */
	public static Boite recupererBoite() {

		/*
		 * Strat�gie : On utilise l'utilitaireFichier pour obtenir un nom 
		 * de fichier valide.  Si le nom est null on avise avec JOptionPane.  
		 * M�me chose pour la boite si la r�f�rence re�ue est nulle.
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
					"Probleme de recuperation, reessayer svp.");
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
		 * Strat�gie.  On utilise obtenirFic de l'utilitaire fichier pour
		 * obtenir un nom valide et on sauvegarde aussi � l'aide de l'utilitaire.
		 * 
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
		}
	}
}