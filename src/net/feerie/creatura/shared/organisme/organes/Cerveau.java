package net.feerie.creatura.shared.organisme.organes;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cet organe représente le centre décisionnel d'une créature.
 * 
 * @author greewi
 */
public class Cerveau extends Organe
{
	private final static int COUT_PAR_CYCLE = 4;
	
	public Cerveau(Organisme organisme)
	{
		super(organisme, TypeOrgane.CERVEAU);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		Organe systemeSanguin = getOrganisme().getOrgane(TypeOrgane.SYSTEME_SANGUIN);
		systemeSanguin.transformeSubstance(Substance.GLUCIDES, Substance.TOXINES, COUT_PAR_CYCLE);
		systemeSanguin.transformeSubstance(Substance.NORALDRENALINE, Substance.TOXINES, 1);
	}
}
