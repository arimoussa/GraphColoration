import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Greedy {

	private ArrayList<Sommet> sortedVertices;
	private int chromaticNumber;
	
	public Greedy( Graphe g , int ordreTri )
	{
		
		if( g.getV() == 0 ) System.exit(0);

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
		// Tri de la liste de sommets
		if( ordreTri== 0 ) {
			Collections.sort(sortedVertices , Collections.reverseOrder() ); ordre = " decroissant" ;
		}
		else if(ordreTri == 1 ) {
			Collections.sort(sortedVertices , null ); ordre =" croissant ";
		}
		else	{
			Collections.shuffle(sortedVertices) ; ordre = " aleatoire " ;
		}
		
		
		int[] assignedColor = new int[g.getV() ];
		for( int i=0 ; i <  sortedVertices.size() ;i++)
		{
			
			assignedColor[ i ]  = 0 ;
		}
		
		
		for( int i=0 ; i <  sortedVertices.size() ;i++)
		{
			
			assignedColor[ sortedVertices.get(i).getNumero()  ]  = assignColorToVertex (  sortedVertices.get(i)  , g , assignedColor ) ;
			
			//if( i== 84 ) System.out.println("Boucle assign assignedColor: "+ assignedColor[i] );
		}
		
		int max= assignedColor[0] ;
		for(int i=1; i < assignedColor.length ;i++ )
		{
			//System.out.println("assicolor "+ "i:" + assignedColor[i] );
			if( assignedColor[i] > max   ) max= assignedColor[i] ;
		}

		
		System.out.println("\nGreedy Algorithme: Nombre Chromatique de g : " + max +" avec un tri des sommets par ordre "+ordre+" des degres \n");
		
	}
	
	/**
	 * 
	 * @param s
	 * @param g
	 * @param assignedColor
	 * @return
	 */
	public int assignColorToVertex( Sommet s , Graphe g , int[]  assignedColor )
	{
		ArrayList<Integer> colorRemove = new ArrayList<Integer>();
		ArrayList<Integer> colorSet = new ArrayList<Integer>();
		
		initializeColor(colorSet ,g.getV() ) ;
		
		//System.out.print ("a remove : ");
		for (Sommet adj : g.adjacentSommetOf(s) )
		{
			if( assignedColor[ adj.getNumero() ] != 0  )
			{
				//colorRemove.add( assignedColor[ adj.getNumero() ] ) ;
				
				Integer a = new Integer( assignedColor[ adj.getNumero() ] ) ;
				boolean b = colorSet.remove( a )  ;
			}	
		}
		
		//colorSet.sort(null);
		if ( colorSet.size() == 0 ) 
		{
			System.out.println("Dans assignColorToVertex color vide apres remove");
			System.exit(0);
		}
		
		//System.out.println("Color set trie :" +colorSet.toString());
		/*if( s.getNumero() == 84 )
		{
			System.out.println("* Methode Assign Couleur sommet "+ s.getNumero()+" : " + colorSet.get(0) );
		}*/
		//System.out.println("Couleur sommet "+ s.getNumero()+" : " + colorSet.get(0) );
		return colorSet.get(0);	
	}
	
	public void initializeColor( ArrayList<Integer> colorSet  , int size )
	{
		
		for (int i = 1; i< size ; i++ )
		{
			colorSet.add(i) ;
		}
	}
	
	
	
}