package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Représente de la nourriture
 * 
 * @author greewi
 */
public class EntiteNourriture extends Entite
{
	private final TypeEntite type;
	
	/**
	 * @param monde le monde de l'entité
	 * @param position la position de l'entité
	 * @param type le type de cette nourriture
	 */
	public EntiteNourriture(Monde monde, Position position, TypeEntite type)
	{
		super(monde, position, new Dimension(20, 20));
		this.type = type;
	}
	
	@Override
	public String active(boolean activeParJoueur)
	{
		if (activeParJoueur)
			detruit();
		return null;
	}
	
	@Override
	public TypeEntite getType()
	{
		return type;
	}
	
	@Override
	public void effectueTic()
	{
		super.effectueTic();
		appliqueGravite();
	}
}
