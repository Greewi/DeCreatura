package net.feerie.creatura.shared.organisme.organes;

import java.util.EnumMap;

import net.feerie.creatura.shared.organisme.Organisme;

/**
 * Cet organe collecte les informations sensorielles de la créature et les
 * apporte au cerveau.
 * 
 * @author greewi
 */
public class SystemeSensoriel extends Organe
{
	private final EnumMap<TypeCanalSensoriel, Integer> canauxSensoriels;
	
	public SystemeSensoriel(Organisme organisme)
	{
		super(organisme, TypeOrgane.SYSTEME_SENSORIEL);
		this.canauxSensoriels = new EnumMap<>(TypeCanalSensoriel.class);
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// TODO Auto-generated method stub
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
}
