import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graphe {

	
	private int v; // number of vertices
	private int e; // number of edges
	
	private ArrayList<Sommet>[] adjacent ;
	
	private ArrayList<Sommet> listeSommet;
	private ArrayList<Arete> listeArete;
	
	 public Graphe() {
		 
	 }
	public int getV() {
		return v;
	}


	public void setV(int v) {
		this.v = v;
	}


	public int getE() {
		return e;
	}


	public void setE(int e) {
		this.e = e;
	}


	public ArrayList<Sommet> getListeSommet() {
		return listeSommet;
	}


	public void setListeSommet(ArrayList<Sommet> listeSommet) {
		this.listeSommet = listeSommet;
	}


	public ArrayList<Arete> getListeArete() {
		return listeArete;
	}


	public void setListeArete(ArrayList<Arete> listeArete) {
		this.listeArete = listeArete;
	}
	
	/**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
	
	public void degreeSommet(Sommet s) {
		int degree=this.adjacentSommetOf(s).size();
        s.setDegree(degree);
        //return degree;
	}
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
						
						this.adjacent = new ArrayList[this.v];
						this.listeSommet = new ArrayList<Sommet>();
						//Creation des sommets
						
						for(int i=0; i< this.v ; i++ )
						{
							
							/*Sommet s = new Sommet(i, 0);
							degreeSommet(s);*/
							this.listeSommet.add( new Sommet(i, 0 ) ) ;
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
						
						//modifier
						//this.listeArete.add( new Arete(new Sommet(numS1,0), new Sommet (numS2,0) )) ;
						
						
						this.listeArete.add(new Arete(this.listeSommet.get(numS1), this.listeSommet.get(numS2)));
						
						//Mise a jour de la liste d'adjacence
						
						//modifier
					//	this.adjacent[numS1].add( new Sommet(numS2,0) );
						
						this.adjacent[numS1].add(this.listeSommet.get(numS2));
						
						
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
		
		if ( readingEdge ) {
			calculDegree();
			
			 
		}
		
		else System.out.println("Le format du fichier choisi est incorrect");
		
	}
	
	public void calculDegree() {
		
		for(int i=0; i<this.adjacent.length ; i++) {
			
			this.listeSommet.get(i).setDegree(this.adjacent[i].size());
			
		}
		
	}
	
	/**
	 * 
	 * Retourne tous les sommets adjacent à S
	 * @param s
	 * @return
	 */
	public ArrayList<Sommet> adjacentSommetOf( Sommet s )
	{	
		return (ArrayList<Sommet>)this.adjacent[s.getNumero()] ;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean isAdjacentTo( Sommet a , Sommet b )
	{
	
		if( a.getNumero() == b.getNumero() ) return true ;
		ArrayList<Sommet> succ = this.adjacentSommetOf(a);
		Iterator<Sommet> it  = succ.iterator() ;
		while( it.hasNext() )
		{
			if( it.next().getNumero() == b.getNumero() ) return true ;
		}
		return false;
	}
	
	/**
	 * Trouve si a adjacent à un sommet de couleur k
	 * @param a
	 * @param list
	 * @return
	 */
	
	public boolean adjacentVerticesWithColor( Sommet a , ArrayList<Sommet> list , int[] color , int k )
	{
		Iterator<Sommet> it = list.iterator() ;
		ArrayList<Integer> adj = new ArrayList<Integer>();
		int i=0;
		while( i <  list.size() )
		{
			if( this.isAdjacentTo(a, list.get(i)  ))
			{	
				if ( color[list.get(i).getNumero()]  == k ) {
			
					//System.out.print("Sommet  i: "+ a.getNumero() +"  Adjacent To: " +list.get(i).getNumero()  );
					//System.out.println(" Color: " +color[list.get(i).getNumero()] + " et k:"+k );
					
					return false ;
				}
			}

			i++;
		}
		
		return true  ;
	}
	
	public void validateVertex( Sommet s )
	{
		if( s.getNumero() < 0 || s.getNumero() >= this.v )
		{
			throw new IllegalArgumentException("Sommet " + s.getNumero()  + " n'est pas entre " + (this.v-1));
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
