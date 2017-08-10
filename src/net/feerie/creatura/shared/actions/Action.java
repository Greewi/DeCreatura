package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;

/**
 * Repr�sente une action entreprise par une cr�ature. Les actions ciblent
 * toujours une entit�.
 * 
 * @author greewi
 */
public abstract class Action
{
	protected final Creature creature;
	protected final Entite cible;
	protected int debut;
	
	/**
	 * @param creature la cr�ature r�alisant l'action
	 * @param cible la cible de l'action
	 * @param debut la frame de d�but de l'action
	 */
	public Action(Creature creature, Entite cible)
	{
		this.creature = creature;
		this.cible = cible;
		this.debut = -1;
	}
	
	/**
	 * Debute l'action
	 * 
	 * @param frame la frame de debut de l'action
	 */
	public void debute(int frame)
	{
		this.debut = frame;
	}
	
	/**
	 * @return le type de l'action
	 */
	public abstract TypeAction getType();
	
	/**
	 * Met � jour l'action, pouvant potentiellement la terminer.
	 * 
	 * @param frame le num�ro de la frame de la mise � jour
	 * @return <tt>true</tt> si et seulement si l'action n'est pas termin�e.
	 */
	public abstract boolean metAJour(int frame);
}
