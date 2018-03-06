package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Un moodle est un état de la créature
 * 
 * @author greewi
 */
public abstract class Moodle
{
	private int charge;
	private boolean actif;
	private final Creature creature;
	
	/* package */ Moodle(Creature creature)
	{
		this.creature = creature;
		this.charge = 0;
		this.actif = false;
	}
	
	/**
	 * Renvoie le type du Moodle
	 * 
	 * @return le type du Moodle
	 */
	public abstract TypeMoodle getType();
	
	/**
	 * Effectue un nouveau cycle métabolique
	 */
	public void nouveauCycle()
	{
		//Ne fait rien par défaut 
	}
	
	/**
	 * Renvoie la créature potentiellement affectée par ce moodle
	 * 
	 * @return la créature potentiellement affectée par ce moodle
	 */
	protected Creature getCreature()
	{
		return creature;
	}
	
	/**
	 * Détermine si le moodle est actif et a un effet
	 * 
	 * @return <tt>true</tt> si le moodle est actif et a un effet
	 */
	public final boolean estActif()
	{
		return actif;
	}
	
	/**
	 * Active le moodle et fixe sa charge au maximum
	 */
	public final void active()
	{
		charge = 100;
		actif = true;
	}
	
	/**
	 * Désactive le moodle et fixe sa charge au minimum
	 */
	public final void desactive()
	{
		charge = 0;
		actif = false;
	}
	
	/**
	 * Charge le moodle et l'active si la charge atteint le maximum
	 * 
	 * @param increment la quantité à charger (sur 100)
	 */
	public final void charge(int increment)
	{
		charge += increment;
		if (charge >= 100)
			active();
	}
	
	/**
	 * Décharge le moodle et le séactive si la charge atteint le minimum
	 * 
	 * @param decrement la quantité à décharger (sur 100)
	 */
	public final void decharge(int decrement)
	{
		charge -= decrement;
		if (charge <= 0)
			desactive();
	}
	
	/**
	 * Renvoie la charge du moodle sur 100. Note une charge entre 0 et 100 ne
	 * veux pas dire que le moodle est actif (ou inactif). utilisez est actif
	 * pour le déterminer.
	 * 
	 * @return la charge du moodle sur 100
	 */
	public int getCharge()
	{
		return charge;
	}
}
