package net.feerie.creatura.shared.creature;

import java.util.EnumMap;

/**
 * Repr�sente un paquet de substance, c'est � dire plusieurs susbtances et leurs
 * quantit�s respectives.
 * 
 * @author greewi
 */
public class PaquetSubstance
{
	private final EnumMap<Substance, Integer> substances;
	
	public PaquetSubstance()
	{
		substances = new EnumMap<>(Substance.class);
	}
	
	/**
	 * Renvoie la quantit� d'une substance dans ce paquet
	 * 
	 * @param substance la substance dont on veut la quantit�
	 * @return la quantit� de la substance contenue dans ce paquet
	 */
	public int get(Substance substance)
	{
		Integer quantite = substances.get(substance);
		if (quantite == null)
			return 0;
		return quantite.intValue();
	}
	
	/**
	 * D�finit la quantit� d'une substance dans le paquet
	 * 
	 * @param substance la substance dont on veut d�finir la quantit�
	 * @param quantite la quantit� de substance
	 */
	public void put(Substance substance, int quantite)
	{
		substances.put(substance, quantite);
	}
}
