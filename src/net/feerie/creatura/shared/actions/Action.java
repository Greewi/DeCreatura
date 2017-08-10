package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;

/**
 * Représente une action entreprise par une créature. Les actions ciblent
 * toujours une entité.
 * 
 * @author greewi
 */
public abstract class Action
{
	protected final Creature creature;
	protected final Entite cible;
	protected int debut;
	
	/**
	 * @param creature la créature réalisant l'action
	 * @param cible la cible de l'action
	 * @param debut la frame de début de l'action
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
	 * Met à jour l'action, pouvant potentiellement la terminer.
	 * 
	 * @param frame le numéro de la frame de la mise à jour
	 * @return <tt>true</tt> si et seulement si l'action n'est pas terminée.
	 */
	public abstract boolean metAJour(int frame);
}
