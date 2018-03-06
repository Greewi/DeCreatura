package net.feerie.creatura.shared.commons;

/**
 * Représente des dimensions
 * 
 * @author greewi
 */
public class Dimension
{
	public double l;
	public double h;
	
	public Dimension()
	{
		
	}
	
	/**
	 * @param l la largeur
	 * @param h la hauteur
	 */
	public Dimension(double l, double h)
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
