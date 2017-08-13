package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe stock les graisses et apporte une insolation thermique
 * supplémentaire.
 * 
 * @author greewi
 */
public class TissuAdipeux extends Organe
{
	
	public TissuAdipeux(Organisme organisme)
	{
		super(organisme, TypeOrgane.TISSU_ADIPEUX);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
	
}
