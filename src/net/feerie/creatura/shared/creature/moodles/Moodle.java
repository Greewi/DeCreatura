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
	 * Applique les chargements et déchargement des moodles (celui-ci y compris)
	 * causé par ce moodle. Toujours appelé, que le moodle soit ou non actif.
	 */
	public void appliqueChargements()
	{
		//Ne fait rien par défaut 
	}
	
	/**
	 * Applique les effects de ce moddle sans les effets de chargement et
	 * déchangement des autres moodles. N'est pas appelée si le moddle n'est pas
	 * actif.
	 */
	public void appliqueEffets()
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
		if (!estActif())
		{
			getCreature().onGagneMoodle(getType());
			getCreature().getIA().constateApparitionMoodle(getType());
		}
		charge = 100;
		actif = true;
	}
	
	/**
	 * Désactive le moodle et fixe sa charge au minimum
	 */
	public final void desactive()
	{
		if (estActif())
		{
			getCreature().onPerdMoodle(getType());
			getCreature().getIA().constateDisparitionMoodle(getType());
		}
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
