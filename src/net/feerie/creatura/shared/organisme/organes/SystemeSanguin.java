package net.feerie.creatura.shared.organisme.organes;

import net.feerie.creatura.shared.organisme.Organisme;

/**
 * C'est par cet organe que transit les substances du corps de la créature.
 * 
 * @author greewi
 */
public class SystemeSanguin extends Organe
{
	
	public SystemeSanguin(Organisme organisme)
	{
		super(organisme, TypeOrgane.SYSTEME_SANGUIN);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
	
}
