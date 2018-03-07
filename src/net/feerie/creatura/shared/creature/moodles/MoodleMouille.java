package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

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
	public void nouveauCycle()
	{
		if (estActif())
		{
			getCreature().getMoodle(TypeMoodle.FROID).charge(5);
			getCreature().getMoodle(TypeMoodle.CHAUD).decharge(10);
		}
		
		Entite eauProche = getCreature().cherche(TypeEntite.EAU);
		if (eauProche != null && getCreature().getDistanceCarre(eauProche) < eauProche.getTaille().l * eauProche.getTaille().l + 1)
			active();
	}
}
