package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;

public class MoodlePoison extends Moodle
{
	
	/* package */ MoodlePoison(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.POISON;
	}
	
	@Override
	public void appliqueChargements()
	{
		decharge(Constantes.POISON_DECHARGEMENT_PASSIF);
	}
	
	@Override
	public void appliqueEffets()
	{
		getCreature().reduitSante(Constantes.POISON_REDUCTION_SANTE);
	}
}
