package net.feerie.creatura.shared.organisme.organes;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cet organe stock les graisses et apporte une insolation thermique
 * supplémentaire.
 * 
 * @author greewi
 */
public class TissuAdipeux extends Organe
{
	/**
	 * Quantité de substance digérée par cycle
	 */
	static final int QUANTITE_PAR_CYCLE = 40;
	
	public TissuAdipeux(Organisme organisme)
	{
		super(organisme, TypeOrgane.TISSU_ADIPEUX);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		Organe systemeSanguin = getOrganisme().getOrgane(TypeOrgane.SYSTEME_SANGUIN);
		//On récupère les lipides du sang
		systemeSanguin.transfertSubstance(Substance.LIPIDES, 50, this);
		
		/*
		//S'il y a beaucoup de glucides on les transforme en lipides
		if (systemeSanguin.getSubstance(Substance.GLUCIDES) > 200)
		{
			systemeSanguin.transfertSubstance(Substance.GLUCIDES, 20, this);
			this.transformeSubstance(Substance.GLUCIDES, Substance.LIPIDES, 20);
		}
		//S'il n'y a pas assez de glucide on en fabrique à partir des lipides
		if (systemeSanguin.getSubstance(Substance.GLUCIDES) < 50)
		{
			this.transformeSubstance(Substance.LIPIDES, Substance.GLUCIDES, 20);
			this.transfertSubstance(Substance.GLUCIDES, 20, systemeSanguin);
		}*/
	}
	
}
