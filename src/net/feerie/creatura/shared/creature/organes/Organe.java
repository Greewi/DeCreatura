package net.feerie.creatura.shared.creature.organes;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.Organisme;
import net.feerie.creatura.shared.creature.Substance;

/**
 * D�fini les organes, les composants d'une cr�ature
 * 
 * @author greewi
 */
public abstract class Organe
{
	private final EnumMap<Substance, Integer> substances;
	private final Organisme organisme;
	
	/**
	 * @param organisme l'organisme auquel appartient cet organe
	 */
	public Organe(Organisme organisme)
	{
		this.organisme = organisme;
		this.substances = new EnumMap<>(Substance.class);
	}
	
	protected Organisme getOrganisme()
	{
		return organisme;
	}
	
	/**
	 * Renvoie la quantit� d'une susbstance pr�sente dans l'organe
	 * 
	 * @param substance la substance dont oon veut la quantit�
	 * @return la quantit� d'une susbstance pr�sente dans l'organe
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
	 * @param substance la substance � ajouter
	 * @param quantite la quantit� de substance � ajouter (si n�gatif, le
	 *        montant absolu sera retir� de l'organe)
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
	 * Effectue le cycle m�tabolique de l'organe (consommation/transformation
	 * des substances plus autres fonctions).
	 */
	public abstract void effectueCycleMetabolique();
}
