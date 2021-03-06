package net.feerie.creatura.shared.creature.ia;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.ActionMarcherVersEntite;
import net.feerie.creatura.shared.actions.ActionNuisibleManger;
import net.feerie.creatura.shared.creature.ia.filtresEntites.FiltreComestible;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.CreatureNuisible;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

public class IANuisible implements IA
{
	private final FiltreComestible filtreEntiteCommestibles = new FiltreComestible();
	private final CreatureNuisible creature;
	
	public IANuisible(CreatureNuisible creature)
	{
		this.creature = creature;
	}
	
	@Override
	public Action decideProchaineAction()
	{
		if (creature.getActionActuelle() != null)
			return creature.getActionActuelle();
		
		Entite nourriture = creature.cherche(filtreEntiteCommestibles);
		
		if (nourriture != null)
			return new ActionMarcherVersEntite(creature, nourriture, new ActionNuisibleManger(nourriture));
		return null;
	}
	
	@Override
	public void encourage()
	{
		// Sans effet sur les nuisibles
	}
	
	@Override
	public void gronde()
	{
		// Sans effet sur les nuisibles
	}

	@Override
	public void constateNouvelleEntite(TypeEntite entite)
	{
		// Sans effet sur les nuisibles
	}

	@Override
	public void constateApparitionMoodle(TypeMoodle moodle)
	{
		// Sans effet sur les nuisibles
	}

	@Override
	public void constateDisparitionMoodle(TypeMoodle moodle)
	{
		// Sans effet sur les nuisibles
	}
}
