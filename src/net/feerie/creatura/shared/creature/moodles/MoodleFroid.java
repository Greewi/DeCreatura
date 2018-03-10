package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la creature a faim
 * 
 * @author greewi
 */
public class MoodleFroid extends Moodle
{
	
	/* package */ MoodleFroid(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.FROID;
	}
	
	@Override
	public void appliqueChargements()
	{
		boolean faitChaud = getCreature().getEnvironnement().getTemperature() > Constantes.TEMPERATURE_CHAUD;
		boolean faitFroid = getCreature().getEnvironnement().getTemperature() < Constantes.TEMPERATURE_FROID;
		boolean mouille = getCreature().getMoodle(TypeMoodle.MOUILLE).estActif();
		
		if (faitChaud)
		{
			if (mouille)
				decharge(Constantes.FROID_DECHARGEMENT_CHAUD_MOUILLE);
			else
				decharge(Constantes.FROID_DECHARGEMENT_CHAUD);
		}
		else if (faitFroid)
		{
			if (mouille)
				charge(Constantes.FROID_CHARGEMENT_FROID_MOUILLE);
			else
				charge(Constantes.FROID_CHARGEMENT_FROID);
		}
		else
		{
			if (mouille)
				decharge(Constantes.FROID_DECHARGEMENT_TEMPERE_MOUILLE);
			else
				decharge(Constantes.FROID_DECHARGEMENT_TEMPERE);
		}
	}
}
