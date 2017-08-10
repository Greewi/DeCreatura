package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Rectangle;

/**
 * Repr�sente une liti�re (l� o� on esp�re que la cr�ature fera ses besoins)
 * 
 * @author greewi
 */
public class Litiere extends Entite
{
	private final Monde monde;
	private double capacite;
	private double contenu;
	
	/**
	 * @param monde le monde ce cette entit�
	 * @param capacite la capacit� de cette liti�re
	 * @param position la potion de l'entit�
	 */
	public Litiere(Monde monde, int capacite, Position position)
	{
		super(monde, position, new Dimension(10.0, 10.0));
		this.monde = monde;
		this.capacite = capacite;
		this.contenu = 0;
	}
	
	/**
	 * @return la capacit� de cette litiere
	 */
	public double getCapacite()
	{
		return capacite;
	}
	
	/**
	 * @return le contenu de cette litiere
	 */
	public double getContenu()
	{
		return contenu;
	}
	
	/**
	 * Ajoute un d�chet dans la liti�re. Si la liti�re est pleine, cr�e un
	 * d�chet dans le monde.
	 * 
	 * @param quantite la quantit� de d�chet � cr�er
	 */
	public void ajouteDechets(double quantite)
	{
		//Si la liti�re n'est pas pleine on stock le d�chet
		if (contenu + quantite <= capacite)
			contenu += quantite;
		//Si la litiere est pleine on cr�� un d�chet dans le monde
		else
		{
			Position positionDechet = Rectangle.creeDepuisCentre(getPosition(), getTaille()).getPositionAleatoire();
			monde.ajouteEntite(new Dechet(monde, quantite, positionDechet));
		}
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.LITIERE;
	}
	
	@Override
	public void metAJour(int frame)
	{
		//Inerte
	}
}
