package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Représente une action entreprise par une créature.
 * 
 * @author greewi
 */
public abstract class AbstractActionAvecDuree implements IAction
{
	private final Creature creature;
	protected int dureeTotale;
	protected int dureeEcoulee;
	
	/**
	 * @param creature la créature réalisant l'action
	 */
	public AbstractActionAvecDuree(Creature creature)
	{
		this.creature = creature;
	}
	
	/**
	 * Renvoie la créature qui effectue l'action
	 * 
	 * @return la créature qui effectue l'action
	 */
	public Creature getCreature()
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
	 * Renvoie la durée de l'action
	 * 
	 * @return la durée de l'action
	 */
	public int getDuree()
	{
		return dureeTotale;
	}
	
	/* (non-Javadoc)
	 * @see net.feerie.creatura.shared.actions.IAction#getProgression()
	 */
	@Override
	public int getProgression()
	{
		return dureeEcoulee;
	}
	
	/* (non-Javadoc)
	 * @see net.feerie.creatura.shared.actions.IAction#getType()
	 */
	@Override
	public abstract TypeAction getType();
	
	/* (non-Javadoc)
	 * @see net.feerie.creatura.shared.actions.IAction#effectueTic()
	 */
	@Override
	public boolean effectueTic()
	{
		dureeEcoulee++;
		if (dureeEcoulee >= dureeTotale)
			return termine();
		return true;
	}
	
	/**
	 * Termine l'action (la durée est écoulée)
	 * 
	 * @return <tt>true</tt> si l'action enchaine sur une autre action.
	 */
	public abstract boolean termine();
}
