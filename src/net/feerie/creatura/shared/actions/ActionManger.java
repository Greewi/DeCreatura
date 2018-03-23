package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Représente l'action de manger
 * 
 * @author greewi
 */
public class ActionManger extends AbstractActionAvecDuree
{
	private final Creature creature;
	private final Entite cible;
	
	/**
	 * @param creature la creature qui mange
	 * @param cible la chose qui sera mangée (enfin que la créature va tenter de
	 *        manger)
	 */
	public ActionManger(Creature creature, Entite cible)
	{
		this.creature = creature;
		this.cible = cible;
		setDuree(Constantes.ACTION_MANGER_DUREE);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.MANGER;
	}
	
	@Override
	public boolean termine()
	{
		//Si c'est de la nourriture on applique les effets
		if (cible.existe() && cible.getType().estActionPossible(TypeAction.MANGER))
		{
			if (cible.getType() == TypeEntite.NOURRITURE_GRANULE)
			{
				creature.getMoodle(TypeMoodle.FAIM).decharge(50);
				creature.getMoodle(TypeMoodle.POPO).charge(30);
			}
			else
			{
				creature.getMoodle(TypeMoodle.POISON).active();				
				creature.getMoodle(TypeMoodle.POPO).charge(30);
			}
			cible.detruit();
		}
		return false;
	}
	
}
