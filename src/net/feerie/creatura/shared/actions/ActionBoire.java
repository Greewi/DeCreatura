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
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.BOIRE;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		//Si la cible n'existe plus on arrète
		if (!cible.existe())
			return false;
		
		//Si la créature n'a pas fini de manger on continue
		if (frame < this.debut + 60)
			return true;
		
		//Sinon on applique les effets
		if (cible.getType() == TypeEntite.EAU)
		{
			getCreature().getMoodle(TypeMoodle.SOIF).desactive();
			getCreature().getMoodle(TypeMoodle.POPO).charge(10);
		}
		return false;
	}
	
}
