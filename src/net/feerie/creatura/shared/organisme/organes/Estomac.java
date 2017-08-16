package net.feerie.creatura.shared.organisme.organes;

import static net.feerie.creatura.shared.organisme.Substance.CHOLECYSTOKININE;
import static net.feerie.creatura.shared.organisme.Substance.EAU;
import static net.feerie.creatura.shared.organisme.Substance.GLUCIDES;
import static net.feerie.creatura.shared.organisme.Substance.LIPIDES;
import static net.feerie.creatura.shared.organisme.Substance.PROTEINES;
import static net.feerie.creatura.shared.organisme.Substance.TOXINES;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.PaquetSubstance;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cet organe dig�re les aliments. C'est ici que va tout ce que mange la
 * cr�ature.
 * 
 * @author greewi
 */
public class Estomac extends Organe
{
	private int capacite;
	/**
	 * Quantit� de substance dig�r�e par cycle
	 */
	static final int QUANTITE_PAR_CYCLE = 40;
	/**
	 * Quantit� de glucide consomm� par cycle de digestion
	 */
	static final int COUT_DIGESTION = 3;
	/**
	 * La liste des substances dig�r�es par l'estomac
	 */
	static final EnumSet<Substance> SUBSTANCES_A_DIGERER = EnumSet.of(EAU, GLUCIDES, LIPIDES, PROTEINES);
	
	/**
	 * @param creature la cr�ature � laquelle appartient cet estomac
	 * @param capacite la capacit� de l'estomac (seuil de sati�t�)
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
	 * Calcule la stati�t� de l'organisme et met � jour le taux de
	 * {@link Substance#CHOLECYSTOKININE} pr�sent dans le syst�me sanguin
	 * 
	 * @param systemeSanguin le syst�me sanguin raccord� � cet estomac
	 */
	private void calculeSatiete(Organe systemeSanguin)
	{
		//Calcul de la quantit� de Cholecystokinine correspondant au remplissage de l'estomac
		int totalToutesSubstances = 0;
		for (Substance substance : Substance.values())
			totalToutesSubstances += getSubstance(substance);
		int quantiteAProduire = Math.min(totalToutesSubstances * 10 / capacite - systemeSanguin.getSubstance(CHOLECYSTOKININE), systemeSanguin.getSubstance(PROTEINES));
		
		//Si on doit produire de la Cholecystokinine on transforme des proteines
		if (quantiteAProduire > 0)
			systemeSanguin.transformeSubstance(PROTEINES, CHOLECYSTOKININE, quantiteAProduire);
	}
	
	/**
	 * Effectue la digestion proprement dite
	 * 
	 * @param systemeSanguin le syst�me sanguin raccord� � cet estomac
	 */
	private void effectueDigestion(Organe systemeSanguin)
	{
		// Calcul de la quantit� de substance pouvant �tre dig�r�e totale
		PaquetSubstance susbtancesADigerer = collecteSubstances(SUBSTANCES_A_DIGERER, QUANTITE_PAR_CYCLE);
		int totalSubstancesADigerer = 0;
		for (Substance substance : SUBSTANCES_A_DIGERER)
			totalSubstancesADigerer += susbtancesADigerer.get(substance);
		
		// S'il n'y a pas assez de nutriment � dig�rer ou pas assez de glucide dans le sang pour faire fonctionner l'estomac on ne fait rien
		if (totalSubstancesADigerer < QUANTITE_PAR_CYCLE)// || systemeSanguin.getSubstance(GLUCIDES) < COUT_DIGESTION)
			return;
		
		//On consomme le glucide
		systemeSanguin.transformeSubstance(GLUCIDES, TOXINES, COUT_DIGESTION);
		
		//On fait passer dans le sang les substances dig�r�es (proportionnellement aux quantit�s dans l'estomac)
		transfereSubstancesVersOrgane(SUBSTANCES_A_DIGERER, QUANTITE_PAR_CYCLE, systemeSanguin);
	}
}
