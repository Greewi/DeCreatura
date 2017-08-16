package net.feerie.creatura.shared.creature.organes;

import java.util.EnumSet;

import net.feerie.creatura.shared.creature.Organisme;
import net.feerie.creatura.shared.creature.PaquetSubstance;
import net.feerie.creatura.shared.creature.Substance;

/**
 * D�fini les organes, les composants d'une cr�ature
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
	 * Renvoie la quantit� d'une susbstance pr�sente dans l'organe
	 * 
	 * @param substance la substance dont oon veut la quantit�
	 * @return la quantit� d'une susbstance pr�sente dans l'organe
	 */
	public int getSubstance(Substance substance)
	{
		return substances.get(substance);
	}
	
	/**
	 * Ajoute une substance dans l'organe
	 * 
	 * @param substance la {@link Substance} � ajouter
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
	 * D�fini la quantit� de sustance dans l'organe
	 * 
	 * @param substance la {@link Substance} � modifier
	 * @param quantite la quantit� de sustance � d�finir
	 */
	public void setSubstance(Substance substance, int quantite)
	{
		substances.put(substance, quantite);
	}
	
	/**
	 * R�cup�re une liste de substances depuis un sous ensemble. Les substances
	 * d�sir�es sont donn�es en param�tre ainsi que la quantit� maximale totale
	 * � renvoyer. Cette m�thode ne modifie pas les quantit�s de substance
	 * contenues dans l'organe.
	 * 
	 * @param substancesACollecter l'ensemble des substances � collecter
	 * @param quantiteTotalACollecter la quantit� totale de substance � renvoyer
	 *        (la somme de toutes les substances renvoy�s)
	 * @return les substances collect�es
	 */
	public PaquetSubstance collecteSubstances(EnumSet<Substance> substancesACollecter, int quantiteTotalACollecter)
	{
		PaquetSubstance substancesCollectees = new PaquetSubstance();
		if (quantiteTotalACollecter < 0)
			quantiteTotalACollecter = 0;
		
		///On calcule la quantit� totale des substances demand�es dans l'organe
		int totalSubstancesDansOrgane = 0;
		for (Substance substance : substancesACollecter)
			totalSubstancesDansOrgane += getSubstance(substance);
		
		//On collecte les substances
		int sommeSubstancesTraitees = 0;
		int sommeSubstancesCollectees = 0;
		for (Substance substance : substancesACollecter)
		{
			int substanceDansOrgane = getSubstance(substance);
			
			// Pour �viter des pertes � cause des divisions enti�res on effectue le calcul suivant
			// On calcule la quantit� totale normalent collect�e apr�s ce tour de boucle et on retire ce qui a d�j� �t� collect�
			int quantiteCollecteeProchaine = (sommeSubstancesTraitees + substanceDansOrgane) * quantiteTotalACollecter / totalSubstancesDansOrgane;
			int quantiteACollecter = quantiteCollecteeProchaine - sommeSubstancesCollectees;
			
			// Mise � jour des compteurs
			sommeSubstancesTraitees += substanceDansOrgane;
			sommeSubstancesCollectees += quantiteACollecter;
			
			// On collecte la substance
			substancesCollectees.put(substance, quantiteACollecter);
		}
		
		return substancesCollectees;
	}
	
	/**
	 * Transfere une certaine quantit� de substance parmi une liste donn�e vers
	 * un autre organe.
	 * 
	 * @param substancesATransferer la liste des substances � transf�rer
	 * @param quantiteTotalATransferer la quantit� totale � transf�rer (les
	 *        substances seront transf�rer au pro-rata de cet organe)
	 * @param organe l'organe vers lequel transf�rer les substances
	 * @return la quantit� totale de substances effectivement transf�r�e
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
	 * Effectue le cycle m�tabolique de l'organe (consommation/transformation
	 * des substances plus autres fonctions).
	 */
	public abstract void effectueCycleMetabolique();
}
