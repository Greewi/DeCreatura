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
	 * @param monde le monde auquel appartient l'entité
	 * @param x la position x de l'entité
	 * @param y la position y de l'entité
	 * @param l la longueur de l'entité
	 * @param h la hauteur de l'entité
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
	 * @return l'ID de cette entité
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * @return la position de cette entité
	 */
	public Position getPosition()
	{
		return new Position(position);
	}
	
	/**
	 * modifie la position de cette entité
	 * 
	 * @param position la nouvelle position
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	/**
	 * @return la taille de cette entité
	 */
	public Dimension getTaille()
	{
		return new Dimension(taille);
	}
	
	/**
	 * @return le type de cette entité (voir {@link TypeEntite})
	 */
	public abstract TypeEntite getType();
	
	/**
	 * Détruit l'entité. Appellée par {@link Monde} !
	 */
	public void detruit()
	{
		estDetruit = true;
	}
	
	/**
	 * Détermie si cette entité existe encore
	 * 
	 * @return <tt>true</tt> si et seulement si cette entité existe encore
	 */
	public boolean existe()
	{
		return !estDetruit;
	}
	
	/**
	 * Met à jour l'entité
	 * 
	 * @param frame le numéro de la frame actuelle
	 */
	public abstract void metAJour(int frame);
}
