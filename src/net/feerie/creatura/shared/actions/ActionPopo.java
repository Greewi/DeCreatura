package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Dechet;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.Litiere;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.organisme.Substance;
import net.feerie.creatura.shared.organisme.organes.Organe;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;

/**
 * Représente l'action de faire popo (oui. Chier...)
 * 
 * @author greewi
 */
public class ActionPopo extends Action
{
	private final Monde monde;
	
	/**
	 * @param creature a créature qui fait popo
	 * @param cible le pauvre truc sur lequel la créature fait popo
	 */
	public ActionPopo(Monde monde, Creature creature, Entite cible)
	{
		super(creature, cible);
		this.monde = monde;
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.FAIRE_POPO;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		
		//Si la cible n'existe plus on arrête
		if (!cible.existe())
			return false;
		
		//Si la créature n'a pas fini de faire ses besoins on continue
		if (frame < debut + 120)
			return true;
		
		//On collecte les déchets de la créature
		int total = 0;
		Organe intestins = creature.getOrganisme().getOrgane(TypeOrgane.INTESTINS);
		for (Substance substance : Substance.values())
		{
			int quantite = intestins.getSubstance(substance);
			total += quantite;
			intestins.ajouteSubstance(substance, -quantite);
		}
		
		//On créé des déchets dans le monde
		if (cible.getType() == TypeEntite.LITIERE)
		{
			Litiere litiere = (Litiere) cible;
			litiere.ajouteDechets(total);
		}
		else
			monde.nouvelleEntite(new Dechet(monde, total, new Position(cible.getPosition())));
		
		return false;
	}
	
}
