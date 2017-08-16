package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.organisme.Substance;
import net.feerie.creatura.shared.organisme.organes.Organe;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;

public class ActionDormir extends Action
{
	
	/**
	 * @param creature la créature qui dort
	 * @param cible l'entité sur laquelle la créature tente de dormir
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
		//Si la cible n'existe plus on arrête
		if (!cible.existe())
			return false;
		
		Organe systemeSanguin = creature.getOrganisme().getOrgane(TypeOrgane.SYSTEME_SANGUIN);
		if (systemeSanguin.getSubstance(Substance.NORALDRENALINE) >= 100)
			return false;
		systemeSanguin.transformeSubstance(Substance.PROTEINES, Substance.NORALDRENALINE, 1);
		return true;
	}
	
}
