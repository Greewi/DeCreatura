package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Vecteur;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;

/**
 * Se déplace vers une entité et s'arrête au bord
 * 
 * @author greewi
 */
public class ActionSeDeplacerVersEntite extends ActionSeDeplacer
{
	private Entite cible;
	
	/**
	 * @param creature la créature qui va se déplacer
	 * @param cible l'entite vers laquelle aller
	 */
	public ActionSeDeplacerVersEntite(Creature creature, Entite cible)
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
	public ActionSeDeplacerVersEntite(Creature creature, Entite cible, Action actionAEnchainer)
	{
		super(creature);
		this.cible = cible;
		setActionAEnchainer(actionAEnchainer);
		calculeDestination();
	}
	
	private void calculeDestination()
	{
		Position depart = getCreature().getPosition();
		Position destination = new Position(cible.getPosition());
		Vecteur vecteur = new Vecteur(depart, destination);
		double norme = vecteur.getNorme();
		destination = depart.translate(vecteur.multiplie((norme - (cible.getTaille().h + cible.getTaille().l) / 4) / norme));
		setDestination(destination);
	}
	
}
