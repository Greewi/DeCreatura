package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.EntiteDechet;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteLitiere;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Repràsente l'action de faire popo (oui. Chier...)
 * 
 * @author greewi
 */
public class ActionPopo extends AbstractActionAvecDuree
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
		setDuree(Constantes.ACTION_POPO_DUREE);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.FAIRE_POPO;
	}
	
	@Override
	public boolean termine()
	{
		//On collecte les déchets de la créature
		getCreature().getMoodle(TypeMoodle.POPO).desactive();
		
		//On créé des déchets dans le monde
		if (cible.getType() == TypeEntite.LITIERE)
		{
			EntiteLitiere litiere = (EntiteLitiere) cible;
			litiere.ajouteDechets();
		}
		else
			getCreature().getMonde().nouvelleEntite(new EntiteDechet(getCreature().getMonde(), new Position(cible.position)));
		
		return false;
	}
}
