package net.feerie.creatura.shared.creature;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Représente un besoin d'une créature dans le monde Un besoin est associé à un
 * nombre compris en 0 et 1 (inclus), 0 signifiant que le besoin est rempli et 1
 * qu'il se manifeste au maximum
 * 
 * @author greewi
 */
public class Besoin
{
	@SuppressWarnings("unused")
	private final TypeBesoin type;
	@SuppressWarnings("unused")
	private final Creature creature;
	private double valeur;
	
	/**
	 * @param type le type du besoin
	 * @param valeurInitiale la valeur initiale du besoin
	 * @param creature la créature auquel se réfere ce besoin
	 */
	public Besoin(TypeBesoin type, double valeurInitiale, Creature creature)
	{
		this.type = type;
		this.creature = creature;
		this.valeur = valeurInitiale;
	}
	
	/**
	 * @return la valeur actuelle de ce besoin
	 */
	public double get()
	{
		return valeur;
	}
	
	/**
	 * Défini la valeur actuelle de ce besoin
	 * 
	 * @param valeur la nouvelle valeur de ce besoin
	 */
	public void set(double valeur)
	{
		if (valeur > 1.0)
			this.valeur = 1.0;
		if (valeur < 0.0)
			this.valeur = 0.0;
		else
			this.valeur = valeur;
	}
}
