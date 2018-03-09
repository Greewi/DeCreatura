package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

public class ActionBoire extends Action
{
	private final Entite cible;
	
	public ActionBoire(Creature creature, Entite cible)
	{
		super(creature);
		this.cible = cible;
		setDuree(6);
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
			getCreature().getMoodle(TypeMoodle.SOIF).desactive();
			getCreature().getMoodle(TypeMoodle.POPO).charge(10);
		}
		return false;
	}
}
