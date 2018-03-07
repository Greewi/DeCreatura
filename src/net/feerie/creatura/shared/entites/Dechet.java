package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;

/**
 * Représente un déchet (une crotte)
 * 
 * @author greewi
 */
public class Dechet extends Entite
{
	
	/**
	 * @param monde le monde de cette entité
	 * @param position la position de cette entité
	 */
	public Dechet(Monde monde, Position position)
	{
		super(monde, position, new Dimension(2, 2));
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.POPO;
	}
	
	@Override
	public void metAJour(int frame)
	{
		//Inerte
	}
	
}
