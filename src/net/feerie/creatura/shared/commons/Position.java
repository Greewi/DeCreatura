package net.feerie.creatura.shared.commons;

/**
 * Repr�sente une position
 * 
 * @author greewi
 */
public class Position
{
	public int x;
	public int z;
	
	public Position()
	{
	}
	
	/**
	 * Constructeur par copie
	 * 
	 * @param position la position à copier
	 */
	public Position(Position position)
	{
		this.x = position.x;
		this.z = position.z;
	}
	
	/**
	 * @param x la coordonnée x de la position
	 * @param z la coordonnée z de la position
	 */
	public Position(int x, int z)
	{
		this.x = x;
		this.z = z;
	}
	
	/**
	 * Effectue une translation (ne modifie pas cette position)
	 * 
	 * @param v le vecteur de translation
	 * @return la position translatée
	 */
	public Position translate(Vecteur v)
	{
		return new Position(x + v.x, z + v.z);
	}
}
