package net.feerie.creatura.shared.organisme.organes;

import java.util.EnumSet;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;

/**
 * Cette classe r�cup�re les d�chets issus de la digestion mais aussi filtr�s
 * par les reins. Ces d�chets sont stock�s en attendant que la cr�ature les
 * �vacues.
 * 
 * @author greewi
 */
public class Intestins extends Organe
{
	/**
	 * Quantit� de substance collect�e par cycle dans les reins
	 */
	static final int QUANTITE_PAR_CYCLE_REINS = 40;
	
	/**
	 * La liste des substances collecte�s dans les reins
	 */
	static final EnumSet<Substance> SUBSTANCES_REINS = EnumSet.allOf(Substance.class);
	
	/**
	 * Quantit� de substance collect�e par cycle dans l'estomac
	 */
	static final int QUANTITE_PAR_CYCLE_ESTOMAC = 40;
	/**
	 * La liste des substances collecte�s dans l'estomac
	 */
	static final EnumSet<Substance> SUBSTANCES_ESTOMAC = EnumSet.complementOf(Estomac.SUBSTANCES_A_DIGERER);
	
	private final int capacite;
	
	/**
	 * @param creature la cr�ature � laquelle appartient cet estomac
	 * @param capacite la capacit� des intestins (au del� l'intestin ne collecte
	 *        plus les d�chets)
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
		
		// On r�cup�re les d�chets stock�s dans les reins
		Organe reins = getOrganisme().getOrgane(TypeOrgane.REINS);
		int totalACollecter = QUANTITE_PAR_CYCLE_REINS > capacite - totalSubstance ? capacite - totalSubstance : QUANTITE_PAR_CYCLE_REINS;
		totalSubstance += reins.transfereSubstancesVersOrgane(SUBSTANCES_REINS, totalACollecter, this);
		
		// On r�cup�re les d�chets stock�s dans l'estomac
		Organe estomac = getOrganisme().getOrgane(TypeOrgane.ESTOMAC);
		totalACollecter = QUANTITE_PAR_CYCLE_ESTOMAC > capacite - totalSubstance ? capacite - totalSubstance : QUANTITE_PAR_CYCLE_ESTOMAC;
		totalSubstance += estomac.transfereSubstancesVersOrgane(SUBSTANCES_ESTOMAC, totalACollecter, this);
		
		// Mise � jour du syst�me sensoriel
		SystemeSensoriel systemeSensoriel = (SystemeSensoriel) getOrganisme().getOrgane(TypeOrgane.SYSTEME_SENSORIEL);
		systemeSensoriel.setValeurCanal(TypeCanalSensoriel.INTESTINS, totalSubstance * 100 / capacite);
	}
}
