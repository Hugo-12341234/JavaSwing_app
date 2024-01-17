package hierarchieAppareils;

/**
 * Interface s'assurant que 3 methodes specifiques sont implementes dans la 
 * hierarchie des appareils
 * 
 * @author Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 */
public interface InterfaceAppareil {

	// methode booleenne servant a savoir si l'appareil a un variateur
	public boolean aVariateur();
	
	// accesseur de la puissance de l'appareil
	public double getPuissance();
	
	// methode booleenne servant a savoir si l'appareil est allume
	public boolean estAllume();
}
