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
	private final TypeNourriture type;
	
	/**
	 * @param monde le monde de l'entité
	 * @param type le type de nourriture
	 * @param position la position de l'entit�
	 */
	public EntiteNourriture(Monde monde, TypeNourriture type, Position position)
	{
		super(monde, position, new Dimension(20, 20));
		this.type = type;
	}
	
	/**
	 * @return la couleur de cette nourriture
	 */
	public String getCouleur()
	{
		return type.getCouleur();
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.NOURRITURE;
	}
	
	@Override
	public void effectueTic()
	{
		super.effectueTic();
		appliqueGravite();
	}
}
