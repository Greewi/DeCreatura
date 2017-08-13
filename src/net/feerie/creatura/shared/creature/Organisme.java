package net.feerie.creatura.shared.creature;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.organes.Organe;
import net.feerie.creatura.shared.creature.organes.TypeOrgane;

/**
 * Repr�sente un organisme, un objet vivant compos� d'organes. C'est la partie
 * biologique d'une cr�ature mais aussi des plantes et des autres entit�s
 * vivantes.
 * 
 * @author greewi
 */
public class Organisme
{
	private final EnumMap<TypeOrgane, Organe> organes;
	
	public Organisme()
	{
		// TODO Auto-generated constructor stub
		organes = new EnumMap<>(TypeOrgane.class);
	}
	
	/**
	 * Renvoie l'un des organes de l'organisme
	 * 
	 * @param typeOrgane le type de l'organe d�sir�
	 * @return l'organe correspondant au type pass� en param�tre ou
	 *         <tt>null</tt> si aucun organe de ce type n'a �t� trouv� dans cet
	 *         organisme.
	 */
	public Organe getOrgane(TypeOrgane typeOrgane)
	{
		return organes.get(typeOrgane);
	}
	
}
