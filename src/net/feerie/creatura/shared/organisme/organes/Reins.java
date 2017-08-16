package net.feerie.creatura.shared.organisme.organes;

import static net.feerie.creatura.shared.organisme.Substance.*;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cet organe extrait les Toxines du système sanguin et les éjecte lors que la
 * créature fait ses besoins.
 * 
 * @author greewi
 */
public class Reins extends Organe
{
	/**
	 * Quantité de substance collectée par cycle dans les reins
	 */
	private static final int QUANTITE_PAR_CYCLE = 40;
	/**
	 * Quantité de glucide consommé par cycle de digestion
	 */
	static final int COUT_PAR_CYCLE = 1;
	/**
	 * La liste des substances collecteés dans les reins
	 */
	private static final EnumSet<Substance> SUBSTANCES_FILTREES = EnumSet.of(TOXINES);
	
	private final int capacite;
	
	/**
	 * @param creature la créature à laquelle appartient cet estomac
	 * @param capacite la capacité des reins (au delà les reins ne collectent
	 *        plus les toxines)
	 */
	public Reins(Organisme organisme, int capacite)
	{
		super(organisme, TypeOrgane.REINS);
		this.capacite = capacite;
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		int totalSubstance = 0;
		for (Substance substance : Substance.values())
			totalSubstance += getSubstance(substance);
		
		Organe systemeSanguin = getOrganisme().getOrgane(TypeOrgane.SYSTEME_SANGUIN);
		
		//On consomme le glucide
		systemeSanguin.ajouteSubstance(GLUCIDES, -COUT_PAR_CYCLE);
		systemeSanguin.ajouteSubstance(TOXINES, COUT_PAR_CYCLE);
		
		// On collecte les toxines stockés dans le systeme sanguin
		int totalACollecter = QUANTITE_PAR_CYCLE > capacite - totalSubstance ? capacite - totalSubstance : QUANTITE_PAR_CYCLE;
		totalSubstance += systemeSanguin.transfereSubstancesVersOrgane(SUBSTANCES_FILTREES, totalACollecter, this);
	}
}
