package net.feerie.creatura.shared.creature;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.organes.Organe;
import net.feerie.creatura.shared.creature.organes.TypeOrgane;

/**
 * Représente un organisme, un objet vivant composé d'organes. C'est la partie
 * biologique d'une créature mais aussi des plantes et des autres entités
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
	 * @param typeOrgane le type de l'organe désiré
	 * @return l'organe correspondant au type passé en paramètre ou
	 *         <tt>null</tt> si aucun organe de ce type n'a été trouvé dans cet
	 *         organisme.
	 */
	public Organe getOrgane(TypeOrgane typeOrgane)
	{
		return organes.get(typeOrgane);
	}
	
}
