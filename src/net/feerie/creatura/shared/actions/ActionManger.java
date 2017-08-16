package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.Nourriture;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.organisme.TypeVariableVitale;

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
			creature.getVariableVitale(TypeVariableVitale.SUCRES_DIGESTION).ajoute(nourriture.getSucres());
			creature.getVariableVitale(TypeVariableVitale.PROTEINES_DIGESTION).ajoute(nourriture.getProteines());
			creature.getVariableVitale(TypeVariableVitale.GRAS_DIGESTION).ajoute(nourriture.getGras());
			creature.getVariableVitale(TypeVariableVitale.EAU_DIGESTION).ajoute(nourriture.getEau());
			cible.detruit();
		}
		return false;
	}
}
