import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Graphe {

	
	private int v; // number of vertices
	private int e; // number of edges
	//private ArrayList<Integer>[] adjacent;
	private List<Sommet>[] adjacent ;
	
	private List<Sommet> listeSommmet;
	private List<Arete> listeArete;
	
	 
	
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
		this.adjacent = new LinkedList[this.v];
		for(int i=0; i< vertices; i++ )
		{
			//this.adjacent[i] = new LinkedList<Integer>() ;
		}
	}
	
	
	public Graphe( BufferedReader buff) 
	{
		
		String line = null ;
		try {
				while ( ( line = buff.readLine() ) != null )
				{
					
					if( line.contains("NbSommets")   )
					{
						int indiceDdebut = line.indexOf(":");

						
					}
					
					
				}
			
			
			this.v = buff.read() ;
			if( this.v < 0  ) throw new IllegalArgumentException("number of vertices in a Grap must be nonnegative") ;
			this.adjacent = new LinkedList[this.v];
			for(int i=0 ; i < this.v ; i++ )
			{
				//this.adjacent[i] = new LinkedList<Integer>() ;
			}
			
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void calculDegree() {
		
		
		
	}
	
}
