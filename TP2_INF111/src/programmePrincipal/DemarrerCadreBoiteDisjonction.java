package programmePrincipal;

import graphiqueGui.CadreBoiteDisjonction;
import javax.swing.SwingUtilities;

/**
 *  Démarre l'application de simulation de boites de disjonction intelligente
 * dans un environnement GUI à l’aide de Swing.
 *  
 *  Dans le cadre du cours inf111
 *  
 * @author Pierre Bélisle
 *
 * @autres auteurs :  Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam 
 *
 * @version Copyright H2023
 */
public class DemarrerCadreBoiteDisjonction {

    public static void main(String args[]) {
    	   	    	    	     	
  /*
      Rappel : On démarre l'application dans un processus séparé du main() 
      pour éviter des conflits de gestion d'événements avec le OS et respecter 
      la règle d’utilisation de l’EDT.

      Écrivez l’appel à invokeLater de la classe SwingUtilities avec une instance de 
      CadreBoiteDisjonction et ajoutez cette classe au projet.  C’est votre nouveau  
      programme principal.
   */
    	
    	CadreBoiteDisjonction cadre = new CadreBoiteDisjonction();
    	SwingUtilities.invokeLater(cadre);
    }
}