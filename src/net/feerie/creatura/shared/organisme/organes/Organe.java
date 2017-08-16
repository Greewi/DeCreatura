package net.feerie.creatura.shared.organisme.organes;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.PaquetSubstance;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Défini les organes, les composants d'une créature
 * 
 * @author greewi
 */
public abstract class Organe
{
	private final PaquetSubstance substances;
	private final Organisme organisme;
	private final TypeOrgane type;
	
	/**
	 * @param organisme l'organisme auquel appartient cet organe
	 */
	public Organe(Organisme organisme, TypeOrgane type)
	{
		this.organisme = organisme;
		this.type = type;
		this.substances = new PaquetSubstance();
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
		return substances.get(substance);
	}
	
	/**
	 * Ajoute une substance dans l'organe
	 * 
	 * @param substance la {@link Substance} à ajouter
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
	 * Transforme une certaine quantite de substance en une autre
	 * 
	 * @param substanceConsomme la substance qui sera transformée
	 * @param substanceProduite la susbtance produite
	 * @param quantite la quantité de substance à convertir
	 */
	public void transformeSubstance(Substance substanceConsomme, Substance substanceProduite, int quantite)
	{
		if (quantite <= 0)
			return;
		
		int quantiteActuelle = getSubstance(substanceConsomme);
		if (quantite > quantiteActuelle)
			quantite = quantiteActuelle;
		
		this.ajouteSubstance(substanceConsomme, -quantite);
		this.ajouteSubstance(substanceProduite, quantite);
	}
	
	/**
	 * Transfert une quantite de substance de cet organe vers un autre
	 * 
	 * @param substance la substance à transférer
	 * @param quantite la quantité de susbtance à transférer
	 * @param destination l'organe vers lequel transférer la substance
	 */
	public void transfertSubstance(Substance substance, int quantite, Organe destination)
	{
		if (quantite <= 0)
			return;
		
		int quantiteActuelle = getSubstance(substance);
		if (quantite > quantiteActuelle)
			quantite = quantiteActuelle;
		
		this.ajouteSubstance(substance, -quantite);
		destination.ajouteSubstance(substance, -quantite);
	}
	
	/**
	 * Récupère une liste de substances depuis un sous ensemble. Les substances
	 * désirées sont données en paramètre ainsi que la quantité maximale totale
	 * à renvoyer. Cette méthode ne modifie pas les quantités de substance
	 * contenues dans l'organe.
	 * 
	 * @param substancesACollecter l'ensemble des substances à collecter
	 * @param quantiteTotalACollecter la quantité totale de substance à renvoyer
	 *        (la somme de toutes les substances renvoyés)
	 * @return les substances collectées
	 */
	public PaquetSubstance collecteSubstances(EnumSet<Substance> substancesACollecter, int quantiteTotalACollecter)
	{
		PaquetSubstance substancesCollectees = new PaquetSubstance();
		if (quantiteTotalACollecter < 0)
			quantiteTotalACollecter = 0;
		
		///On calcule la quantité totale des substances demandées dans l'organe
		int totalSubstancesDansOrgane = 0;
		for (Substance substance : substancesACollecter)
			totalSubstancesDansOrgane += getSubstance(substance);
		
		//On collecte les substances
		int sommeSubstancesTraitees = 0;
		int sommeSubstancesCollectees = 0;
		for (Substance substance : substancesACollecter)
		{
			int substanceDansOrgane = getSubstance(substance);
			
			// Pour éviter des pertes à cause des divisions entières on effectue le calcul suivant
			// On calcule la quantité totale normalent collectée après ce tour de boucle et on retire ce qui a déjà été collecté
			int quantiteCollecteeProchaine = (sommeSubstancesTraitees + substanceDansOrgane) * quantiteTotalACollecter / totalSubstancesDansOrgane;
			int quantiteACollecter = quantiteCollecteeProchaine - sommeSubstancesCollectees;
			
			// Mise à jour des compteurs
			sommeSubstancesTraitees += substanceDansOrgane;
			sommeSubstancesCollectees += quantiteACollecter;
			
			// On collecte la substance
			substancesCollectees.put(substance, quantiteACollecter);
		}
		
		return substancesCollectees;
	}
	
	/**
	 * Transfere une certaine quantité de substance parmi une liste donnée vers
	 * un autre organe.
	 * 
	 * @param substancesATransferer la liste des substances à transférer
	 * @param quantiteTotalATransferer la quantité totale à transférer (les
	 *        substances seront transférer au pro-rata de cet organe)
	 * @param organe l'organe vers lequel transférer les substances
	 * @return la quantité totale de substances effectivement transférée
	 */
	public int transfereSubstancesVersOrgane(EnumSet<Substance> substancesATransferer, int quantiteTotalATransferer, Organe organe)
	{
		PaquetSubstance substancesCollectees = collecteSubstances(substancesATransferer, quantiteTotalATransferer);
		int quantiteTotale = 0;
		for (Substance substance : substancesATransferer)
		{
			int quantite = substancesCollectees.get(substance);
			this.ajouteSubstance(substance, -quantite);
			organe.ajouteSubstance(substance, quantite);
			quantiteTotale += quantite;
		}
		return quantiteTotale;
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
