package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;

public class ActionDormir extends Action
{
	
	/**
	 * @param creature la créature qui dort
	 */
	public ActionDormir(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.DORMIR;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		return getCreature().estAffectePar(TypeMoodle.FATIGUE);
	}
}
