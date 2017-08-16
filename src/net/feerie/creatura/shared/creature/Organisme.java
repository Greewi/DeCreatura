package net.feerie.creatura.shared.creature;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.organes.*;

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
		organes = new EnumMap<>(TypeOrgane.class);
		addOrgane(new Cerveau(this));
		addOrgane(new Estomac(this, 1000));
		addOrgane(new Intestins(this, 1000));
		addOrgane(new Peau(this));
		addOrgane(new Reins(this, 1000));
		addOrgane(new SystemeSensoriel(this));
		addOrgane(new SystemeSanguin(this));
		addOrgane(new SystemeMoteur(this));
		addOrgane(new TissuAdipeux(this));
	}
	
	/**
	 * Ajoute un organe � l'organisme
	 * 
	 * @param organe l'organe � ajouter
	 * @return <tt>true</tt> si le l'organe a �t� ajout�, <tt>false</tt> sinon
	 *         (notamment si un organe du m�me type existe d�j�)
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
