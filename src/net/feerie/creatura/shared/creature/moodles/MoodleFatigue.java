package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.actions.IAction;
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
	public void appliqueChargements()
	{
		IAction actionActuelle = getCreature().getActionActuelle();
		if (actionActuelle != null && actionActuelle.getType() == TypeAction.DORMIR)
			decharge(Constantes.FATIGUE_DECHARGEMENT_DODO);
		else
			charge(Constantes.FATIGUE_CHARGEMENT_PASSIF);
	}
	
	@Override
	public void appliqueEffets()
	{
		getCreature().reduitSante(Constantes.FATIGUE_REDUCTION_SANTE);
	}
}
