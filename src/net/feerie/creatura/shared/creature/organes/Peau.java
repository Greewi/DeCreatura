package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

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
