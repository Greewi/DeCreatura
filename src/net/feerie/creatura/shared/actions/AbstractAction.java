package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;

public abstract class AbstractAction implements IAction
{
	private final Creature creature;
	protected int dureeEcoulee;
	
	/**
	 * @param creature la créature réalisant l'action
	 */
	public AbstractAction(Creature creature)
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.feerie.creatura.shared.actions.IAction#getProgression()
	 */
	@Override
	public int getProgression()
	{
		return dureeEcoulee;
	}
}
