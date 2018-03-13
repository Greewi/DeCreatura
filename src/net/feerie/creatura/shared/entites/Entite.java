package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.monde.Monde;

public abstract class Entite
{
	/**
	 * La position actuelle de la créature
	 */
	public final Position position;
	/**
	 * La position de la créature au prochain tic
	 */
	public final Position positionProchainTic;
	protected final Monde monde;
	private final int ID;
	private Dimension taille;
	private int vitesseVerticale;
	private boolean estDetruit = false;
	
	/**
	 * Constructeur
	 * 
	 * @param monde le monde auquel appartient l'entité
	 * @param x la position x de l'entité
	 * @param z la position z de l'entité
	 * @param l la longueur de l'entité
	 * @param h la hauteur de l'entité
	 */
	public Entite(Monde monde, Position position, Dimension taille)
	{
		this.monde = monde;
		this.ID = monde.genereIDEntite();
		this.position = position;
		this.positionProchainTic = new Position(position);
		this.taille = taille;
		this.vitesseVerticale = 0;
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
		Position p1 = entite.position;
		Position p2 = position;
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.z - p2.z) * (p1.z - p2.z);
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
		monde.supprimeEntite(ID);
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
	 * Active l'entité (clic sur l'entité, ou utilisation par la créature)
	 * 
	 * @param activeParJoueur si <tt>true</tt> l'entité est activée par le
	 *        joueur
	 * @return le nom de l'interface à ouvrir si l'entité permet l'accès à un
	 *         une interface particulière (la gestion de la créature par
	 *         exemple) ou <tt>null</tt> si aucune interface n'est à ouvrir
	 */
	public String active(boolean activeParJoueur)
	{
		return null;
	}
	
	/**
	 * Met à jour l'entité
	 */
	public void effectueTic()
	{
		position.x = positionProchainTic.x;
		position.z = positionProchainTic.z;
	}
	
	/**
	 * Applique un tic de gravité sur l'entite
	 */
	public void appliqueGravite()
	{
		int zSol = monde.getCarte().getHauteurSol(positionProchainTic);
		vitesseVerticale += monde.getAccelerationGravite();
		positionProchainTic.z -= vitesseVerticale;
		
		if (positionProchainTic.z < zSol)// Atterissage
		{
			vitesseVerticale = 0;
			positionProchainTic.z = zSol;
		}
	}
	
	/**
	 * Met à jour l'entité
	 */
	public void effectueCycleIA()
	{
		//Rien à faire par défaut		
	}
	
	/**
	 * Met à jour l'entité
	 */
	public void effectueCycleMetabolique()
	{
		//Rien à faire par défaut
	}
}
