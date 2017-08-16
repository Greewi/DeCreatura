package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.Nourriture;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.organisme.Substance;
import net.feerie.creatura.shared.organisme.organes.Organe;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;

/**
 * Représente l'action de manger
 * 
 * @author greewi
 */
public class ActionManger extends Action
{
	/**
	 * @param creature la creature qui mange
	 * @param cible la chose qui sera mangée (enfin que la créature va tenter de
	 *        manger)
	 */
	public ActionManger(Creature creature, Entite cible)
	{
		super(creature, cible);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.MANGER;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		//Si la cible n'existe plus on arrête
		if (!cible.existe())
			return false;
		
		//Si la créature n'a pas fini de manger on continue
		if (frame < this.debut + 60)
			return true;
		
		//Sinon on applique les effets
		if (cible.getType() == TypeEntite.NOURRITURE)
		{
			Nourriture nourriture = (Nourriture) cible;
			Organe estomac = creature.getOrganisme().getOrgane(TypeOrgane.ESTOMAC);
			estomac.ajouteSubstance(Substance.EAU, nourriture.getEau());
			estomac.ajouteSubstance(Substance.GLUCIDES, nourriture.getSucres());
			estomac.ajouteSubstance(Substance.LIPIDES, nourriture.getGras());
			estomac.ajouteSubstance(Substance.PROTEINES, nourriture.getProteines());
			cible.detruit();
		}
		return false;
	}
}
