package net.feerie.creatura.shared.organisme.organes;

import net.feerie.creatura.shared.organisme.Organisme;

/**
 * Cet organe protège les aux autres organes de l'environnement.
 * 
 * @author greewi
 */
public class Peau extends Organe
{
	
	public Peau(Organisme organisme)
	{
		super(organisme, TypeOrgane.PEAU);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
	
}
