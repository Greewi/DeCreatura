package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;

/**
 * Action activer quelque chose
 * 
 * @author greewi
 */
public class ActionActiver extends AbstractActionAvecDuree
{
	private final Creature creature;
	private final Entite cible;
	
	/**
	 * @param creature la creature qui activer l'entité
	 * @param cible la chose qui sera activée
	 */
	public ActionActiver(Creature creature, Entite cible)
	{
		this.creature = creature;
		this.cible = cible;
		setDuree(Constantes.ACTION_ACTIVER_DUREE);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.ACTIVER;
	}
	
	@Override
	public boolean termine()
	{
		cible.active(creature);
		return false;
	}
}
