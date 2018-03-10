package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature a trop chaud
 * 
 * @author greewi
 */
public class MoodleChaud extends Moodle
{
	
	/* package */ MoodleChaud(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.CHAUD;
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
				charge(Constantes.CHAUD_CHARGEMENT_CHAUD_MOUILLE);
			else
				charge(Constantes.CHAUD_CHARGEMENT_CHAUD);
		}
		else if (faitFroid)
		{
			if (mouille)
				decharge(Constantes.CHAUD_DECHARGEMENT_FROID_MOUILLE);
			else
				decharge(Constantes.CHAUD_DECHARGEMENT_FROID);
		}
		else
		{
			if (mouille)
				decharge(Constantes.CHAUD_DECHARGEMENT_TEMPERE_MOUILLE);
			else
				decharge(Constantes.CHAUD_DECHARGEMENT_TEMPERE);
		}
	}
}
