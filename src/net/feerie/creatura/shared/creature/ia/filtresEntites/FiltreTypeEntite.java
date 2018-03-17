package net.feerie.creatura.shared.creature.ia.filtresEntites;

import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Filtre des entités en ne conservant que celles d'un type particulier
 * 
 * @author greewi
 */
public class FiltreTypeEntite implements FiltreEntite
{
	private TypeEntite type;
	
	public FiltreTypeEntite(TypeEntite typeAConserver)
	{
		this.type = typeAConserver;
	}
	
	public void setTypeEntite(TypeEntite typeAConserver)
	{
		this.type = typeAConserver;
	}
	
	@Override
	public boolean teste(Entite entite)
	{
		return entite.getType() == type;
	}
	
}
