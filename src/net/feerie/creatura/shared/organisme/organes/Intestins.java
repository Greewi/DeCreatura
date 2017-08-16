package net.feerie.creatura.shared.organisme.organes;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cette classe récupère les déchets issus de la digestion mais aussi filtrés
 * par les reins. Ces déchets sont stockés en attendant que la créature les
 * évacues.
 * 
 * @author greewi
 */
public class Intestins extends Organe
{
	/**
	 * Quantité de substance collectée par cycle dans les reins
	 */
	static final int QUANTITE_PAR_CYCLE_REINS = 40;
	
	/**
	 * La liste des substances collecteés dans les reins
	 */
	static final EnumSet<Substance> SUBSTANCES_REINS = EnumSet.allOf(Substance.class);
	
	/**
	 * Quantité de substance collectée par cycle dans l'estomac
	 */
	static final int QUANTITE_PAR_CYCLE_ESTOMAC = 40;
	/**
	 * La liste des substances collecteés dans l'estomac
	 */
	static final EnumSet<Substance> SUBSTANCES_ESTOMAC = EnumSet.complementOf(Estomac.SUBSTANCES_A_DIGERER);
	
	private final int capacite;
	
	/**
	 * @param creature la créature à laquelle appartient cet estomac
	 * @param capacite la capacité des intestins (au delà l'intestin ne collecte
	 *        plus les déchets)
	 */
	public Intestins(Organisme organisme, int capacite)
	{
		super(organisme, TypeOrgane.INTESTINS);
		this.capacite = capacite;
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		int totalSubstance = 0;
		for (Substance substance : Substance.values())
			totalSubstance += getSubstance(substance);
		
		// On récupère les déchets stockés dans les reins
		Organe reins = getOrganisme().getOrgane(TypeOrgane.REINS);
		int totalACollecter = QUANTITE_PAR_CYCLE_REINS > capacite - totalSubstance ? capacite - totalSubstance : QUANTITE_PAR_CYCLE_REINS;
		totalSubstance += reins.transfereSubstancesVersOrgane(SUBSTANCES_REINS, totalACollecter, this);
		
		// On récupère les déchets stockés dans l'estomac
		Organe estomac = getOrganisme().getOrgane(TypeOrgane.ESTOMAC);
		totalACollecter = QUANTITE_PAR_CYCLE_ESTOMAC > capacite - totalSubstance ? capacite - totalSubstance : QUANTITE_PAR_CYCLE_ESTOMAC;
		totalSubstance += estomac.transfereSubstancesVersOrgane(SUBSTANCES_ESTOMAC, totalACollecter, this);
		
		// Mise à jour du système sensoriel
		SystemeSensoriel systemeSensoriel = (SystemeSensoriel) getOrganisme().getOrgane(TypeOrgane.SYSTEME_SENSORIEL);
		systemeSensoriel.setValeurCanal(TypeCanalSensoriel.INTESTINS, totalSubstance * 100 / capacite);
	}
}
