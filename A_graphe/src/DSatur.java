import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;



public class DSatur extends Graphe implements Comparator<Sommet>{
	

	private static ArrayList<Sommet> sommetsNonColories;
	private Graphe g;
	
	
	/**
	 * calcul du degre de saturation de chaque sommet
	 * @param s : le sommet
	 * @return
	 * si aucun voisin de s est colori�, alors degreSat(x)
	 * renvoie le degre de s
	 * sinon, degreSat(s), le nombre de couleurs differentes 
	 * utilis�es dans le voisinage de s
	 */
	public int degreSat(Sommet s){
        if(s==null) return -1;
        
        // r�cuperation des sommets en relation 
        ArrayList<Sommet> sommetRelation= g.adjacentSommetOf(s);
        
        // pour s'assurer de ne pas avoir de doublons, utilisation hashSet
        HashSet<Integer> nbCoulours=new HashSet<Integer>();
        
        for(int i=0; i<sommetRelation.size();i++){
            Sommet sNext =sommetRelation.get(i);
            if(s.getCouleur()!=0) nbCoulours.add(sNext.getCouleur());
        }
        int degree = sommetRelation.size();
        if(nbCoulours.size()==0) return degree ;
        else return nbCoulours.size();
    }
	
	
	/*private boolean recherche(ArrayList<Sommet> listeSommet, int couleur){
       int i =0;
       
        while(i<listeSommet.size()) {
            if(listeSommet.get(i).getCouleur()==couleur)
                return false;
            i++;
        }
        return true;
    }
	*/
	
	private boolean rC(ArrayList<Sommet> c, int x){
        Iterator<Sommet> it=c.iterator();
        while(it.hasNext())
            if(it.next().getCouleur()==x)
                return false;
        return true;
    }
	
	public int plusPetiteCouleur(ArrayList<Sommet> listeSommetVoisins) {
		int couleur=1;
		while(true) {
			if(rC(listeSommetVoisins, couleur)) {
				return couleur;
			}
			couleur++;
		}
	}
	
	public DSatur(Graphe g) {
		// TODO Auto-generated constructor stub
		
		this.g = g;
		// trier par ordre decroissant de degr�s 
		Collections.sort(g.getListeSommet(), Collections.reverseOrder()); 
		
	

		// recuperation de tous les sommets du graphe
		sommetsNonColories = new ArrayList<Sommet>();
		sommetsNonColories.addAll(g.getListeSommet()); 
	
		// coloration du sommet avec le degree max avec la couleur 1
		Sommet s = sommetsNonColories.get(0);
		s.setCouleur(1);
		System.out.println("get 0 "+s.getNumero());
		int nbChromatique = 1;
		sommetsNonColories.remove(0);
		
		Collections.sort(sommetsNonColories, Collections.reverseOrder()); 
		for(int k=0; k<sommetsNonColories.size();k++) {
			System.out.println("sommet " + sommetsNonColories.get(k).getNumero());
		}
		
		while(sommetsNonColories.size()>0) {
			Sommet sNew =sommetsNonColories.get(0);
			
			ArrayList<Sommet> sommetRelation = g.adjacentSommetOf(sNew);
			
			int plusPetiteCouleur = plusPetiteCouleur(sommetRelation);
			
			if(plusPetiteCouleur > nbChromatique ) nbChromatique = plusPetiteCouleur;
			sNew.setCouleur(plusPetiteCouleur);
			sommetsNonColories.remove(0);
            Collections.sort(sommetsNonColories, Collections.reverseOrder());
		}
		
		System.out.println("Nombre Chromatique du graphe: " + nbChromatique);
	}
	@Override
	public int compare(Sommet o1, Sommet o2) {
		// TODO Auto-generated method stub
		if(degreSat(o2)-degreSat(o1)!=0)
            return degreSat(o2)-degreSat(o1);
        else {

            int degreeO1 = g.adjacentSommetOf(o1).size();
            int degreeO2 = g.adjacentSommetOf(o2).size();
        	return degreeO2-degreeO1;
        }
	}

}
