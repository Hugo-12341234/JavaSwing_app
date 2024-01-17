package hierarchieAppareils;

/**
 * Classe representant les appareils qui possedent un interrupteur. Cette classe
 * herite de AppareilAbstrait et complete les deux methodes manquantes de 
 * l'interface InterfaceAppareil
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */
public class AppareilInterrupteur extends AppareilAbstrait{

	/**
	 * Constructeur par copie d'attributs de l'appareil avec interrupteur
	 * 
	 * @param categorie categorie de l'appareil
	 * @param tension tension de l'appareil
	 * @param ampere amperage de l'appareil
	 * @param emplacement emplacement de l'appareil
	 */
	public AppareilInterrupteur(String categorie, double ampere, int tension, 
														   String emplacement) {
		
		super(categorie, ampere, tension, emplacement);
	}
	
	/**
	 * Methode indiquant si l'appareil possede un variateur
	 * 
	 * @return false, pcq cette classe est pour les appareils avec interrupteur
	 */
	public boolean aVariateur() {
		
		return false;
	}
	
	/**
	 * Methode indiquant la puissance consommee par l'appareil a interrupteur
	 * 
	 * @return le resultat de sa puissance
	 */
	public double getPuissanceInterne() {
			
		return super.getAmpere() * super.getTension();
	}
}
