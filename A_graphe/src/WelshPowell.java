import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class WelshPowell {
	
	
	private Graphe g ;
	private ArrayList<Sommet> sortedVertices;
	private int chromaticNumber;


	public WelshPowell( Graphe g , int ordreTri )
	{
		if( g.getV() == 0 ) System.exit(0);
		
		sortedVertices = new ArrayList<Sommet>(g.getListeSommet() );
		String ordre="";
		if( ordreTri > 2 && ordreTri <0 ) 
			{
				System.out.println("L'entier ordreTri doit etre compris entre 0 et 2:");
				System.out.println("0:Tri par ordre decroissant des degres des sommetes");
				System.out.println("1:Tri par ordre croissant des degres des sommetes");
				System.out.println("2:Tri aleatoires des degres des sommetes");
				System.exit(0);
			}
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
		
		int k = 1;
		int [] color  = new int[ g.getV()];
		initializeColor( color ) ;
		
		/*System.out.println( "Sommet "+ sortedVertices.get(0) +"Max deg : " + sortedVertices.get(0).getDegree() );
		System.out.println( "Sommet "+ sortedVertices.get( sortedVertices.size()  - 1 ) +"Min deg : " + sortedVertices.get( sortedVertices.size()  - 1 ).getDegree() );
		System.out.println(" Sorted size : " + sortedVertices.size() );
		System.out.println(" Color size : " + color.length ) ;*/
		
		Sommet y = null;
		while ( sortedVertices.size() > 0 )
		{
			
			Sommet x = sortedVertices.get(0);
			
			color[ x.getNumero() ] = k ;
			//System.out.println("Wesh sommet i: "+ x.getNumero() + " deg = "+x.getDegree() + " color : "+ color[ x.getNumero() ] );
			
			sortedVertices.remove( x ) ;
			
			if ( sortedVertices.size() > 0 ) y = sortedVertices.get(0) ;
			int i=0;
			
			ArrayList<Sommet> copy = new ArrayList<Sommet>(sortedVertices );
			// On parcours les autres sommets pour attibuer la couleu ailleurs
			while( i < copy.size() )
			{
				
				boolean finAdj = g.adjacentVerticesWithColor(y , g.getListeSommet() , color , k) ;
				// Y non adjacent à un sommet de couleur k
				if ( finAdj   ) 
				{
					color[ y.getNumero() ]  = k ;
					copy.remove(y);
				}
				
		
				 if( i < copy.size() ) y = copy.get(i) ;
				i++;
			}
			sortedVertices=copy;
			
			k++;
		}
		
		System.out.println("\nNombre Chromatique de g : " + k +" avec un tri des sommets par ordre "+ordre+" des degres \n");
	}
	

	public void initializeColor( int []color)
	{
		for(int i=0; i<color.length ; i++ )
		{
			color[i]= -1;
		}
	}

	public int getChromaticNumber() {
		return chromaticNumber;
	}


	public void setChromaticNumber(int chromaticNumber) {
		this.chromaticNumber = chromaticNumber;
	}

	
	

}
