package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Metronome;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Représente une action entreprise par une créature.
 * 
 * @author greewi
 */
public abstract class Action
{
	private final Creature creature;
	private long dateDebut;
	protected int dureeTotale;
	protected int dureeEcoulee;
	
	/**
	 * @param creature la créature réalisant l'action
	 */
	public Action(Creature creature)
	{
		this.creature = creature;
		debute();
	}
	
	/**
	 * Renvoie la créature qui effectue l'action
	 * 
	 * @return la créature qui effectue l'action
	 */
	protected Creature getCreature()
	{
		return creature;
	}
	
	/**
	 * Défini la durée de l'action
	 * 
	 * @param duree la durée de l'action en tic
	 */
	protected void setDuree(int duree)
	{
		this.dureeTotale = duree;
	}
	
	/**
	 * Renvoie la progression de l'action
	 * 
	 * @return la progression de l'action (0 = début, 1 = fin)
	 */
	public double getProgression()
	{
		return dureeEcoulee / (double) dureeTotale;
	}
	
	/**
	 * Renvoie la progression de l'action pour l'affichage client. Cette
	 * progression n'est pas fiable et ne devrait jamais servir dans la
	 * simulation.
	 * 
	 * @param date la date actuelle en milliseconde
	 * @return la progression de l'action (0 = début, 1 = fin)
	 */
	public double getProgressionAffichage(long date)
	{
		return (date - dateDebut) / (double) (dureeTotale * Metronome.PERIODE_TIC);
	}
	
	/**
	 * @return le type de l'action
	 */
	public abstract TypeAction getType();
	
	/**
	 * Met à jour l'action, pouvant potentiellement la terminer.
	 * 
	 * @return <tt>true</tt> si et seulement si l'action n'est pas terminée.
	 */
	public boolean effectueTic()
	{
		dureeEcoulee++;
		if (dureeEcoulee >= dureeTotale)
			return termine();
		return true;
	}
	
	/**
	 * Débute l'action
	 */
	public void debute()
	{
		dateDebut = System.currentTimeMillis();
	}
	
	/**
	 * Termine l'action (la durée est écoulée)
	 * 
	 * @return <tt>true</tt> si l'action enchaine sur une autre action.
	 */
	public abstract boolean termine();
}
