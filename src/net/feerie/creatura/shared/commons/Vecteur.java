package net.feerie.creatura.shared.commons;

/**
 * Représente un vecteur
 * 
 * @author greewi
 */
public class Vecteur
{
	public int x;
	public int z;
	
	public Vecteur()
	{
	}
	
	/**
	 * @param x la coordonnée x du vecteur
	 * @param z la coordonnée z du vecteur
	 */
	public Vecteur(int x, int z)
	{
		this.x = x;
		this.z = z;
	}
	
	/**
	 * Construit le vecteur à partir d'une position de départ et une position de
	 * fin
	 * 
	 * @param debut la position de départ du vecteur
	 * @param fin la position de fin du vecteur
	 */
	public Vecteur(Position debut, Position fin)
	{
		this.x = fin.x - debut.x;
		this.z = fin.z - debut.z;
	}
	
	/**
	 * @return la norme au carree du vecteur
	 */
	public int getNormeCarree()
	{
		return x * x + z * z;
	}
	
	/**
	 * @return la norme du vecteur
	 */
	public double getNorme()
	{
		return Math.sqrt(x * x + z * z);
	}
	
	/**
	 * Multiplie ce vecteur par un nombre et le renvoie. Ne modifie pas ce
	 * vecteur.
	 * 
	 * @param facteur le nombre à déplacer
	 * @return le vecteur multiplié par le vecteur.
	 * @deprecated
	 */
	public Vecteur multiplie(double facteur)
	{
		return new Vecteur((int) (x * facteur), (int) (z * facteur));
	}
}
