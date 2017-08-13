package net.feerie.creatura.shared.creature;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.organes.*;

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
		organes = new EnumMap<>(TypeOrgane.class);
		addOrgane(new Cerveau(this));
		addOrgane(new Estomac(this, 1000));
		addOrgane(new Peau(this));
		addOrgane(new Reins(this));
		addOrgane(new SystemeSensoriel(this));
		addOrgane(new SystemeSanguin(this));
		addOrgane(new SystemeMoteur(this));
		addOrgane(new TissuAdipeux(this));
	}
	
	/**
	 * Ajoute un organe à l'organisme
	 * 
	 * @param organe l'organe à ajouter
	 * @return <tt>true</tt> si le l'organe a été ajouté, <tt>false</tt> sinon
	 *         (notamment si un organe du même type existe déjà)
	 */
	private boolean addOrgane(Organe organe)
	{
		if (this.organes.containsKey(organe.getType()))
			return false;
		this.organes.put(organe.getType(), organe);
		return true;
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
