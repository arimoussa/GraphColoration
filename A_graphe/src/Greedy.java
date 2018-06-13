import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Greedy {

	private ArrayList<Sommet> sortedVertices; // liste triée des sommets du graphe

	
	
	// Algorithme greedy
	public Greedy( Graphe g , int ordreTri )
	{
		
		if( g.getV() == 0 ) System.exit(0);

		Instant start = Instant.now();
		if( ordreTri > 2 && ordreTri <0 ) 
			{
				System.out.println("L'entier ordreTri doit etre compris entre 0 et 2:");
				System.out.println("0:Tri par ordre decroissant des degres des sommetes");
				System.out.println("1:Tri par ordre croissant des degres des sommetes");
				System.out.println("2:Tri aleatoires des degres des sommetes");
				System.exit(0);
			}
		
		
		sortedVertices = new ArrayList<Sommet>(g.getListeSommet() );
		String ordre="";
		// Tri de la liste de sommets en fonction de l'ordre
		if( ordreTri== 0 ) {
			Collections.sort(sortedVertices , Collections.reverseOrder() ); ordre = " decroissant" ;
		}
		else if(ordreTri == 1 ) {
			Collections.sort(sortedVertices , null ); ordre =" croissant ";
		}
		else	{
			Collections.shuffle(sortedVertices) ; ordre = " aleatoire " ;
		}
		
		
		int[] assignedColor = new int[g.getV() ];// tableau des couleurs assignées au sommet
		for( int i=0 ; i <  sortedVertices.size() ;i++)
		{
			assignedColor[ i ]  = 0 ;
		}
		
		//Parcours de tous les sommets de la liste triée et on assigne la plus petite couleur possible
		for( int i=0 ; i <  sortedVertices.size() ;i++)
		{
			assignedColor[ sortedVertices.get(i).getNumero()  ]  = assignColorToVertex (  sortedVertices.get(i)  , g , assignedColor ) ;
		}
		
		// Recherche de la couleur maximale
		int max= assignedColor[0] ;
		for(int i=1; i < assignedColor.length ;i++ )
		{
			if( assignedColor[i] > max   ) max= assignedColor[i] ;
		}
		
		/*
		for( int i=0 ; i <  g.getListeSommet().size() ;i++)
		{
			System.out.println("Sommet "+g.getListeSommet().get(i).getNumero() +" color:"+ assignedColor[ g.getListeSommet().get(i).getNumero() ] );
		 
		}
		*/
		
		//Calcul du temps d'execution
		Instant end = Instant.now();
		long time = Duration.between(start, end).toMillis() ;
		long nano = Duration.between(start, end).toNanos();
	
		// Affichage du resultat
		System.out.println("\n Greedy "+ordre +" : " + max +"\nTemps d'execution: "+ time+" mili sec "+nano+" nano sec");
		
	}
	
	/**
	 * Retourne la plus petite couleur possible pour un sommet 
	 * @param s le sommet à colorier
	 * @param g l graphe
	 * @param assignedColor le tableau de couleur des autres sommets
	 * @return
	 */
	public int assignColorToVertex( Sommet s , Graphe g , int[]  assignedColor )
	{
		ArrayList<Integer> colorSet = new ArrayList<Integer>();
		
		initializeColor(colorSet ,g.getV() ) ;

		for (Sommet adj : g.adjacentSommetOf(s) )
		{
			if( assignedColor[ adj.getNumero() ] != 0  )
			{	
				Integer a = new Integer( assignedColor[ adj.getNumero() ] ) ;
				boolean b = colorSet.remove( a )  ;
			}	
		}

		if ( colorSet.size() == 0 ) 
		{
			System.out.println("Dans assignColorToVertex color vide apres remove");
			System.exit(0);
		}
		return colorSet.get(0);	
	}
	
	
	// Initialisation de la liste de couleur disponible
	public void initializeColor( ArrayList<Integer> colorSet  , int size )
	{
		
		for (int i = 1; i< size ; i++ )
		{
			colorSet.add(i) ;
		}
	}
	
	
	
}