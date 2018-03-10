package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
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
	public void appliqueChargements()
	{
		if(getCreature().getMoodle(TypeMoodle.CHAUD).estActif())
			charge(Constantes.SOIF_CHARGE_PASSIVE_CHALEUR);
		else
			charge(Constantes.SOIF_CHARGE_PASSIVE);
	}
	
	@Override
	public void appliqueEffets()
	{
		getCreature().reduitSante(Constantes.SOIF_REDUCTION_SANTE);
	}
}
