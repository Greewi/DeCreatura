package net.feerie.creatura.shared.organisme.organes;

import net.feerie.creatura.shared.organisme.Organisme;

/**
 * Cet organe représente le centre décisionnel d'une créature.
 * 
 * @author greewi
 */
public class Cerveau extends Organe
{
	
	public Cerveau(Organisme organisme)
	{
		super(organisme, TypeOrgane.CERVEAU);
	}
	
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
}
