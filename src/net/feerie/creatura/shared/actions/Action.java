package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Représente une action entreprise par une créature.
 * 
 * @author greewi
 */
public abstract class Action
{
	private final Creature creature;
	protected int debut;
	
	/**
	 * @param creature la créature réalisant l'action
	 * @param debut la frame de début de l'action
	 */
	public Action(Creature creature)
	{
		this.creature = creature;
		this.debut = -1;
	}
	
	protected Creature getCreature()
	{
		return creature;
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
	 * Met à jour l'action, pouvant potentiellement la terminer.
	 * 
	 * @param frame le numéro de la frame de la mise à jour
	 * @return <tt>true</tt> si et seulement si l'action n'est pas terminée.
	 */
	public abstract boolean metAJour(int frame);
}
