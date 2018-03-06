package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature a envie de faire ses besoins
 * 
 * @author greewi
 */
public class MoodlePopo extends Moodle
{
	
	/* package */ MoodlePopo(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.POPO;
	}
}
