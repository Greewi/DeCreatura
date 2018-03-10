package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature est en train de mourir de Faim
 * 
 * @author greewi
 */
public class MoodleFamine extends Moodle
{
	
	public MoodleFamine(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.FAMINE;
	}
	
	@Override
	public void appliqueChargements()
	{
		if (getCreature().estAffectePar(TypeMoodle.FAIM))
			charge(Constantes.FAMINE_CHARGE_FAIM);
		else
			desactive();
	}
	
	@Override
	public void appliqueEffets()
	{
		getCreature().reduitSante(Constantes.FAMINE_REDUCTION_SANTE);
	}
}
