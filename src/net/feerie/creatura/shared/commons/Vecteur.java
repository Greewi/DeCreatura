package net.feerie.creatura.shared.commons;

/**
 * Repr�sente un vecteur
 * 
 * @author greewi
 */
public class Vecteur
{
	public double x;
	public double y;
	
	public Vecteur()
	{
	}
	
	/**
	 * @param x la coordonn�e x du vecteur
	 * @param y la coordonn�e y du vecteur
	 */
	public Vecteur(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construit le vecteur � partir d'une position de d�part et une position de
	 * fin
	 * 
	 * @param debut la position de d�part du vecteur
	 * @param fin la position de fin du vecteur
	 */
	public Vecteur(Position debut, Position fin)
	{
		this.x = fin.x - debut.x;
		this.y = fin.y - debut.y;
	}
	
	/**
	 * @return la norme du vecteur
	 */
	public double getNorme()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Multiplie ce vecteur par un nombre et le renvoie. Ne modifie pas ce
	 * vecteur.
	 * 
	 * @param facteur le nombre � d�placer
	 * @return le vecteur multipli� par le fecteur.
	 */
	public Vecteur multiplie(double facteur)
	{
		return new Vecteur(x * facteur, y * facteur);
	}
}
