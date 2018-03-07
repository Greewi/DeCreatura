package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature s'ennuie
 * 
 * @author greewi
 */
public class MoodleEnnui extends Moodle
{
	
	/* package */ MoodleEnnui(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.ENNUI;
	}
	
	@Override
	public void nouveauCycle()
	{
		if (getCreature().getActionActuelle() == null)
			charge(10);
		else
			decharge(24);
	}
}
