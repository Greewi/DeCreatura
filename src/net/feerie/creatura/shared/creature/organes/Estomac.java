package net.feerie.creatura.shared.creature.organes;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.Nutriments;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Cet organe digère les aliments. C'est ici que va tout ce que mange la
 * créature.
 * 
 * @author greewi
 */
public class Estomac implements Organe
{
	private Creature creature;
	private EnumMap<Nutriments, Integer> contenu;
	private int dechets;
	private int capacite;
	
	/**
	 * @param creature la créature à laquelle appartient cet estomac
	 * @param capacite la capacité de l'estomac
	 */
	public Estomac(Creature creature, int capacite)
	{
		this.creature = creature;
		this.contenu = new EnumMap<>(Nutriments.class);
		this.capacite = capacite;
		this.dechets = 0;
	}
	
	@Override
	public void metAJour(int frame)
	{
		// TODO Auto-generated method stub
		
	}
}
