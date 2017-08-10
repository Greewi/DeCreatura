package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;

/**
 * Repr�sente de la nourriture
 * 
 * @author greewi
 */
public class Nourriture extends Entite
{
	TypeNourriture type;
	
	/**
	 * @param monde le monde de l'entit�
	 * @param type le type de nourriture
	 * @param position la position de l'entit�
	 */
	public Nourriture(Monde monde, TypeNourriture type, Position position)
	{
		super(monde, position, new Dimension(2.0, 2.0));
		this.type = type;
	}
	
	/**
	 * @return la quantit� d'eau contenue dans ce type de nourriture
	 */
	public int getEau()
	{
		return type.getEau();
	}
	
	/**
	 * @return la quantit� de sucres contenue dans ce type de nourriture
	 */
	public int getSucres()
	{
		return type.getSucres();
	}
	
	/**
	 * @return la quantit� de proteines contenue dans ce type de nourriture
	 */
	public int getProteines()
	{
		return type.getProteines();
	}
	
	/**
	 * @return la quantit� de gras contenue dans ce type de nourriture
	 */
	public int getGras()
	{
		return type.getGras();
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
	public void metAJour(int frame)
	{
		//On verra pour la nourriture qui se sauve plus tard...
	}
	
}
