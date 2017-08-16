package net.feerie.creatura.shared.organisme.organes;

import net.feerie.creatura.shared.organisme.Organisme;

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
