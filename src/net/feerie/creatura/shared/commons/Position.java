package net.feerie.creatura.shared.commons;

/**
 * Repr�sente une position
 * 
 * @author greewi
 */
public class Position
{
	public double x;
	public double y;
	
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
		this.y = position.y;
	}
	
	/**
	 * @param x la coordonnée x de la position
	 * @param y la coordonnée y de la position
	 */
	public Position(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Effectue une translation (ne modifie pas cette position)
	 * 
	 * @param v le vecteur de translation
	 * @return la position translatée
	 */
	public Position translate(Vecteur v)
	{
		return new Position(x + v.x, y + v.y);
	}
}
