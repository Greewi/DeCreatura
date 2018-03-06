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
}
