package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;

public class ActionDormir extends Action
{
	
	/**
	 * @param creature la créature qui dort
	 */
	public ActionDormir(Creature creature)
	{
		super(creature);
		setDuree(30);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.DORMIR;
	}
	
	@Override
	public boolean termine()
	{
		return false;
	}
}
