package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Moodle : la créature est mouillée
 * 
 * @author greewi
 */
public class MoodleMouille extends Moodle
{
	
	/* package */ MoodleMouille(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public TypeMoodle getType()
	{
		return TypeMoodle.MOUILLE;
	}
	
	@Override
	public void appliqueChargements()
	{
		decharge(Constantes.MOUILLE_DECHARGEMENT_PASSIF);
	}
}
