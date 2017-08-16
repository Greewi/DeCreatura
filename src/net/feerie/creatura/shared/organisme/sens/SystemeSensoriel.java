package net.feerie.creatura.shared.organisme.sens;

import static net.feerie.creatura.shared.organisme.Substance.GLUCIDES;
import static net.feerie.creatura.shared.organisme.Substance.TOXINES;

import java.util.EnumMap;

import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.Substance;
import net.feerie.creatura.shared.organisme.organes.Organe;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;

/**
 * Cet organe collecte les informations sensorielles de la créature et les
 * apporte au cerveau.
 * 
 * @author greewi
 */
public class SystemeSensoriel extends Organe
{
	/**
	 * Quantité de glucide consommé par cycle de digestion
	 */
	static final int COUT_PAR_CYCLE = 1;
	
	private final EnumMap<TypeCanalSensoriel, Integer> canauxSensoriels;
	
	public SystemeSensoriel(Organisme organisme)
	{
		super(organisme, TypeOrgane.SYSTEME_SENSORIEL);
		this.canauxSensoriels = new EnumMap<>(TypeCanalSensoriel.class);
	}
	
	/**
	 * Récupère la valeur actuelle d'un canal sensoriel
	 * 
	 * @param canal le canal sensoriel dont on veut la valeur
	 * @return la valeur du canal sensoriel
	 */
	public int getValeurCanal(TypeCanalSensoriel canal)
	{
		Integer valeur = canauxSensoriels.get(canal);
		if (valeur == null)
			return 0;
		return valeur.intValue();
	}
	
	/**
	 * Défini la valeur d'un canal sensoriel
	 * 
	 * @param canal le canal à définir
	 * @param valeur la valeur du canal
	 */
	public void setValeurCanal(TypeCanalSensoriel canal, int valeur)
	{
		canauxSensoriels.put(canal, valeur);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		Organe systemeSanguin = getOrganisme().getOrgane(TypeOrgane.SYSTEME_SANGUIN);
		if(systemeSanguin == null)
			return;

		setValeurCanal(TypeCanalSensoriel.SANTE, getOrganisme().getSante());
		setValeurCanal(TypeCanalSensoriel.ENERGIE, systemeSanguin.getSubstance(Substance.NORALDRENALINE));
		setValeurCanal(TypeCanalSensoriel.MORAL, systemeSanguin.getSubstance(Substance.SEROTONINE));
		setValeurCanal(TypeCanalSensoriel.EVEIL, systemeSanguin.getSubstance(Substance.DOPAMINE));
		setValeurCanal(TypeCanalSensoriel.SATIETE, systemeSanguin.getSubstance(Substance.CHOLECYSTOKININE)*10);
		setValeurCanal(TypeCanalSensoriel.HYDRATATION, systemeSanguin.getSubstance(Substance.EAU));
		
		//Consomme des glucides
		systemeSanguin.transformeSubstance(GLUCIDES, TOXINES, COUT_PAR_CYCLE);
		systemeSanguin.transformeSubstance(Substance.CHOLECYSTOKININE, TOXINES, 1);
	}
}
