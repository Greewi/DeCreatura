package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.ia.IANuisible;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Représente une créature nuisible (celles qui mangent tout ce qui traine et
 * apparaissent s'il y a trop de nourriture ou de déchets qui trainent).
 * 
 * @author greewi
 */
public class CreatureNuisible extends EntiteCreature
{
	public CreatureNuisible(Monde monde, Position position)
	{
		super(monde, position, new Dimension(30, 30));
		setIA(new IANuisible(this));
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.CREATURE_NUISIBLE;
	}
	
	@Override
	public boolean estVivante()
	{
		return true;
	}
	
	@Override
	public String active(EntiteCreature activateur)
	{
		detruit();
		//TODO IA disparition entite
		return null;
	}
}
