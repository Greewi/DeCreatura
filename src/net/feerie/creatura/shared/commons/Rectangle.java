package net.feerie.creatura.shared.commons;

import com.google.gwt.user.client.Random;

/**
 * Représente un rectangle (ou une zone rectangulaire)
 * 
 * @author greewi
 */
public class Rectangle
{
	public Position position;
	public Dimension dimension;
	
	/**
	 * Créé un rectangle depuis les coordonnées de son coin haut-gauche et de
	 * ses dimensions
	 * 
	 * @param position la position du coin haut-gauche
	 * @param dimension les dimentions du rectangle
	 * @return le rectangle créé
	 */
	public static Rectangle creeDepuisCoinHautGauche(Position position, Dimension dimension)
	{
		return new Rectangle(new Position(position), new Dimension(dimension));
	}
	
	/**
	 * Créé un rectangle centré sur un point avec des dimensions précisées
	 * 
	 * @param centre le centre du rectangle
	 * @param dimension les dimensions du rectangle
	 * @return le rectangle créé
	 */
	public static Rectangle creeDepuisCentre(Position centre, Dimension dimension)
	{
		return new Rectangle(new Position(centre.x - dimension.l / 2, centre.y - dimension.h / 2), new Dimension(dimension));
	}
	
	/**
	 * Détermine si le rectangle contient le point donné
	 * 
	 * @param position la position dont on veut savoir si elle est contenue dans
	 *        le rectangle
	 * @return <tt>true</tt> si et seulement la position est contenue dans le
	 *         rectagle (bords compris)
	 */
	public boolean contient(Position position)
	{
		return position.x >= this.position.x && position.y >= this.position.y && position.x <= this.position.x + this.dimension.l && position.y <= this.position.y + this.dimension.h;
	}
	
	/**
	 * Génère une position aléatoire contenue dans le rectangle
	 * 
	 * @return la position générée
	 */
	public Position getPositionAleatoire()
	{
		return new Position(position.x + Random.nextDouble() * dimension.l, position.y + Random.nextDouble() * dimension.h);
	}
	
	private Rectangle(Position position, Dimension dimension)
	{
		this.position = position;
		this.dimension = dimension;
	}
	
}
