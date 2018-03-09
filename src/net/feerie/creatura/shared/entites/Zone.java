package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Environnement;
import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Rectangle;

/**
 * Représente une zone (un endroit, une salle) dans le monde (étend
 * creatura.Entite)
 * 
 * @author greewi
 */
public class Zone extends Entite
{
	private Rectangle geometrie;
	private Environnement environnement;
	
	/**
	 * @param monde le monde de l'entité
	 * @param position la position de la zone (centre de la zone)
	 * @param taille la taille de la zone
	 * @param environnement l'environnement de la zone
	 */
	public Zone(Monde monde, Position position, Dimension taille, Environnement environnement)
	{
		super(monde, position, taille);
		this.geometrie = Rectangle.creeDepuisCentre(position, taille);
		this.environnement = environnement;
	}
	
	/**
	 * Détermine si la zone contient la position donnée
	 * 
	 * @param position la position dont on veut savoir si elle est contenue dans
	 *        la zone
	 * @return <tt>true</tt> si et seulement si la zone contient la position
	 *         demand�e
	 */
	public boolean contient(Position position)
	{
		return geometrie.contient(position);
	}
	
	/**
	 * @return l'environnement de la zone
	 */
	public Environnement getEnvironnement()
	{
		return environnement;
	}
	
	/**
	 * @return la couleur de la zone
	 */
	public String getCouleur()
	{
		return environnement.getCouleur();
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.ZONE;
	}
}
