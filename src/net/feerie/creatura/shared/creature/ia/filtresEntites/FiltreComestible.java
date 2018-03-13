package net.feerie.creatura.shared.creature.ia.filtresEntites;

import net.feerie.creatura.shared.entites.Entite;

/**
 * Filtre les entités en ne conservant que celles qui peuvent petre mangée
 * 
 * @author greewi
 */
public class FiltreComestible implements FiltreEntite
{
	@Override
	public boolean teste(Entite entite)
	{
		return entite.getType().peutEtreMange();
	}
}
