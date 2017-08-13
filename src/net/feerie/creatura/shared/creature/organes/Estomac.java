package net.feerie.creatura.shared.creature.organes;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe dig�re les aliments. C'est ici que va tout ce que mange la
 * cr�ature.
 * 
 * @author greewi
 */
public class Estomac extends Organe
{
	private int capacite;
	
	/**
	 * @param creature la cr�ature � laquelle appartient cet estomac
	 * @param capacite la capacit� de l'estomac (seuil de sati�t�)
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
