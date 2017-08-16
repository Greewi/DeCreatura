package net.feerie.creatura.shared.creature.organes;

import java.util.EnumMap;

import net.feerie.creatura.shared.creature.Organisme;

/**
 * Cet organe collecte les informations sensorielles de la cr�ature et les
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
	 * R�cup�re la valeur actuelle d'un canal sensoriel
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
	 * D�fini la valeur d'un canal sensoriel
	 * 
	 * @param canal le canal � d�finir
	 * @param valeur la valeur du canal
	 */
	public void setValeurCanal(TypeCanalSensoriel canal, int valeur)
	{
		canauxSensoriels.put(canal, valeur);
	}
}
