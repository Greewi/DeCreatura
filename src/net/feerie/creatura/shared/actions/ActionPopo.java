package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteDechet;
import net.feerie.creatura.shared.entites.EntiteLitiere;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Repràsente l'action de faire popo (oui. Chier...)
 * 
 * @author greewi
 */
public class ActionPopo extends AbstractActionAvecDuree
{
	private Creature creature;
	private final Entite cible;
	
	/**
	 * @param creature a créature qui fait popo
	 * @param cible le pauvre truc sur lequel la créature fait popo
	 */
	public ActionPopo(Creature creature, Entite cible)
	{
		this.creature = creature;
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
		if (creature.estAffectePar(TypeMoodle.POPO))
		{
			//On collecte les déchets de la créature
			creature.getMoodle(TypeMoodle.POPO).desactive();
			
			//On créé des déchets dans le monde
			if (cible.getType() == TypeEntite.LITIERE)
			{
				EntiteLitiere litiere = (EntiteLitiere) cible;
				litiere.ajouteDechets();
			}
			else
			{
				Monde monde = creature.getMonde();
				monde.nouvelleEntite(new EntiteDechet(monde, new Position(cible.position)));
				creature.getIA().constateNouvelleEntite(TypeEntite.POPO);
			}
		}
		
		return false;
	}
}
