package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteCreature;
import net.feerie.creatura.shared.entites.TypeEntite;

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
	public ActionMarcherVersEntite(EntiteCreature creature, Entite cible)
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
	public ActionMarcherVersEntite(EntiteCreature creature, Entite cible, Action actionAEnchainer)
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
		if (cible.getType() == TypeEntite.EAU)
		{
			if (depart.x < destination.x)
				destination.x -= cible.getTaille().l / 2;
			else
				destination.x += cible.getTaille().l / 2;
		}
		setDestination(destination);
	}
	
}
