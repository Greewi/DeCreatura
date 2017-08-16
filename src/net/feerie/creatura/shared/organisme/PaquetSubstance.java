package net.feerie.creatura.shared.organisme;

import java.util.EnumMap;

/**
 * Représente un paquet de substance, c'est à dire plusieurs susbtances et leurs
 * quantités respectives.
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
	 * Renvoie la quantité d'une substance dans ce paquet
	 * 
	 * @param substance la substance dont on veut la quantité
	 * @return la quantité de la substance contenue dans ce paquet
	 */
	public int get(Substance substance)
	{
		Integer quantite = substances.get(substance);
		if (quantite == null)
			return 0;
		return quantite.intValue();
	}
	
	/**
	 * Définit la quantité d'une substance dans le paquet
	 * 
	 * @param substance la substance dont on veut définir la quantité
	 * @param quantite la quantité de substance
	 */
	public void put(Substance substance, int quantite)
	{
		substances.put(substance, quantite);
	}
	
	@Override
	public String toString()
	{
		String str = "Paquet de substances : ";
		for(Substance substance : substances.keySet())
			str+= substance+"("+substances.get(substance)+")  ";
		return str;
	}
}
