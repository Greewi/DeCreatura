package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Représente un déchet (une crotte)
 * 
 * @author greewi
 */
public class EntiteDechet extends Entite
{
	
	/**
	 * @param monde le monde de cette entité
	 * @param position la position de cette entité
	 */
	public EntiteDechet(Monde monde, Position position)
	{
		super(monde, position, new Dimension(20, 20));
	}
	
	@Override
	public String active(boolean activeParJoueur)
	{
		if (activeParJoueur)
			this.detruit();
		return super.active(activeParJoueur);
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.POPO;
	}
	
	@Override
	public void effectueTic()
	{
		super.effectueTic();
		appliqueGravite();
	}
}
