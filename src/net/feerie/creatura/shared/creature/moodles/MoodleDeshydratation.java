package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : La créature est déshydratée
 * 
 * @author greewi
 */
public class MoodleDeshydratation extends Moodle
{
	
	public MoodleDeshydratation(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.DESHYDRATATION;
	}
	
	@Override
	public void appliqueChargements()
	{
		if (getCreature().estAffectePar(TypeMoodle.SOIF))
			charge(Constantes.DESHYDRATATION_CHARGE_SOIF);
		else
			desactive();
	}
	
	@Override
	public void appliqueEffets()
	{
		getCreature().reduitSante(Constantes.DESHYDRATATION_REDUCTION_SANTE);
	}
}
