package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Rectangle;

/**
 * Représente une litière (là où on espère que la créature fera ses besoins)
 * 
 * @author greewi
 */
public class Litiere extends Entite
{
	private final Monde monde;
	private double capacite;
	private double contenu;
	
	/**
	 * @param monde le monde ce cette entité
	 * @param capacite la capacité de cette litière
	 * @param position la potion de l'entité
	 */
	public Litiere(Monde monde, int capacite, Position position)
	{
		super(monde, position, new Dimension(10.0, 10.0));
		this.monde = monde;
		this.capacite = capacite;
		this.contenu = 0;
	}
	
	/**
	 * @return la capacité de cette litiere
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
	 * Ajoute un déchet dans la litière. Si la litière est pleine, crée un
	 * déchet dans le monde.
	 * 
	 * @param quantite la quantité de déchet à créer
	 */
	public void ajouteDechets(double quantite)
	{
		//Si la litière n'est pas pleine on stock le déchet
		if (contenu + quantite <= capacite)
			contenu += quantite;
		//Si la litiere est pleine on créé un déchet dans le monde
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
