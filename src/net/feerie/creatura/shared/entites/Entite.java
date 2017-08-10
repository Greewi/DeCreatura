package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;

public abstract class Entite
{
	protected final Monde monde;
	private final int ID;
	private Position position;
	private Dimension taille;
	private boolean estDetruit = false;
	
	/**
	 * Constructeur
	 * 
	 * @param monde le monde auquel appartient l'entit�
	 * @param x la position x de l'entit�
	 * @param y la position y de l'entit�
	 * @param l la longueur de l'entit�
	 * @param h la hauteur de l'entit�
	 */
	public Entite(Monde monde, Position position, Dimension taille)
	{
		this.monde = monde;
		this.ID = monde.genereIDEntite();
		this.position = position;
		this.taille = taille;
		this.estDetruit = false;
	}
	
	/**
	 * @return l'ID de cette entit�
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * @return la position de cette entit�
	 */
	public Position getPosition()
	{
		return new Position(position);
	}
	
	/**
	 * modifie la position de cette entit�
	 * 
	 * @param position la nouvelle position
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	/**
	 * @return la taille de cette entit�
	 */
	public Dimension getTaille()
	{
		return new Dimension(taille);
	}
	
	/**
	 * @return le type de cette entit� (voir {@link TypeEntite})
	 */
	public abstract TypeEntite getType();
	
	/**
	 * D�truit l'entit�. Appell�e par {@link Monde} !
	 */
	public void detruit()
	{
		estDetruit = true;
	}
	
	/**
	 * D�termie si cette entit� existe encore
	 * 
	 * @return <tt>true</tt> si et seulement si cette entit� existe encore
	 */
	public boolean existe()
	{
		return !estDetruit;
	}
	
	/**
	 * Met � jour l'entit�
	 * 
	 * @param frame le num�ro de la frame actuelle
	 */
	public abstract void metAJour(int frame);
}
