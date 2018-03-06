package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la creature a faim
 * 
 * @author greewi
 */
public class MoodleFroid extends Moodle
{
	
	/* package */ MoodleFroid(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.FROID;
	}
	
	@Override
	public void nouveauCycle()
	{
		if (getCreature().getEnvironnement().getTemperature() < 10)
			charge(10);
		else
			decharge(10);
		if (estActif())
			getCreature().getMoodle(TypeMoodle.FAIM).charge(5);
	}
}
