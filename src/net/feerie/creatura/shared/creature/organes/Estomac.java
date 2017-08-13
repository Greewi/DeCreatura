package net.feerie.creatura.shared.creature.organes;

import static net.feerie.creatura.shared.creature.Substance.CHOLECYSTOKININE;
import static net.feerie.creatura.shared.creature.Substance.EAU;
import static net.feerie.creatura.shared.creature.Substance.GLUCIDES;
import static net.feerie.creatura.shared.creature.Substance.LIPIDES;
import static net.feerie.creatura.shared.creature.Substance.PROTEINES;

import java.util.EnumSet;

import net.feerie.creatura.shared.creature.Organisme;
import net.feerie.creatura.shared.creature.Substance;

/**
 * Cet organe digère les aliments. C'est ici que va tout ce que mange la
 * créature.
 * 
 * @author greewi
 */
public class Estomac extends Organe
{
	private int capacite;
	/**
	 * Quantité de substance digérée par cycle
	 */
	private final int QUANTITE_PAR_CYCLE = 40;
	/**
	 * Quantité de glucide consommé par cycle de digestion
	 */
	private final int COUT_DIGESTION = 3;
	
	/**
	 * @param creature la créature à laquelle appartient cet estomac
	 * @param capacite la capacité de l'estomac (seuil de satiété)
	 */
	public Estomac(Organisme organisme, int capacite)
	{
		super(organisme, TypeOrgane.ESTOMAC);
		this.capacite = capacite;
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		Organe systemeSanguin = getOrganisme().getOrgane(TypeOrgane.SYSTEME_SANGUIN);
		if (systemeSanguin == null)
			return;
		
		calculeSatiete(systemeSanguin);
		effectueDigestion(systemeSanguin);
	}
	
	/**
	 * Calcule la statiété de l'organisme et met à jour le taux de
	 * {@link Substance#CHOLECYSTOKININE} présent dans le système sanguin
	 * 
	 * @param systemeSanguin le système sanguin raccordé à cet estomac
	 */
	private void calculeSatiete(Organe systemeSanguin)
	{
		//Calcul de la quantité de Cholecystokinine correspondant au remplissage de l'estomac
		int totalToutesSubstances = 0;
		for (Substance substance : Substance.values())
			totalToutesSubstances += getSubstance(substance);
		int quantiteAProduire = Math.min(totalToutesSubstances * 10 / capacite - systemeSanguin.getSubstance(CHOLECYSTOKININE), systemeSanguin.getSubstance(PROTEINES));
		
		//Si on doit produire de la Cholecystokinine on transforme des proteines
		if (quantiteAProduire > 0)
		{
			systemeSanguin.ajouteSubstance(PROTEINES, -quantiteAProduire);
			systemeSanguin.ajouteSubstance(CHOLECYSTOKININE, quantiteAProduire);
		}
	}
	
	/**
	 * Effectue la digestion proprement dite
	 * 
	 * @param systemeSanguin le système sanguin raccordé à cet estomac
	 */
	private void effectueDigestion(Organe systemeSanguin)
	{
		// Calcul de la quantité de substance pouvant être digérée totale
		EnumSet<Substance> subtancesADigerer = EnumSet.of(EAU, GLUCIDES, LIPIDES, PROTEINES);
		int totaleSubstancesADigerer = 0;
		for (Substance substance : subtancesADigerer)
			totaleSubstancesADigerer += getSubstance(substance);
		
		// S'il n'y a pas assez de nutriment à digérer ou pas assez de glucide dans le sang pour faire fonctionner l'estomac on ne fait rien
		if (totaleSubstancesADigerer < QUANTITE_PAR_CYCLE || systemeSanguin.getSubstance(GLUCIDES) < COUT_DIGESTION)
			return;
		
		//On consomme le glucide
		systemeSanguin.ajouteSubstance(GLUCIDES, -COUT_DIGESTION);
		
		//On fait passer dans le sang les substances digérées (proportionnellement aux quantités dans l'estomac)
		int sommeSubstanceTraitee = 0;
		int sommeSubstanceDigeree = 0;
		for (Substance substance : subtancesADigerer)
		{
			int substanceDansEstomac = getSubstance(substance);
			
			// Pour éviter des perces à cause des divisions entières on effectue le calcul suivant
			int totalNormalementDigere = (sommeSubstanceTraitee + substanceDansEstomac) * QUANTITE_PAR_CYCLE / totaleSubstancesADigerer;
			int quantiteADigerer = totalNormalementDigere - sommeSubstanceDigeree;
			sommeSubstanceTraitee += substanceDansEstomac;
			sommeSubstanceDigeree += quantiteADigerer;
			
			// On transfert effectivement la substance
			ajouteSubstance(substance, -quantiteADigerer);
			systemeSanguin.ajouteSubstance(substance, quantiteADigerer);
		}
	}
}
