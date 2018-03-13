package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.EntiteCreature;

public abstract class AbstractAction implements Action
{
	private final EntiteCreature creature;
	protected int dureeEcoulee;
	
	/**
	 * @param creature la créature réalisant l'action
	 */
	public AbstractAction(EntiteCreature creature)
	{
		this.creature = creature;
	}
	
	/**
	 * Renvoie la créature qui effectue l'action
	 * 
	 * @return la créature qui effectue l'action
	 */
	public EntiteCreature getCreature()
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
