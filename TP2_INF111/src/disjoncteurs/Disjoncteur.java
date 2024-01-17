package disjoncteurs;


import java.io.Serializable;

import hierarchieAppareils.AppareilAbstrait;
import liste.Liste;

/**
* Classe qui regroupe tout qui concerne un
* disjoncteur dans le projet.
*
* On y retrouve les constantes et les  sous-programmes
* li� � un disjoncteur.
* 
* Impl�mente l'interface Serializable pour la sauvegarde
* dans un fichier binaire. 
*
* @author Pierre B�lisle
* @version Copyright H2023
*/
public class Disjoncteur implements Serializable{

	/**
	 * Enl�ve un "warning". On ne g�re pas les versions.
	 */
	private static final long serialVersionUID = 1L;
	

	
    // �tat possible d'un disjoncteur.
	public static final int ALLUME = 1;
	public static final int ETEINT = 0;
	
	// Choix d'amp�rages possibles.
	private static final int MIN_AMPERAGE = 15;
	private static final int MAX_AMPERAGE = 60;
	

	// Tous les amp�rages permis dans un tableau.  
	public static final int AMPERAGES_PERMIS[] =
		                         {MIN_AMPERAGE, 20, 40, 50, MAX_AMPERAGE};

	// Construction d'une cha�ne avec les amp�rages permis. Sert � valider.
	public static final  String CHAINE_AMPERAGE_PERMIS = 
			"15/20/40/50/60";
	
	// Les tensions possibles.
	public static final int TENSION_ENTREE = 240;
	public static final int TENSION_PHASE = 120;

	// Construction d'une cha�ne avec les tensions permises. Sert � valider.
	public static final  String CHAINE_TENSION_PERMISE = 
			"120/240";
	
	// Total de la bo�te.
	public static final double MAX = .8;
	
	/******************************
	 * * Les attributs d'un disjoncteur
	 ********************************/
	
	private double ampere;
    private double tension;

	// Une liste d'appareils sur le circuit.
	private Liste listeAppareils;
	
	// ALLUME ou ETEINT.	
    private int etat;
    
    
    /*
     * Strat�gie : On utilise la classe Listepour conserver les appareil et
     * on maintient � jour l'attribut demandeDuCircuit � chaque ajout et 
     * retrait d'appareil.
     * 
     */

	/**
	 * Constructeur qui re�oit la capacit� maximum de puissance et
	 * la tension du disjoncteur.
	 * 
	 * @param ampere
	 * @param tension
	 */
	public Disjoncteur(int ampere, int tension){

	    this.ampere  = ampere;
        this.listeAppareils = new Liste();
	    this.etat = ETEINT;
	    this.tension = tension;

	}    


	/**
	 * Ajoute un appareil sur le circuit d'un disjoncteur.
	 * 
	 * Aucune validation, l'appareil est ajout� d�but de liste.
	 * 
	 * @param appareil
	 */
	public void ajouterAppareil(AppareilAbstrait appareil){
		

		listeAppareils.inserer(appareil, 0);

		miseAJourEtat();
	}

	/**
	 * Retire le i�me appareil de la liste.
	 * 
	 * Rien n'est retir� si ieme n'existe pas dans la liste.
	 * 
	 * @param ieme La position de l'appareil � retirer. 0 == premier.
	 */
	public void retirerAppareil(int ieme){
		
		int nb = listeAppareils.getNbElements();
		
		// Si la liste n'est pas vide et que ieme est valide
		if(!listeAppareils.estVide() && ieme > -1 && ieme < nb){
			
				try{
					listeAppareils.supprimer(ieme);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				miseAJourEtat();
		}
		
	}
	
	/**
	 * Methode verifiant si le disjoncteur doit s'eteindre ou s'allumer selon
	 * sa puissance par rapport a sa puissance maximale
	 */
	public void miseAJourEtat() {
		
		// S'il y a d�passement, on ETEINT.
		if(this.getPuissanceEnWatt() <= this.puissanceMax()){

			this.setEtat(Disjoncteur.ALLUME);
		}
		else {
			this.setEtat(Disjoncteur.ETEINT);
		}
	}
	
	/**
	 * Retourne tous les appareils contenu dans le disjoncteur ou null.
	 * 
	 * @return null ou un tableau avec les appareils du disjoncteur.
	 */
	public AppareilAbstrait[] getTabAppareils(){
		
		// Tabelau � retourner
		AppareilAbstrait[] tabAppareils = null;

		int nb = listeAppareils.getNbElements();

		if(nb != 0){

			tabAppareils = new AppareilAbstrait[nb];

			for(int i = 0; i < nb; i++){
				tabAppareils[i] = 
						(AppareilAbstrait)listeAppareils.getElement(i);
			}
		}
		
		return tabAppareils;
	}

	/*
	 * @return La puissance maximum possible pour ce disjoncteur.
	 */
	double puissanceMax(){

	    return ampere * tension * MAX;
	}


	/**
	 * @return l'amperage du disjoncteur.
	 */
	public double getAmpere() {
		
		return ampere;
	}


	/**
	 * @param ampere Le nouvel amperage.
	 */
	public void setAmpere(double ampere) {
		
		this.ampere = ampere;
	}
	

	/**
	 * @return La puissance en Watts.
	 */
	public double getPuissanceEnWatt(){		
		
		/*
		 * On doit parcourir tous les appareils pour obtenir la 
		 * puissance r�elle selon l'�tat ou le variateur.
		 */
		double demandeDuCircuit = 0;
		
		int nb = listeAppareils.getNbElements();
		
		// �vite l'exception.
		for(int i =0; i < nb; i++){

			AppareilAbstrait appareil =
					(AppareilAbstrait) listeAppareils.getElement(i);

			demandeDuCircuit += appareil.getPuissance();
		}


		return demandeDuCircuit;
	}

	
	/**
	 * @return L'�tat du disjoncteur ALLUME ou ETEINT.
	 */
	public int getEtat() {
		
		return etat;
	}

	/**
	 * @param etat Modifie l'�tat par l'�tat re�u ALLUME ou ETEINT.
	 */
	public void setEtat(int etat) {
		
		this.etat = etat;
	}

	/**
	 * @return La tension du disjoncteur.
	 */
	public double getTension() {
		
		return tension;
	}
	
}