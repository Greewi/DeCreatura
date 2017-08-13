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
	private final int QUANTITE_PAR_CYCLE = 40;
	/**
	 * Quantit� de glucide consomm� par cycle de digestion
	 */
	private final int COUT_DIGESTION = 3;
	
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
		{
			systemeSanguin.ajouteSubstance(PROTEINES, -quantiteAProduire);
			systemeSanguin.ajouteSubstance(CHOLECYSTOKININE, quantiteAProduire);
		}
	}
	
	/**
	 * Effectue la digestion proprement dite
	 * 
	 * @param systemeSanguin le syst�me sanguin raccord� � cet estomac
	 */
	private void effectueDigestion(Organe systemeSanguin)
	{
		// Calcul de la quantit� de substance pouvant �tre dig�r�e totale
		EnumSet<Substance> subtancesADigerer = EnumSet.of(EAU, GLUCIDES, LIPIDES, PROTEINES);
		int totaleSubstancesADigerer = 0;
		for (Substance substance : subtancesADigerer)
			totaleSubstancesADigerer += getSubstance(substance);
		
		// S'il n'y a pas assez de nutriment � dig�rer ou pas assez de glucide dans le sang pour faire fonctionner l'estomac on ne fait rien
		if (totaleSubstancesADigerer < QUANTITE_PAR_CYCLE || systemeSanguin.getSubstance(GLUCIDES) < COUT_DIGESTION)
			return;
		
		//On consomme le glucide
		systemeSanguin.ajouteSubstance(GLUCIDES, -COUT_DIGESTION);
		
		//On fait passer dans le sang les substances dig�r�es (proportionnellement aux quantit�s dans l'estomac)
		int sommeSubstanceTraitee = 0;
		int sommeSubstanceDigeree = 0;
		for (Substance substance : subtancesADigerer)
		{
			int substanceDansEstomac = getSubstance(substance);
			
			// Pour �viter des perces � cause des divisions enti�res on effectue le calcul suivant
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
