import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Graphe {

	
	private int v; // number of vertices
	private int e; // number of edges
	//private ArrayList<Integer>[] adjacent;
	private List<Sommet>[] adjacent ;
	
	private ArrayList<Sommet> listeSommet;
	private ArrayList<Arete> listeArete;
	
	 
	
	/**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
	public Graphe( int vertices )
	{
		if( vertices <0 ) throw new IllegalArgumentException("Number of vertices must be nonnegative ");
		//this.adjacent = ( Bag<Integer>[] )new Bag[this.v];
		
		
		this.v = vertices ;
		this.e = 0;
		this.adjacent = new ArrayList[this.v];
		for(int i=0; i< vertices; i++ )
		{
			//this.adjacent[i] = new LinkedList<Integer>() ;
		}
	}
	
	
	public Graphe( BufferedReader buff) 
	{
		
		String line = null ;
		boolean readingEdge=false;
		try {
				while ( ( line = buff.readLine() ) != null )
				{
					
					if( line.contains("NbSommets")   )
					{
						int indiceDebut = line.indexOf(":");
						//System.out.println(" Line : " + line.substring(indiceDebut+2).trim()  );
						this.v = Integer.parseInt( line.substring(indiceDebut+2).trim() );
						
						//System.out.println("Nb sommet lu : "  + this.v );
						if( this.v < 0  ) throw new IllegalArgumentException("number of vertices in a Grap must be nonnegative") ;
						
						this.adjacent = new List[this.v];
						this.listeSommet = new ArrayList<Sommet>();
						//Creation des sommets
						
						for(int i=0; i< this.v ; i++ )
						{
							this.listeSommet.add( new Sommet(i) ) ;
						}
						// Initialisation de la liste d'adjacence
						for(int i=0 ; i < this.v ; i++ )
						{
							this.adjacent[i] = new ArrayList<Sommet>() ;
						}
						
					}
					//Initialisation du nombre d'aretes
					if( ( line.contains("NbArcs" ) ) )
					{
						int indiceDebut = line.indexOf(":");
						//System.out.println(" ligne NB arcs: " + this.e);
						this.e = Integer.parseInt( line.substring(indiceDebut+2) );
						//System.out.println("NB arcs lu : " + this.e);
						if( this.e < 0  ) throw new IllegalArgumentException("number of edges in a Grap must be nonnegative") ;
						this.listeArete = new ArrayList<Arete>();
					}
					// Ajout des aretes et de la liste d'adjacence
					if( readingEdge )
					{
						String[] data = line.split(" ");
						//System.out.println("data split :" + Arrays.toString( data ));
						//System.out.println("Length : "  + data.length);
						if( data.length != 2 ) throw new IllegalArgumentException("Une arete n'est pas bien ecrit ") ;
						int numS1 = Integer.parseInt(data[0]);
						int numS2 = Integer.parseInt(data[1]);
						this.listeArete.add( new Arete(new Sommet(numS1), new Sommet (numS2) )) ;
						
						//Mise a jour de la liste d'adjacence
						this.adjacent[numS1].add( new Sommet(numS2) );
						
					}
					//Lecture d'aretes
					if( ( line.contains("aretes" ) ) )
					{
						readingEdge = true;
						
					}
					
				}
				
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.calculDegree();
	}
	
	public void calculDegree() {
		
		for(int i=0; i<this.adjacent.length ; i++) {
			
			this.listeSommet.get(i).setDegree(this.adjacent[i].size());
			
		}
		
	}
	
	public String toString()
	{
		String s = "NbSommets :"+this.v+"\n"+ "NbArcs: "+this.e+"\n"+"Liste des sommets:\n";
		
		for(int i=0 ; i<this.v; i++ )
		{
			s+= this.listeSommet.get(i).getNumero() + " deg= "+  this.listeSommet.get(i).getDegree()   +" \n";
			
		}
		
		s+="Liste des aretes: \n";
		for(int i=0 ; i<this.e ; i++ )
		{
			s+= this.listeArete.get(i).getSommet1().getNumero() +"---"+ this.listeArete.get(i).getSommet2().getNumero()  +" \n";
			
		}
		
		return s;
	}
	
	
	
	
	
}
