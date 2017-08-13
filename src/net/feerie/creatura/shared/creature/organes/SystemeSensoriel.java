package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe collecte les informations sensorielles de la créature et les
 * apporte au cerveau.
 * 
 * @author greewi
 */
public class SystemeSensoriel extends Organe
{
	
	public SystemeSensoriel(Organisme organisme)
	{
		super(organisme, TypeOrgane.SYSTEME_SENSORIEL);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
	
}
