import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





public class Sommet implements Comparable<Sommet>{

	private int degree;
	private int numero;

	
	
	public Sommet(int numero) {
		// TODO Auto-generated constructor stub
		this.numero = numero;	
	}

	// getters et setters
	public int getDegree() {
		return degree;
	}


	public void setDegree(int degree) {
		this.degree = degree;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public int compareTo(Sommet arg0) {
		// TODO Auto-generated method stub
		return this.degree - arg0.degree ;
	}
	

	public String toString()
	{
		
		return this.getNumero()+" ";
	}
	/*
	public boolean adjacentTo( Sommet s ,ArrayList<Sommet>, ArrayList<Arete> a )
	{
		
		Iterator<Arete> it = a.iterator();
		Iterator<Arete> itSommet = a.iterator();
		
		while( itSommet.hasNext() )
		{
			
			
		}
		
		while( it.hasNext() )
		{
			
			if( it.next().adjacent(s, s) )
			{
				
			}
			
			
		}
		
		
		
		return false;
	}
	*/
	
}
