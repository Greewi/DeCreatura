package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;

public interface IAction
{
	/**
	 * Renvoie la créature qui effectue l'action
	 * 
	 * @return la créature qui effectue l'action
	 */
	public Creature getCreature();
		
	/**
	 * Renvoie la progression de l'action (en tics depuis le début)
	 * 
	 * @return la progression de l'action (en tics depuis le début)
	 */
	public int getProgression();
	
	/**
	 * @return le type de l'action
	 */
	public abstract TypeAction getType();
	
	/**
	 * Met à jour l'action, pouvant potentiellement la terminer.
	 * 
	 * @return <tt>true</tt> si et seulement si l'action n'est pas terminée.
	 */
	public boolean effectueTic();
}