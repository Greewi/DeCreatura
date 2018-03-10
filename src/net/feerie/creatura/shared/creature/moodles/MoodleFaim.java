package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
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
	public void appliqueChargements()
	{
		if (getCreature().getMoodle(TypeMoodle.FROID).estActif())
			charge(Constantes.FAIM_CHARGE_PASSIVE_FROID);
		else
			charge(Constantes.FAIM_CHARGE_PASSIVE);
	}
	
	@Override
	public void appliqueEffets()
	{
		getCreature().reduitSante(Constantes.FAIM_REDUCTION_SANTE);
	}
}
