package net.feerie.creatura.shared.organisme.organes;

import static net.feerie.creatura.shared.organisme.Substance.*;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cet organe extrait les Toxines du syst�me sanguin et les �jecte lors que la
 * cr�ature fait ses besoins.
 * 
 * @author greewi
 */
public class Reins extends Organe
{
	/**
	 * Quantit� de substance collect�e par cycle dans les reins
	 */
	private static final int QUANTITE_PAR_CYCLE = 40;
	/**
	 * Quantit� de glucide consomm� par cycle de digestion
	 */
	static final int COUT_PAR_CYCLE = 1;
	/**
	 * La liste des substances collecte�s dans les reins
	 */
	private static final EnumSet<Substance> SUBSTANCES_FILTREES = EnumSet.of(TOXINES);
	
	private final int capacite;
	
	/**
	 * @param creature la cr�ature � laquelle appartient cet estomac
	 * @param capacite la capacit� des reins (au del� les reins ne collectent
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
		systemeSanguin.transformeSubstance(GLUCIDES, TOXINES, COUT_PAR_CYCLE);
		
		// On collecte les toxines stock�s dans le systeme sanguin
		int totalACollecter = QUANTITE_PAR_CYCLE > capacite - totalSubstance ? capacite - totalSubstance : QUANTITE_PAR_CYCLE;
		totalSubstance += systemeSanguin.transfereSubstancesVersOrgane(SUBSTANCES_FILTREES, totalACollecter, this);
	}
}
