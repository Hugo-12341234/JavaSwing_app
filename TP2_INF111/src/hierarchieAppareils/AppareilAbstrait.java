package hierarchieAppareils;

import java.io.Serializable;

import disjoncteurs.Disjoncteur;

/**
 * Classe representant les appareil abstraits. Elle gere les methodes communes
 * aux deux types d'appareil. Elle implement l'interface, mais n'ecrit qu'une
 * seule des 3 methodes requises (estAllume)
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 *
 */
public abstract class AppareilAbstrait implements InterfaceAppareil,
																   Serializable{

	// attributs des appareils abstraits
	private String categorie;
	private int tension;
	private double ampere;
	private int etat;
	private String emplacement;
	
	/**
	 * Constructeur par copie d'attributs de AppareilAbstrait
	 * 
	 * @param categorie categorie de l'appareil
	 * @param tension tension de l'appareil
	 * @param ampere ampere de l'appareil
	 * @param emplacement emplacement de l'appareil
	 */
	public AppareilAbstrait(String categorie, double ampere, int tension, 
														  String emplacement) {
		this.categorie = categorie;
		this.tension = tension;
		this.ampere = ampere;
		this.emplacement = emplacement;
		this.etat = Disjoncteur.ALLUME;
	}
	
	/**
	 * Accesseur de la categorie
	 * @return la categorie de l'appareil
	 */
	public String getCategorie() {
		
		return categorie;
	}
	
	/**
	 * Accesseur de la tension
	 * @return la tension de l'appareil
	 */
	public int getTension() {
		
		return tension;
	}
	
	/**
	 * Accesseur de l'amperage
	 * @return l'amperage de l'appareil
	 */
	public double getAmpere() {
		
		return ampere;
	}
	
	/**
	 * Accesseur de l'etat
	 * @return l'etat de l'appareil
	 */
	public int getEtat() {
		
		return etat;
	}
	
	/**
	 * Accesseur de l'emplacement
	 * @return emplacement de l'appareil
	 */
	public String getEmplacement() {
		
		return emplacement;
	}
	
	/**
	 * Mutateur de l'etat
	 * @param etat etat voulu de l'appareil
	 */
	public void setEtat(int etat) {
		
		this.etat = etat;
	}
	
	/**
	 * Methode retournant si l'appareil est allume ou non
	 * @return true si l'appareil est allume, false sinon
	 */
	public boolean estAllume() {
		
		return (this.etat == Disjoncteur.ALLUME);
	}
	
	/**
	 * Methode retournant la puissance des appareils
	 * @return 0 si l'appareil est allume, le resultat du calcul sinon
	 */
	public double getPuissance() {
		
		return (this.etat == Disjoncteur.ALLUME) ? getPuissanceInterne():0;
	}
	
	
	protected abstract double getPuissanceInterne();
}
