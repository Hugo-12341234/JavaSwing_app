package liste;

import java.io.Serializable;

/**
 * Classe qui implémente une liste avec opérations à partir d'une position
 * explicitement fourni par l'utilisateur.
 * 
 * La position 0 signifie en tête de liste.
 * 
 * Seul les exceptions pour inséser et supprimer sont levées.  Les autres
 * erreurs recevront des exceptions systèmes.
 *
 * @author  Hugo Vaillant, Primika Khayargoli, Nilaxsan Tharmalingam
 * @version A2022
 */
public class Liste implements Serializable {

	/*
	 * STRATÉGIE : On garde deux variables pour retenir la position courante
	 * et une pour le nombre d'éléments qui est mise à jour après chaque
	 * insertion ou supression.
	 *
	 * L'implémentation est par chaînage dynamique simple. Il s'agit de 
	 * minimiser les déplacement lors d'insertion et de suppression.  On ne 
	 * veut pas repartir du début si la position fournie correspond au noeud
	 * suivant de la position courante.
	 *
	 */
	
	public static final int POS_LISTE_VIDE = -1;

	/* 
	 * La classe permettant le chaînage qui contient un élément et une
	 * référence vers le noeud suivant.
	 * 
	 * Seul un constructeur par copie d'attributs est disponible et les
	 * attributs ne sont pas encapsulés (avantage classe interne).
	 */
	private class Noeud implements Serializable {
		
		Object element;		
		Noeud suivant;
		
		Noeud(Object element, Noeud suivant){
			
			this.element = element;
			this.suivant = suivant;
		}
	}
	
	 // La référence sur le premier noeud de la liste.
	 private Noeud debut;

	 // La référence sur le dernier noeud de la liste.
	 private Noeud fin;

	 // La position où sont effectuées les opérations.
	 private Noeud positionCourante;
	 
	 // La place de la position courante en terme de position.  La valeur 0
	 // signifie en tête de liste.
	 private int pos;



	 // Maintenu à jour après une insertion ou une suppression.
	 private int nbElements;
	 

	 /**
	  * Crée une liste vide.
	  */
	 public Liste(){

		 initListe();
	 }

	 /*
	  * Methode initialisant la liste
	  */
	 private void initListe() {
		 
		 debut = null;
		 fin = null;
		 positionCourante = null;
		 pos = POS_LISTE_VIDE;
		 nbElements = 0;
	 }


	 /**
	  * Retourne si la liste est vide.
	  *
	  * Antécédent : Aucun.
	  * Conséquent : Aucun.
	  *
	  * @return true si la liste est vide et false autrement.
	  */
	 public boolean estVide(){

		 return (nbElements == 0);
	 }
	 
	 
	 /**
	  * Insère l'élément reçu. Tous les éléments subséquents sont déplacés 
	  * vers la droite d'une position.
      *
      * Antedecent : Aucun.
      *
	  * Conséquent : nbElement = nbElement + 1 &&
	  *                        liste.getElement() == element.
	  *
	  * @param element L'élément à insérer
	  * @param position La position de l'élment dans la liste après l'insertion.
	  * 
	  * @throws Si on insère dans une liste pleine (outOfMemoryError).
	  *
	  */
	 public void inserer(Object element, int position){
		 
		 // Insertion dans une liste vide ou si on veut inserer au debut
		 if (nbElements == 0 || pos == 0) {
			 
			 insererAuDebut(element);
		 }
		 else if(position >= nbElements) {
			 
			 insererALaFin(element);
		 }
		 else {
			 
			 insererAuMilieu(element, position);
		 }
		 
		 nbElements++;
	 }
	 
	 /**
	  * Methode inserant au debut
	  * 
	  * @param element Element que l'on veut inserer
	  */
	 private void insererAuDebut(Object element) {
		 
		 debut = new Noeud(element, debut);
		 
		 if (nbElements == 0) {
			 
			 fin = debut;
		 }
		 
		 positionCourante = debut;
		 pos = 0;
	 }
	 
	 /**
	  * Methode inserant a la fin
	  * @param element Element que l'on veut inserer
	  */
	 private void insererALaFin(Object element) {
		 
		 fin.suivant = new Noeud(element, null);
		 fin = fin.suivant;
		 
		 positionCourante = fin;
		 pos = nbElements;
	 }
	 
	 /**
	  * Methode inserant au milieu
	  * @param element Element que l'on veut inserer
	  * @param position Position a laquelle on veut inserer l'element
	  */
	 private void insererAuMilieu(Object element, int position) {
		 
		 placerPc(position);
		 
		 positionCourante.suivant = 
				 new Noeud(positionCourante.element, positionCourante.suivant);
		 
		 positionCourante.element = element;
	 }
	 
	 /**
	  * Methode pour positionner correctement la position courante
	  * @param position Position a laquelle on veut placer la position courante
	  */
	 private void placerPc(int position) {
		 
		 if (position > this.pos) {
			 
			 avancer(position - this.pos);
		 }
		 else if (position < this.pos) {
			 
			 positionCourante = debut;
			 pos = 0;
			 avancer(position);
		 }
	 }
	 
	 /**
	  * Methode permettant d'avancer dans la liste chainee
	  * @param distance Distance pour laquelle on avance dans la liste
	  */
	 private void avancer(int distance) {
		 
		 for (int i = 0; i < distance; i++) {
			 
			 positionCourante = positionCourante.suivant;
			 pos++;
		 }
	 }
	 
	 
	 /**
	 * Supprime l'élément à la position fournie.
	 *
	 * @throws Erreur si on supprime dans une liste vide.
	 */
	 public void supprimer(int position) throws Exception{

		 if (nbElements == 0) {
			 
			 throw new Exception("La liste est vide");
		 }
		 else {
			 
			 if (position == 0) {
				 
				 supprimerAuDebut();
			 }
			 else if (position >= nbElements) {
				 
				 supprimerALaFin();
			 }
			 else {
				 
				 supprimerAuMilieu(position);
			 }			 
		 
			 nbElements--;
		 }
	 }
	 
	 
	 /**
	  * Methode supprimant l'element au debut de la liste chainee
	  */
	 private void supprimerAuDebut() {
		 
		 debut = debut.suivant;
	 }
	 
	 /**
	  * Methode supprimant l'element a la fin de la liste chainee
	  */
	 private void supprimerALaFin() {
		 
		 placerPc(nbElements-2);
		 positionCourante.suivant = null;
		 fin = positionCourante;
	 }
	 
	 /**
	  * Methode supprimant un element a une certaine position de la liste
	  */
	 private void supprimerAuMilieu(int position) {
		 
		 placerPc(position - 1);
		 positionCourante.suivant = positionCourante.suivant.suivant;
	 }

	 //LES AUTRES MÉTHODES
	 /**
	  * Retourne l'élément à la position fournie.
	  *
	  * Antécédent : La liste ne doit pas être vide.
	  *
	  * Conséquent : Le contenu de la liste est inchangé.
	  *
	  * @return L'élément à la position demandée.
	  */
	 public Object getElement(int position){

		 Object element = null;
		 
		 if (!estVide()) {
			 
			 placerPc(position);
			 
			 element = positionCourante.element;
		 }
		 
		 return element;
	 }

	 /**
	  * Retourne le nombre d'éléments actuellement dans la liste.
	  *
	  * Antécédent : aucun.
	  * Conséquent : aucun.
	  *
	  * @return Le nombre d'éléments de la liste.
	  */
	 public int getNbElements(){

		 return nbElements;

	 }
}