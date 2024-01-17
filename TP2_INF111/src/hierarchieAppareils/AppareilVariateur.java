package hierarchieAppareils;

/**
 * Classe regroupant les appareils possedant un variateur. Cette classe
 * herite de AppareilAbstrait et complete les deux methodes manquantes de 
 * l'interface InterfaceAppareil
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */
public class AppareilVariateur extends AppareilAbstrait {

	// represente la valeur du variateur de l'appareil
	private double variateur;
	
	/**
	 * Constructeur par copie d'attributs de l'appareil avec variateur
	 * 
	 * @param categorie categorie de l'appareil
	 * @param tension tension de l'appareil
	 * @param ampere amperage de l'appareil
	 * @param emplacement emplacement de l'appareil
	 * @param variateur variateur de l'appareil
	 */
	public AppareilVariateur(String categorie, double ampere, int tension,
										String emplacement, double variateur) {

			super(categorie, ampere, tension, emplacement);
			
			// on stocke la valeur du variateur en pourcentage
			this.variateur = variateur/100;
	}
	
	/**
	 * Methode indiquant si l'appareil a un variateur
	 * 
	 * @return vrai, pcq cette classe represente les appareils avec variateur
	 */
	public boolean aVariateur() {
		
		return true;
	}
	
	/**
	 * Methode indiquant la puissance consommee par l'appareil a variateur
	 * 
	 * @return la puissance consommee par l'appareil a variateur
	 */
	public double getPuissanceInterne() {
		
		return super.getAmpere()*super.getTension()*variateur;
	}
}
