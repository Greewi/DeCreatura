package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;

/**
 * Repr�sente un d�chet (une crotte)
 * 
 * @author greewi
 */
public class Dechet extends Entite
{
	private double quantite;
	
	/**
	 * @param monde le monde de cette entit�
	 * @param quantite la quantit� de d�chet
	 * @param position la position de cette entit�
	 */
	public Dechet(Monde monde, double quantite, Position position)
	{
		super(monde, position, new Dimension(quantite / 1000.0, quantite / 1000.0));
		this.quantite = quantite;
	}
	
	/**
	 * @returnla quantit� de d�chet contenu dans ce d�chet
	 */
	public double getQuantite()
	{
		return quantite;
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.DECHET;
	}
	
	@Override
	public void metAJour(int frame)
	{
		//Inerte
	}
	
}
