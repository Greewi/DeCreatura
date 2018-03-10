package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Représente une source d'eau où la créature peut boire
 * 
 * @author greewi
 */
public class EntiteEau extends Entite
{
	public EntiteEau(Monde monde, Position position, Dimension dimension)
	{
		super(monde, position, dimension);
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.EAU;
	}
}
