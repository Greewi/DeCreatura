package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Rectangle;

/**
 * Représente une litière (là ou on espère que la créature fera ses besoins)
 * 
 * @author greewi
 */
public class EntiteLitiere extends Entite
{
	private final Monde monde;
	private double capacite;
	private double contenu;
	
	/**
	 * @param monde le monde ce cette entité
	 * @param capacite la capacité de cette litière
	 * @param position la potion de l'entité
	 */
	public EntiteLitiere(Monde monde, int capacite, Position position)
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
	public void ajouteDechets()
	{
		//Si la litière n'est pas pleine on stock le déchet
		if (contenu + 1 <= capacite)
			contenu++;
		//Si la litiere est pleine on créé un déchet dans le monde
		else
		{
			Position positionDechet = Rectangle.creeDepuisCentre(getPosition(), getTaille()).getPositionAleatoire();
			monde.nouvelleEntite(new EntiteDechet(monde, positionDechet));
		}
	}
	
	/**
	 * Vide la litière
	 */
	public void vide()
	{
		contenu = 0;
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.LITIERE;
	}
}
