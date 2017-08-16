package net.feerie.creatura.shared.organisme.organes;

import static net.feerie.creatura.shared.organisme.Substance.*;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.PaquetSubstance;
import net.feerie.creatura.shared.organisme.Substance;

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
	static final int QUANTITE_PAR_CYCLE = 40;
	/**
	 * Quantité de glucide consommé par cycle de digestion
	 */
	static final int COUT_DIGESTION = 3;
	/**
	 * La liste des substances digérées par l'estomac
	 */
	static final EnumSet<Substance> SUBSTANCES_A_DIGERER = EnumSet.of(EAU, GLUCIDES, LIPIDES, PROTEINES);
	
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
		PaquetSubstance susbtancesADigerer = collecteSubstances(SUBSTANCES_A_DIGERER, QUANTITE_PAR_CYCLE);
		int totaleSubstancesADigerer = 0;
		for (Substance substance : SUBSTANCES_A_DIGERER)
			totaleSubstancesADigerer += susbtancesADigerer.get(substance);
		
		// S'il n'y a pas assez de nutriment à digérer ou pas assez de glucide dans le sang pour faire fonctionner l'estomac on ne fait rien
		if (totaleSubstancesADigerer < QUANTITE_PAR_CYCLE || systemeSanguin.getSubstance(GLUCIDES) < COUT_DIGESTION)
			return;
		
		//On consomme le glucide
		systemeSanguin.ajouteSubstance(GLUCIDES, -COUT_DIGESTION);
		systemeSanguin.ajouteSubstance(TOXINES, COUT_DIGESTION);
		
		//On fait passer dans le sang les substances digérées (proportionnellement aux quantités dans l'estomac)
		transfereSubstancesVersOrgane(SUBSTANCES_A_DIGERER, QUANTITE_PAR_CYCLE, systemeSanguin);
	}
}
