package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe digère les aliments. C'est ici que va tout ce que mange la
 * créature.
 * 
 * @author greewi
 */
public class Estomac extends Organe
{
	private int capacite;
	
	/**
	 * @param creature la créature à laquelle appartient cet estomac
	 * @param capacite la capacité de l'estomac (seuil de satiété)
	 */
	public Estomac(Organisme organisme, int capacite)
	{
		super(organisme, TypeOrgane.ESTOMAC);
		this.capacite = capacite;
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
	}
}
