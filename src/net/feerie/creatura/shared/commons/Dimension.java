package net.feerie.creatura.shared.commons;

/**
 * Représente des dimensions
 * 
 * @author greewi
 */
public class Dimension
{
	public int l;
	public int h;
	
	public Dimension()
	{
		
	}
	
	/**
	 * @param l la largeur
	 * @param h la hauteur
	 */
	public Dimension(int l, int h)
	{
		this.l = l;
		this.h = h;
	}
	
	/**
	 * Constructeur par copie
	 * 
	 * @param dimension la Dimention à copier
	 */
	public Dimension(Dimension dimension)
	{
		this.l = dimension.l;
		this.h = dimension.h;
	}
}
