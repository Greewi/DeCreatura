package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

public class ActionBoire extends AbstractActionAvecDuree
{
	private final Creature creature;
	private final Entite cible;
	
	public ActionBoire(Creature creature, Entite cible)
	{
		this.creature = creature;
		this.cible = cible;
		setDuree(Constantes.ACTION_BOIRE_DUREE);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.BOIRE;
	}
	
	@Override
	public boolean termine()
	{
		//Si c'est de l'eau on applique les effets
		if (cible.getType() == TypeEntite.EAU)
		{
			creature.getMoodle(TypeMoodle.SOIF).desactive();
			creature.getMoodle(TypeMoodle.POPO).charge(10);
			creature.getMoodle(TypeMoodle.MOUILLE).active();
		}
		return false;
	}
}
