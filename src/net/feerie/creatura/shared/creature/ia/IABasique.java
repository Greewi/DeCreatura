package net.feerie.creatura.shared.creature.ia;

import java.util.List;

import com.google.gwt.user.client.Random;

import net.feerie.creatura.shared.actions.ActionBoire;
import net.feerie.creatura.shared.actions.ActionDormir;
import net.feerie.creatura.shared.actions.ActionManger;
import net.feerie.creatura.shared.actions.ActionMarcher;
import net.feerie.creatura.shared.actions.ActionMarcherVersEntite;
import net.feerie.creatura.shared.actions.ActionPopo;
import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.TypeAction;
import net.feerie.creatura.shared.creature.ia.filtresEntites.FiltreTypeEntite;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * L'IABasique de base/debug d'une créature
 * 
 * @author greewi
 */
public class IABasique implements IA
{
	private final Creature creature;
	private FiltreTypeEntite filtreGranules = new FiltreTypeEntite(TypeEntite.NOURRITURE_GRANULE);
	private FiltreTypeEntite filtreEau = new FiltreTypeEntite(TypeEntite.EAU);
	private FiltreTypeEntite filtreLitiere = new FiltreTypeEntite(TypeEntite.LITIERE);
	
	public IABasique(Creature creature)
	{
		this.creature = creature;
	}
	
	@Override
	public Action decideProchaineAction()
	{
		if (creature.getActionActuelle() != null && creature.getActionActuelle().getType() != TypeAction.DORMIR)
			return creature.getActionActuelle();
		
		if (creature.estAffectePar(TypeMoodle.FAIM))
		{
			Entite nourriture = creature.cherche(filtreGranules);
			if (nourriture != null)
				return new ActionMarcherVersEntite(creature, nourriture, new ActionManger(creature, nourriture));
		}
		if (creature.estAffectePar(TypeMoodle.SOIF))
		{
			Entite eau = creature.cherche(filtreEau);
			if (eau != null)
				return new ActionMarcherVersEntite(creature, eau, new ActionBoire(creature, eau));
		}
		
		if (creature.getActionActuelle() != null)
			return creature.getActionActuelle();
		
		if (creature.estAffectePar(TypeMoodle.POPO))
		{
			Entite litiere = creature.cherche(filtreLitiere);
			if (litiere != null)
				return new ActionMarcher(creature, litiere.position, new ActionPopo(creature, litiere));
		}
		if (creature.estAffectePar(TypeMoodle.FATIGUE))
		{
			return new ActionDormir(creature);
		}
		if (creature.estAffectePar(TypeMoodle.ENNUI))
		{
			List<Entite> entites = creature.getMonde().getListeEntites();
			Entite entite = entites.get(Random.nextInt(entites.size()));
			return new ActionMarcherVersEntite(creature, entite);
		}
		
		return null;
	}
}
