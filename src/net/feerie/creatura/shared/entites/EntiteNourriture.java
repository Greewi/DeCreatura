package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;

/**
 * Représente de la nourriture
 * 
 * @author greewi
 */
public class EntiteNourriture extends Entite
{
	TypeNourriture type;
	
	/**
	 * @param monde le monde de l'entité
	 * @param type le type de nourriture
	 * @param position la position de l'entit�
	 */
	public EntiteNourriture(Monde monde, TypeNourriture type, Position position)
	{
		super(monde, position, new Dimension(2.0, 2.0));
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
}
