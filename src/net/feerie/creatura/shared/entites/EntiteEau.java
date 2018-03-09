package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;

/**
 * Représente une source d'eau où la créature peut boire
 * 
 * @author greewi
 */
public class EntiteEau extends Entite
{
	public EntiteEau(Monde monde, Position position)
	{
		super(monde, position, new Dimension(30, 30));
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.EAU;
	}
}
