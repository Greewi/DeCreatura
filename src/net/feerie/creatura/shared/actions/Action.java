package net.feerie.creatura.shared.actions;

public interface Action
{
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