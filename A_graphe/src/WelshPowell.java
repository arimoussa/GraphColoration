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
	

	private ArrayList<Sommet> sortedVertices;// Liste des sommets triés
	private int chromaticNumber; // nombre de couleur trouvé


	//Algorithme
	public WelshPowell( Graphe g , int ordreTri )
	{
		if( g.getV() == 0 ) System.exit(0);
		
		// Demarrage du timer
		Instant start = Instant.now();
		//Liste des sommets
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
		// Tri de la liste de sommets en fonction des ordres
		if( ordreTri== 0 ) {
			Collections.sort(sortedVertices , Collections.reverseOrder()  );ordre = " decroissant" ;
		}
		else if(ordreTri == 1 ) {
			Collections.sort(sortedVertices , null ); ordre =" croissant ";
		}
		else	{
			Collections.shuffle(sortedVertices) ; ordre = " aleatoire " ;
		}
		
		int k = 1; // Couleur à assigner à un sommet
		int [] color  = new int[ g.getV()]; // Tableau de couleur des sommets
		initializeColor( color ) ;
	
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
			// On parcours les autres sommets pour attibuer si possible la couleur courante k
			while( i < copy.size() )
			{
				
				int j=1;
				boolean finAdj = false;
				while( ! finAdj && j<=k) {
						finAdj = g.adjacentVerticesWithColor(y , g.getListeSommet() , color , j) ;
						// Si Y non adjacent à un sommet de couleur k alors on lui assigne la couleur k
						if ( finAdj   ) 
						{
							color[ y.getNumero() ]  = j ;
							copy.remove(y);
						}
						j++;
				}
				
				if(i < copy.size() ) y = copy.get(i) ;
				i++;
			}
			sortedVertices=copy;
			
			if( sortedVertices.size() > 0  ) k++;
		}
		//Calcul du temps d'execution
		Instant end = Instant.now();
		long time = Duration.between(start, end).toMillis() ;
		long nano = Duration.between(start, end).toNanos();
		
		//Affichage du resultat
		System.out.println("\n WelshPowell "+ordre +" : " + k +"\nTemps d'execution: "+ time+" mili sec "+nano+" nano sec");
	}
	

	/**
	 * Initialise le tableua color avec la valeur -1
	 * @param color
	 */
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
