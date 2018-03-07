package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.TypeAction;
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
		Action actionActuelle = getCreature().getActionActuelle();
		if (actionActuelle != null && actionActuelle.getType() == TypeAction.DORMIR)
			decharge(5);
		else
		{
			if (estActif())
				getCreature().reduitSante(1);
			charge(1);
		}
	}
}
