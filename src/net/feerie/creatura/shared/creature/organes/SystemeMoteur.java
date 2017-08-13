package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe permet à la créature d'agir et de se déplacer
 * 
 * @author greewi
 */
public class SystemeMoteur extends Organe
{
	
	public SystemeMoteur(Organisme organisme)
	{
		super(organisme, TypeOrgane.SYSTEME_MOTEUR);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
	
}
