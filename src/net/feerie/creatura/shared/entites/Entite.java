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
	 * Calcule la distance carrée avec une autre entité
	 * 
	 * @param entite l'entité avec laquelle calculer la distance carrée
	 * @return la distance au carrée entre les deux entité
	 */
	public double getDistanceCarre(Entite entite)
	{
		Position p1 = entite.getPosition();
		Position p2 = getPosition();
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
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
	 * Met à jour l'entit�
	 * 
	 * @param frame le numéro de la frame actuelle
	 */
	public abstract void metAJour(int frame);
}
