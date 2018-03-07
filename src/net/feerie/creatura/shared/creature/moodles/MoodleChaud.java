package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature a trop chaud
 * 
 * @author greewi
 */
public class MoodleChaud extends Moodle
{
	
	/* package */ MoodleChaud(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.CHAUD;
	}
	
	@Override
	public void nouveauCycle()
	{
		if (estActif())
			getCreature().getMoodle(TypeMoodle.SOIF).charge(5);
		
		if (getCreature().getEnvironnement().getTemperature() > 25)
			charge(10);
		else
			decharge(20);
	}
}
