package net.feerie.creatura.shared.organisme;

import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.CERVEAU;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.ESTOMAC;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.INTESTINS;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.PEAU;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.REINS;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.SYSTEME_MOTEUR;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.SYSTEME_SANGUIN;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.SYSTEME_SENSORIEL;
import static net.feerie.creatura.shared.organisme.organes.TypeOrgane.TISSU_ADIPEUX;

import java.util.EnumMap;

import net.feerie.creatura.shared.organisme.organes.Cerveau;
import net.feerie.creatura.shared.organisme.organes.Estomac;
import net.feerie.creatura.shared.organisme.organes.Intestins;
import net.feerie.creatura.shared.organisme.organes.Organe;
import net.feerie.creatura.shared.organisme.organes.Peau;
import net.feerie.creatura.shared.organisme.organes.Reins;
import net.feerie.creatura.shared.organisme.organes.SystemeMoteur;
import net.feerie.creatura.shared.organisme.organes.SystemeSanguin;
import net.feerie.creatura.shared.organisme.organes.TissuAdipeux;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;
import net.feerie.creatura.shared.organisme.sens.SystemeSensoriel;

/**
 * Représente un organisme, un objet vivant composé d'organes. C'est la partie
 * biologique d'une créature mais aussi des plantes et des autres entités
 * vivantes.
 * 
 * @author greewi
 */
public class Organisme
{
	/**
	 * Defini l'ordre dans lequel les organes effectuent leur cycle métabolique
	 */
	private final static TypeOrgane[] PRIORITE_METABOLISME = new TypeOrgane[] { ESTOMAC, PEAU, SYSTEME_SENSORIEL, TISSU_ADIPEUX, CERVEAU, SYSTEME_MOTEUR, SYSTEME_SANGUIN, REINS, INTESTINS };
	
	/**
	 * Les organes de cet organisme
	 */
	private final EnumMap<TypeOrgane, Organe> organes;
	
	/**
	 * La santé de l'organisme (0 mort, 100 pleine forme)
	 */
	private int sante;
	
	public Organisme()
	{
		this.sante = 100;
		
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
		
		Organe systemeSanguin = getOrgane(SYSTEME_SANGUIN);
		systemeSanguin.ajouteSubstance(Substance.GLUCIDES, 100);
	}
	
	public int getSante()
	{
		return sante;
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
	
	/**
	 * Effectue le cycle métabolique de l'organisme
	 */
	public void effectueCycleMetabolique()
	{
		// On exécute le cycle métabolique de tous les organes
		for (TypeOrgane typeOrgane : PRIORITE_METABOLISME)
		{
			Organe organe = getOrgane(typeOrgane);
			if (organe != null)
				organe.effectueCycleMetabolique();
		}
		
		//Recalcul de la santé
		Organe systemeSanguin = getOrgane(SYSTEME_SANGUIN);
		if(systemeSanguin==null)
			this.sante = 0;
		else
		{
			this.sante = 100;
			int eau = systemeSanguin.getSubstance(Substance.EAU);
			int glucides = systemeSanguin.getSubstance(Substance.GLUCIDES);
					
			if(eau == 0)
				this.sante -= 75;
			if(glucides == 0)
				this.sante -= 50;
		}
		if(this.sante<0)
			this.sante=0;
	}
}
