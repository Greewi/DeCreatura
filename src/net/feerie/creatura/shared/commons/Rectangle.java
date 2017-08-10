package net.feerie.creatura.shared.commons;

import com.google.gwt.user.client.Random;

/**
 * Repr�sente un rectangle (ou une zone rectangulaire)
 * 
 * @author greewi
 */
public class Rectangle
{
	public Position position;
	public Dimension dimension;
	
	/**
	 * Cr�� un rectangle depuis les coordonn�es de son coin haut-gauche et de
	 * ses dimensions
	 * 
	 * @param position la position du coin haut-gauche
	 * @param dimension les dimentions du rectangle
	 * @return le rectangle cr��
	 */
	public static Rectangle creeDepuisCoinHautGauche(Position position, Dimension dimension)
	{
		return new Rectangle(new Position(position), new Dimension(dimension));
	}
	
	/**
	 * Cr�� un rectangle centr� sur un point avec des dimensions pr�cis�es
	 * 
	 * @param centre le centre du rectangle
	 * @param dimension les dimensions du rectangle
	 * @return le rectangle cr��
	 */
	public static Rectangle creeDepuisCentre(Position centre, Dimension dimension)
	{
		return new Rectangle(new Position(centre.x - dimension.l / 2, centre.y - dimension.h / 2), new Dimension(dimension));
	}
	
	/**
	 * D�termine si le rectangle contient le point donn�
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
	 * G�n�re une position al�atoire contenue dans le rectangle
	 * 
	 * @return la position g�n�r�e
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
