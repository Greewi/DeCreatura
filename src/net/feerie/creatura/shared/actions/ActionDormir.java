package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.organisme.TypeVariableVitale;
import net.feerie.creatura.shared.organisme.VariableVitale;

public class ActionDormir extends Action
{
	
	/**
	 * @param creature la cr�ature qui dort
	 * @param cible l'entit� sur laquelle la cr�ature tente de dormir
	 */
	public ActionDormir(Creature creature, Entite cible)
	{
		super(creature, cible);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.DORMIR;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		//Si la cible n'existe plus on arr�te
		if (!cible.existe())
			return false;
		
		int sommeilGagnee = 1 + (frame - this.debut) / 10;
		VariableVitale fatigue = creature.getVariableVitale(TypeVariableVitale.FATIGUE);
		fatigue.ajoute(sommeilGagnee);
		//Si la cr�ature n'a plus sommeil on la r�veille
		if (fatigue.get() == fatigue.getIdeal())
			return false;
		return true;
	}
	
}
