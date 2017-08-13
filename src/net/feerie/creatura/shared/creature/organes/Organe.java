package net.feerie.creatura.shared.creature.organes;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.Organisme;
import net.feerie.creatura.shared.creature.Substance;

/**
 * Défini les organes, les composants d'une créature
 * 
 * @author greewi
 */
public abstract class Organe
{
	private final EnumMap<Substance, Integer> substances;
	private final Organisme organisme;
	private final TypeOrgane type; 
	
	/**
	 * @param organisme l'organisme auquel appartient cet organe
	 */
	public Organe(Organisme organisme, TypeOrgane type)
	{
		this.organisme = organisme;
		this.type = type;
		this.substances = new EnumMap<>(Substance.class);
	}
	
	protected Organisme getOrganisme()
	{
		return organisme;
	}
	
	/**
	 * Renvoie la quantité d'une susbstance présente dans l'organe
	 * 
	 * @param substance la substance dont oon veut la quantité
	 * @return la quantité d'une susbstance présente dans l'organe
	 */
	public int getSubstance(Substance substance)
	{
		Integer quantite = substances.get(substance);
		if (quantite == null)
			return 0;
		return quantite.intValue();
	}
	
	/**
	 * Ajoute uen substance dans l'organe
	 * 
	 * @param substance la substance à ajouter
	 * @param quantite la quantité de substance à ajouter (si négatif, le
	 *        montant absolu sera retiré de l'organe)
	 */
	public void ajouteSubstance(Substance substance, int quantite)
	{
		int quantiteActuelle = getSubstance(substance);
		quantite += quantiteActuelle;
		if (quantite < 0)
			quantite = 0;
		substances.put(substance, quantite);
	}
	
	/**
	 * Renvoie le type de l'organe
	 * 
	 * @return le type de l'organe
	 */
	public TypeOrgane getType()
	{
		return type;
	}
	
	/**
	 * Effectue le cycle métabolique de l'organe (consommation/transformation
	 * des substances plus autres fonctions).
	 */
	public abstract void effectueCycleMetabolique();
}
