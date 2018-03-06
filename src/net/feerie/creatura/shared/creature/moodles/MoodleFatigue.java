package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature est fatiguée
 * 
 * @author greewi
 */
public class MoodleFatigue extends Moodle
{
	
	/* package */ MoodleFatigue(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.FATIGUE;
	}
	
	@Override
	public void nouveauCycle()
	{
		charge(1);
		if (estActif())
			getCreature().reduitSante(1);
	}
}
