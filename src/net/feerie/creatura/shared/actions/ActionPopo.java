package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.TypeVariableVitale;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Dechet;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.Litiere;
import net.feerie.creatura.shared.entites.TypeEntite;

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
		
		//On créé des déchets dans le monde
		double quantite = creature.getVariableVitale(TypeVariableVitale.DECHETS).get();
		if (cible.getType() == TypeEntite.LITIERE)
		{
			Litiere litiere = (Litiere) cible;
			litiere.ajouteDechets(quantite);
		}
		else
			monde.ajouteEntite(new Dechet(monde, quantite, new Position(cible.getPosition())));
		
		//On "vide" la créature
		creature.getVariableVitale(TypeVariableVitale.DECHETS).set(0);
		
		return false;
	}
	
}
