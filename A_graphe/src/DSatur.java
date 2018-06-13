import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

public class DSatur implements Comparator<Sommet> {

	private static ArrayList<Sommet> sommetsNonColories;
	private Graphe g;

	/**
	 * calcul du degre de saturation d'un sommet
	 * @param s: le sommet
	 * @return si aucun voisin de s est colorié, alors degreSat(x) renvoie le degre
	 *         de s sinon, degreSat(s), le nombre de couleurs differentes utilisées
	 *         dans le voisinage de s
	 */
	public int degreSat(Sommet s) {
		if (s == null)
			return -1;

		// récuperation des sommets en relation
		ArrayList<Sommet> sommetRelation = g.adjacentSommetOf(s);

		// pour s'assurer de ne pas avoir de doublons, utilisation hashSet
		HashSet<Integer> nbCoulours = new HashSet<Integer>();

		for (int i = 0; i < sommetRelation.size(); i++) {
			Sommet sNext = sommetRelation.get(i);
			if (sNext.getCouleur() != 0)
				nbCoulours.add(sNext.getCouleur());
		}

		if (nbCoulours.size() == 0) {
			
			int degree = s.getDegree();
			//s.setDegree(degree);
			s.setDSat(degree);
			return degree;
		}

		else {
			s.setDSat(nbCoulours.size());
			return nbCoulours.size();
		}

	}

	/***
	 * recherche si un sommet voisin a la même couleur que le sommet en question
	 * utile dans la recherche de plus petite couleur à attribuer 
	 * @param listeSommet
	 * @param couleur
	 * @return vrai si la couleur n'est pas attibuée à un voisin
	 * 			faux si la couleur est déjà prise par un voisin
	 */
	private boolean recherche(ArrayList<Sommet> listeSommet, int couleur) {
		int i = 0;

		while (i < listeSommet.size()) {
			if (listeSommet.get(i).getCouleur() == couleur) {
				return false;
			}
			i++;
		}
		return true;
	}

	/**
	 * Recherche de la plus petite couleur à attribuer en fonction des voisins
	 * @param listeSommetVoisins
	 * @return la plus petite couleur 
	 */
	public int plusPetiteCouleur(ArrayList<Sommet> listeSommetVoisins) {
		int couleur = 1;
		while (true) {
			if (recherche(listeSommetVoisins, couleur)) {
				return couleur;
			}
			couleur++;
		}
	}

	/**
	 * Calcul des degrés saturés de tous les sommets
	 * Maj nécessaire dans l'algo
	 * @param sommetsNonColorier
	 */
	public void calculDSat(ArrayList<Sommet> sommetsNonColorier) {
		for (int i = 0; i <= sommetsNonColorier.size() - 1; i++) {
			sommetsNonColorier.get(i).setDSat(degreSat(sommetsNonColorier.get(i)));
		}
	}

	/**
	 * Algorithme DSatur
	 * @param g : le graphe à colorier
	 */

	public DSatur(Graphe g) {
		// TODO Auto-generated constructor stub
		super();
		// début timer pour calcul du temps d'exécution
		Instant start = Instant.now();	
		this.g = g;
		
		// initialisation de tous les degrés saturés des sommets
		calculDSat(g.getListeSommet());
		// 1er temps : trier par ordre decroissant de degrés
		g.getListeSommet().sort(Comparator.comparing(Sommet::getDegree).reversed());

		// recuperation de tous les sommets du graphe
		sommetsNonColories = new ArrayList<Sommet>();
		sommetsNonColories.addAll(g.getListeSommet());

		// coloration du sommet avec le degree max avec la couleur 1
		Sommet s = sommetsNonColories.get(0);
		s.setCouleur(1);
		// compteur de couleur attribué
		int nbCouleurAttribuee = 1;
		
		// une fois le sommet colorié, on le retire du graphe
		sommetsNonColories.remove(0);
		
		// maj des degrés saturés 
		calculDSat(sommetsNonColories);

		//tri décroissant
        Collections.sort(sommetsNonColories, this);
        
        // tant qu'on a des sommets à colorier, on boucle
		while (sommetsNonColories.size() > 0) {
			Sommet sNew = sommetsNonColories.get(0);

			ArrayList<Sommet> sommetRelation = g.adjacentSommetOf(sNew);

			int plusPetiteCouleur = plusPetiteCouleur(sommetRelation);

			if (plusPetiteCouleur > nbCouleurAttribuee)
				nbCouleurAttribuee = plusPetiteCouleur;
			sNew.setCouleur(plusPetiteCouleur);
			sommetsNonColories.remove(0);

			calculDSat(sommetsNonColories);
			
			sommetsNonColories.sort(Comparator.comparing(Sommet::getDSat).reversed());

		}

		Instant end  = Instant.now();
		long time = Duration.between(start, end).toMillis() ;	
		System.out.println("\n Dsatur :" + nbCouleurAttribuee +"  Temps d'execution: "+ time+" mili sec ");//+nano+" nano sec");
		
		
	}

	@Override
	public int compare(Sommet o1, Sommet o2) {
		
		if (o2.getDSat() < o1.getDSat())
			return -1;
		else if (o1.getDSat() < o2.getDSat())
			return 1;
		else {
			return -1 * Integer.compare(o1.getDegree(), o2.getDegree());
		}
	}

}
