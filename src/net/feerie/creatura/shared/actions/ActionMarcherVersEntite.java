package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;

/**
 * Se déplace vers une entité et s'arrête au bord
 * 
 * @author greewi
 */
public class ActionMarcherVersEntite extends ActionMarcher
{
	private Entite cible;
	
	/**
	 * @param creature la créature qui va se déplacer
	 * @param cible l'entite vers laquelle aller
	 */
	public ActionMarcherVersEntite(Creature creature, Entite cible)
	{
		super(creature);
		this.cible = cible;
		calculeDestination();
	}
	
	/**
	 * @param creature la créature qui va se déplacer
	 * @param cible l'entite vers laquelle aller
	 * @param actionAEnchainer l'action à effetuer une fois arrivée sur place.
	 *        <tt>null</tt> si aucune action n'est à faire.
	 */
	public ActionMarcherVersEntite(Creature creature, Entite cible, IAction actionAEnchainer)
	{
		super(creature);
		this.cible = cible;
		setActionAEnchainer(actionAEnchainer);
		calculeDestination();
	}
	
	private void calculeDestination()
	{
		Position depart = getCreature().position;
		Position destination = new Position(cible.position);
		if (depart.x < destination.x)
			destination.x -= cible.getTaille().l / 2;
		else
			destination.x += cible.getTaille().l / 2;
		setDestination(destination);
	}
	
}
