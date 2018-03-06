package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Dechet;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.Litiere;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Repràsente l'action de faire popo (oui. Chier...)
 * 
 * @author greewi
 */
public class ActionPopo extends Action
{
	private final Entite cible;
	
	/**
	 * @param creature a créature qui fait popo
	 * @param cible le pauvre truc sur lequel la créature fait popo
	 */
	public ActionPopo(Creature creature, Entite cible)
	{
		super(creature);
		this.cible = cible;
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.FAIRE_POPO;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		
		//Si la cible n'existe plus on arràte
		if (!cible.existe())
			return false;
		
		//Si la créature n'a pas fini de faire ses besoins on continue
		if (frame < debut + 120)
			return true;
		
		//On collecte les déchets de la créature
		getCreature().getMoodle(TypeMoodle.POPO).desactive();
		
		//On créé des déchets dans le monde
		if (cible.getType() == TypeEntite.LITIERE)
		{
			Litiere litiere = (Litiere) cible;
			litiere.ajouteDechets();
		}
		else
			getCreature().getMonde().nouvelleEntite(new Dechet(getCreature().getMonde(), new Position(cible.getPosition())));
		
		return false;
	}
	
}
