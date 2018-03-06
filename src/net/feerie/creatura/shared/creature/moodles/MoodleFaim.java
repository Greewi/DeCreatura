package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature a faim
 * 
 * @author greewi
 */
public class MoodleFaim extends Moodle
{
	
	/* package */ MoodleFaim(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.FAIM;
	}
	
	@Override
	public void nouveauCycle()
	{
		charge(2);
		if (estActif())
			getCreature().reduitSante(1);
	}
}
