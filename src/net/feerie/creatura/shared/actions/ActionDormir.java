package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;

public class ActionDormir implements Action
{
	private final Creature creature;
	
	/**
	 * @param creature la créature qui dort
	 */
	public ActionDormir(Creature creature)
	{
		this.creature = creature;
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.DORMIR;
	}
	
	@Override
	public boolean effectueTic()
	{
		return creature.estAffectePar(TypeMoodle.FATIGUE);
	}
	
	@Override
	public int getProgression()
	{
		return 0;
	}
}
