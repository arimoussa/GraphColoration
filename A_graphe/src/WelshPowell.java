import java.awt.List;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class WelshPowell {
	
	
	private Graphe g ;
	private ArrayList<Sommet> sortedVertices;
	private int chromaticNumber;


	public WelshPowell( Graphe g , int ordreTri )
	{
		if( g.getV() == 0 ) System.exit(0);
		
		Instant start = Instant.now();
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
			//Collections.sort(sortedVertices , Collections.reverseOrder()  );
			
			sortedVertices.sort( new Comparator<Sommet>() {

				@Override
				public int compare(Sommet o1, Sommet o2) {
					
					return  o2.getDegree()-o1.getDegree();
				}
				
				
			});
			ordre = " decroissant" ;
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
		
		/*System.out.println( "Sommet "+ sortedVertices.get(0)+"Max deg : " + sortedVertices.get(0).getDegree() );
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
				
				int j=1;
				boolean finAdj = false;
				while( ! finAdj && j<=k) {
						finAdj = g.adjacentVerticesWithColor(y , g.getListeSommet() , color , j) ;
						// Y non adjacent à un sommet de couleur k
						if ( finAdj   ) 
						{
							color[ y.getNumero() ]  = j;
							copy.remove(y);
						}
						j++;
				}
				
				if(i < copy.size() ) y = copy.get(i) ;
				i++;
			}
			sortedVertices=copy;
			
			if( sortedVertices.size() > 0  ) k++;
				//k++;
		}
		
		/*for( int i=0 ; i <  g.getListeSommet().size() ;i++)
		{
			System.out.println("Sommet "+g.getListeSommet().get(i).getNumero() +" color:"+ color[ g.getListeSommet().get(i).getNumero() ] );
		 
		}*/
		
		Instant end = Instant.now();
		long time = Duration.between(start, end).toMillis() ;
		long nano = Duration.between(start, end).toNanos();
		System.out.println("\n WelshPowell "+ordre +":" + k +" Temps d'execution: "+ time+" mili sec "+nano+" nano sec");
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
