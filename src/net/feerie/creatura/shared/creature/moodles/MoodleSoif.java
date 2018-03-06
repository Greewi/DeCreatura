package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature a soif.
 * 
 * @author greewi
 */
public class MoodleSoif extends Moodle
{
	
	/* package */ MoodleSoif(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.SOIF;
	}
	
	@Override
	public void nouveauCycle()
	{
		charge(3);
		if (estActif())
			getCreature().reduitSante(1);
	}
}
