import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sommet implements Comparable<Sommet> {

	private int degree;
	private int numero;
	private int dSat;

	// coloration
	private int couleur;

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

	// getters et setters
	public int getDSat() {
		return dSat;
	}

	public void setDSat(int degree) {
		this.dSat = degree;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	@Override
	public int compareTo(Sommet arg0) {
		// TODO Auto-generated method stub
		return this.degree - arg0.degree;
	}

	public String toString() {

		return this.getNumero() + " ";
	}

}
