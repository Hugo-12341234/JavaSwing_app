package disjoncteurs;
import java.io.Serializable;

import hierarchieAppareils.AppareilAbstrait;
import utile.Coord;
import utile.UtilitaireMath;

/**
 * Module qui permet la gestion d'une bo�te �lectrique
 * avec disjoncteurs.
 *
 * La boite doit d'abord �tre initialis�e au nombre d'amp�res voulus 
 * ainsi que son nombre de disjoncteurs maximum possibles.
 *
 * Impl�mente l'interface Serializable pour la sauvegarde
 * dans un fichier binaire. 
 * 
 * #author Pierre B�lisle
 * @version Copyright H2023
 */
public class Boite implements Serializable{
	
	/**
	 * Enl�ve un "warning". On ne g�re pas les versions.
	 */
	private static final long serialVersionUID = 1L;
	
	/*********************************
	 *  LES CONSTANTES DE LA BOITE
	 *********************************/
	// La modification a un effet direct sur l'affichage.
	public static final int MAX_DISJONCTEURS  = 40;
	public static final int NB_COLONNES  = 2;
	
	public static final int NB_LIGNES_MAX  = 
			MAX_DISJONCTEURS/NB_COLONNES;
	
	// Pour le remplissage de d�part.
    public static final double POURC_REMPLI = 0.6;
	public static final double POURC_TENSION_ENTREE = .3;
	
	public static final int AMPERAGE_MIN= 100;
	public static final int AMPERAGE_MAX = 400;
	
	/*********************************
	 *  LES ATTRIBUTS DE LA BOITE
	 *********************************/
	private int maxAmperes;
	
	// Le tableau est 2D mais il est � l'envers de la r�alit� (ligne-colonne).
	// Toutes les m�thodes qui n�cessitent la position, re�oivent (colonne-ligne).  
	private Disjoncteur[][] tabDisjoncteurs;	
	private int nbDisjoncteurs;
	
	// On d�duit les disjoncteurs TENSION_ENTREE par
	// nbDisjoncteurs - nbDisjoncteursPhase  
	private int nbDisjoncteursPhase;
	
	

	/**
	 * Constructeur par d�faut qui cr�e une bo�te vide de max_amp�res amp�res. 
	 * @param max_amperes
	 */
	public Boite(int max_amperes){
		
		// Retient le max amp�res et initialise le tableau de disjoncteurs.
	  this.maxAmperes = max_amperes;
	  initTabDisjoncteurs();
		    
	}
	
	/**
	 * Fonction locale qui cr�e et  initialise le tableau de disjoncteurs.
	 * (Bonne pratique).
	 */
	private void initTabDisjoncteurs(){

		tabDisjoncteurs = 
				new  Disjoncteur[NB_LIGNES_MAX][NB_COLONNES];

		// Tous les emplacement des disjoncteurs sont vides.
		for(int i = 0; i < NB_LIGNES_MAX; i++){
			
			for (int j = 0; j < NB_COLONNES; j++){
				
				tabDisjoncteurs[i][j] = null;
			}
		}
		
		nbDisjoncteurs = 0;
		nbDisjoncteursPhase = 0;
		

	}
	// La tension doit �tre TENSION ENTREE ou TENSION_PHASE.
	// L'amp�rage entre MIN_AMPERAGE et MAX_AMPERAGE.
	// Aucune validation n'est effectu�e ici.
	public boolean ajouterDisjoncteur(int colonne, int ligne,
	                          	      Disjoncteur disjoncteur){

	    boolean valide = tabDisjoncteurs[ligne][colonne] == null;

	    if(valide){

	        tabDisjoncteurs[ligne][colonne] = disjoncteur;
	        
	        tabDisjoncteurs[ligne][colonne]
	        		.setEtat(	Disjoncteur.ALLUME);
	        
		    nbDisjoncteurs++;
		    
		    if(disjoncteur.getTension() == Disjoncteur.TENSION_PHASE){
		    	
		    	nbDisjoncteursPhase++;		    
		    }
	    }
	   
	    return valide;
	    
	}
	
	
	/**
	 * @return Le maximum d'amperage de la bo�te.
	 */
	public int getMaxAmperes() {
		
		return maxAmperes;
	}

	
	/**
	 * @param max_amperes Le maximum d'amp�re � modifier.
	 */
	public void setMaxAmperes(int max_amperes) {
		
		this.maxAmperes = max_amperes;
	}
	
	/**
	 * @return Le nombre de disjoncteurs.
	 */
	public int getNbDisjoncteurs() {
		
		return nbDisjoncteurs;
	}


	/**
	 * @return Le nombre de disjoncteurs TENSION_PHASE.
	 */
	public int getNbDisjoncteursPhase() {
		
		return nbDisjoncteursPhase;
	}

	/**
	 * @return Le nombre de disjoncteurs TENSION_ENTREE.
	 */
	public int getNbDisjoncteursEntree() {
		
		return nbDisjoncteurs - nbDisjoncteursPhase;
	}



	/**
	 * Accesseur d'un disjonctuur dans la bo�te.
	 * 
	 * Aucune validation de (colonne-ligne) n'est faite ici.  Ils doivent �tre
	 * valides.
	 * 
	 * @param colonne
	 * @param ligne
	 * @return Le disjoncteur � la position colonne-ligne.
	 */
	public Disjoncteur getDisjoncteur(int colonne, int ligne){
		
		// Rappel : La bo�te est � l'envers du tableau (colonne-ligne). 
		return tabDisjoncteurs[ligne][colonne];
	}
	
	/*
	* Remplit la boite avec des disjoncteurs s�lectionn�s au hasard
	* � partir des constantes POURC_REMPLI et 
	* POURC_TENSION_ENTREE.
	*/
	public void  remplirAlea(){

	   // En partant, le POURC_REMPLI de la bo�te est en 
	   // TENSION_PHASE et le reste en TENSION_ENTREE.  
		// Tous sont allum�s.
	   int colonne;
	   int ligne;

	   // Compte le nombre de disjoncteurs install�.
	   int compteur = 0;

	   // Sert � obtenir les diff�rents amp�rages g�n�r�s.
	   int ampere;
	   
	   // Sert � r�cup�rer ceux � installer.
	   Disjoncteur  disjoncteur;
	   

	   // Remplit selon la valeur du POURC_REMPLI du nombre total de disjoncteurs.
	   while(compteur < (int) MAX_DISJONCTEURS * POURC_REMPLI){

	          // Un amp�rage au hasard parmis les AMPERAGES_PERMIS.
	          ampere = 
	        		  UtilitaireMath.entierAlea(0, 
	        				   Disjoncteur.AMPERAGES_PERMIS.length-1);
	          
	          
	          colonne = UtilitaireMath.entierAlea(0,  NB_COLONNES-1);
	          ligne = UtilitaireMath.entierAlea (0,  NB_LIGNES_MAX-1);

	          // Le pourcentage en volt TENSION_ENTREE
	          if(UtilitaireMath.entierAlea(0, 1) < POURC_TENSION_ENTREE){

	              // Obtenir un disjoncteur de la bonne tension.
	              disjoncteur = 
	                  new Disjoncteur(Disjoncteur.AMPERAGES_PERMIS[ampere],
	                		  Disjoncteur.TENSION_ENTREE);

	          }
	          // Le reste en TENSION_PHASE
	          else{

	              disjoncteur = 
	                    new Disjoncteur(Disjoncteur.AMPERAGES_PERMIS[ampere], 
	                    		Disjoncteur.TENSION_PHASE);
	          }

	          ajouterDisjoncteur(colonne, ligne, disjoncteur);

	          compteur++;
	         
	    }
	}



	/**
	 * Ajoute la demande au circuit du disjoncteur de la boite.
	 * Si la puissance totale est trop �lev�e, le disjoncteur est �teint. 
	 * Si la puissance est n�gative et que la demande ne d�passe pas la 
	 * capacit� totale le disjoncteur est allum�.
	 *  
	 * @param colonne
	 * @param ligne
	 * @param demande
	 */
	public void ajouterDemande(int colonne, int ligne, 
													AppareilAbstrait appareil){
	   
		// On ajoute rien sur un disjoncteur vide.
	    if(tabDisjoncteurs[ligne][colonne] != null){

	        tabDisjoncteurs[ligne][colonne]
	        	 .ajouterAppareil(appareil);	    
	    }
    }
	
	/**
	 * Retire une demande au disjoncteur.  Si la demande n'existe pas,
	 * rien n'esrt retir�.
	 *  
	 * @param colonne La demande � retirer.
	 * @param i 
	 * @param demande 
	 */	
	public void retirerPuissance(int colonne, int ligne, int ieme){
		
		if(tabDisjoncteurs[ligne][colonne]!=null){
			tabDisjoncteurs[ligne][colonne].retirerAppareil(ieme);
			
	        // S'il y a d�passement, on ETEINT.
	        if(tabDisjoncteurs[ligne][colonne].getPuissanceEnWatt() <= 
	        		tabDisjoncteurs[ligne][colonne].puissanceMax()){
	        	
	        	tabDisjoncteurs[ligne][colonne].setEtat(Disjoncteur.ALLUME);
	        }


		}
		
	}

	/**
	 * Cherche et retourne le premier emplacement vide dans l'une ou l'autre
	 * des colonnes.  S'i n'y en a pas, l'attribut colonne est PLEINE.
	 * 
	 * @return La coordonn�e d'un emplacement disponible.
	 */
	public Coord getEmplacementDisponible(){
	    
		Coord c = new Coord();

	    /*
	     * Strat�gie : On d�place le champ ligne tant qu'il n'y a pas de place.
	     * S'il devient �gal � NB_LIGNES_MAX, on d�place la colonne.
	     */
	    // Au d�part la colonne est 0.
	    do{
	    	
	    	c.setLigne(0);
	    	
	    	// On cherche dans une colonne
	    	while(c.getLigne() < tabDisjoncteurs.length && 
	    			tabDisjoncteurs[c.getLigne()][c.getColonne()]!= null){

	    		c.setLigne(c.getLigne() + 1);	    	
	    	}
	    	
	    	// si on en a pas trouv�, on change de colonne
	    	if(c.getLigne() == NB_LIGNES_MAX){	
	    		
	    		c.setColonne(c.getColonne() + 1);
	    	}
	    	
	    	
	    }while (c.getColonne() < NB_COLONNES && 
	    		     c.getLigne() == NB_LIGNES_MAX);
	    
	   
	    return c;
	}

	/**
	 * @return S'il reste encore des emplacement disponible.
	 */
	public boolean emplacementEncoreDisponible(){		
		
		// Strat�gie : On utilise la taille de la grille.
		return nbDisjoncteurs <
					tabDisjoncteurs[0].length * tabDisjoncteurs.length;
		
	
	}
	/**
	 * @return Si le disjoncteurs � la position colonne-ligne dans la bo�te 
	 * est null.
	 */
	public boolean emplacementEstVide(int colonne, int ligne){
	    
	    return tabDisjoncteurs[ligne][colonne] == null;
	}

	/**
	 * @return La consommation totale en Watts de la bo�te.
	 */
	public double getConsommationTotalEnWatt(){

	    double  total = 0;
	    
	    for(int i = 0; i < NB_LIGNES_MAX; i++){

	        for(int j = 0; j < NB_COLONNES; j++){

	            if(tabDisjoncteurs[i][j] != null){

	            	// Calcul en Watts.
	                total+=tabDisjoncteurs[i][j].getPuissanceEnWatt();
	            }
	        }
	    }

	    return total;

	}

	/**
	 * @return la puissance totale consomm�e sur les disjoncteurs. 
	 */
	public double puissance_total_boite(){
		
	    double total = 0;

	    for(int i = 0; i < NB_LIGNES_MAX; i++){

	        for(int j = 0; j < NB_COLONNES; j++){

	            if(tabDisjoncteurs[i][j] != null){

	                total+= tabDisjoncteurs[i][j].puissanceMax();
	            }
	        }
	    }

	    return total;

	}

	/*
	 * 
	 * @return  Le temps de support de la charge.
	 */
	public double temps_ups(){

	    return maxAmperes /
	                (puissance_total_boite()/Disjoncteur.TENSION_ENTREE);
	}
}
