package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe extrait les Toxines du système sanguin et les éjecte lors que la
 * créature fait ses besoins.
 * 
 * @author greewi
 */
public class Reins extends Organe
{
	
	public Reins(Organisme organisme)
	{
		super(organisme, TypeOrgane.REINS);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
	
}
