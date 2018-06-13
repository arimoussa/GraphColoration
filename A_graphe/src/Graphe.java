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

	
	private int v; // nombre de sommets
	private int e; // nombre d'aretes
	
	private ArrayList<Sommet>[] adjacent ;// liste d'adjacence
	
	private ArrayList<Sommet> listeSommet;// liste des sommets
	private ArrayList<Arete> listeArete;// liste des aretes
	
	
	 public Graphe() {
		 
	 }
	
	// Getter et Setter 
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
	 * Calcule le degre d'un sommet et assigne cette valeur au degre du sommet
	 * @param s le sommet
	 */
	public void degreeSommet(Sommet s) {
		int degree=this.adjacentSommetOf(s).size();
        s.setDegree(degree);
        //return degree;
	}
	
	/**
	 * Lit un fichier de configuration de graphe et cree un graphe avec cette configuration
	 * @param buff bufferedReader
	 */
	public Graphe( BufferedReader buff) 
	{
		
		String line = null ; // Ligne courante
		boolean readingEdge=false; // Pour savoir si on lit une arete
		boolean boolColor = false;// Pour savoir si on lit une couleur
		try {
				while ( ( line = buff.readLine() ) != null )
				{
					
					if( line.contains("NbSommets")   ) // si la ligne contient le mot NbSommets
					{
						int indiceDebut = line.indexOf(":");
						this.v = Integer.parseInt( line.substring(indiceDebut+2).trim() );
						
						if( this.v < 0  ) throw new IllegalArgumentException("number of vertices in a Grap must be nonnegative") ;
						
						this.adjacent = new ArrayList[this.v];
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
						this.e = Integer.parseInt( line.substring(indiceDebut+2) );
						if( this.e < 0  ) throw new IllegalArgumentException("number of edges in a Grap must be nonnegative") ;
						this.listeArete = new ArrayList<Arete>();
					}
					// Ajout des aretes et de la liste d'adjacence
					if( readingEdge )
					{
						String[] data = line.split(" ");
						if( data.length != 2 ) throw new IllegalArgumentException("Une arete n'est pas bien ecrit ") ;
						int numS1 = Integer.parseInt(data[0]);
						int numS2 = Integer.parseInt(data[1]);
						this.listeArete.add(new Arete(this.listeSommet.get(numS1), this.listeSommet.get(numS2)));
						
						//Mise a jour de la liste d'adjacence
						this.adjacent[numS1].add(this.listeSommet.get(numS2));
						
						
					}
					if( ( line.contains("aretes" ) ) )
					{
						readingEdge = true;
						boolColor = false;
					}
					if (boolColor) {
						String[] data = line.split(" ");
						System.out.println("data split :" + Arrays.toString( data ));
						System.out.println("Length : "  + data.length);
						if( data.length != 2 ) throw new IllegalArgumentException("une couleur n'est pas écrite ") ;
						int numS = Integer.parseInt(data[0]);
						int color = Integer.parseInt(data[1]);
						
						this.listeSommet.get(numS).setCouleur(color);
					}
					if(line.contains("Couleur")) {
						
						boolColor = true;
						
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
	
	/**
	 * Calcule le degre d'un sommet
	 */
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
	 * Teste si deux sommets sont adjacents
	 * @param a le 1er sommet
	 * @param b le 2eme sommet
	 * @return True/False
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
	 * Verifie si le sommet a est adjacent à un sommet de couleur k
	 * @param a le sommet 
	 * @param list la liste des sommets
	 * @return
	 */
	/**
	 * Verifie si le sommet a est adjacent à un sommet de couleur k
	 * @param a		le sommet 
	 * @param list la liste des sommets
	 * @param color le tableau de couleur des sommets
	 * @param k la couleur k a chercher
	 * @return 	t	True si y n'es pas adjacent à un sommet de couleur k
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
	
	/**
	 * Teste si le sommet s à un numero valide
	 * @param s le sommet s
	 */
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
			s+= this.listeSommet.get(i).getNumero() + " deg= "+  this.listeSommet.get(i).getDegree()+"\n";
			
		}
		
		return s;
	}
	
		

	/*public String toString()
	{
		String s = "NbSommets :"+this.v+"\n"+ "NbArcs: "+this.e+"\n"+"Liste des sommets:\n";
		
		for(int i=0 ; i<this.v; i++ )
		{
			s+= this.listeSommet.get(i).getNumero() + " deg= "+  this.listeSommet.get(i).getDegree()  +" couleur ="+ this.listeSommet.get(i).getCouleur()+ "\n";
			
		}
		
	s+="Liste des aretes: \n";
		for(int i=0 ; i<this.e ; i++ )
		{
			s+= this.listeArete.get(i).getSommet1().getNumero() +"---"+ this.listeArete.get(i).getSommet2().getNumero()  +" \n";
			
		}
		
		return s;
	}*/

	
}
