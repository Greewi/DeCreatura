package net.feerie.creatura.shared.entites;

import java.util.ArrayList;

import com.google.gwt.user.client.Random;

import net.feerie.creatura.shared.Environnement;
import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.ActionDormir;
import net.feerie.creatura.shared.actions.ActionManger;
import net.feerie.creatura.shared.actions.ActionPopo;
import net.feerie.creatura.shared.actions.ActionSeDeplacer;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.organisme.Organisme;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;
import net.feerie.creatura.shared.organisme.sens.SystemeSensoriel;
import net.feerie.creatura.shared.organisme.sens.TypeCanalSensoriel;

/**
 * Repr�sente une cr�ature
 * 
 * @author greewi
 */
public class Creature extends Entite
{
	private Action action;
	private Environnement environnementActuel;
	
	private final Organisme organisme;
	private SystemeSensoriel systemeSensoriel;
	private long dateDernierCycleMetabolique = 0; 
	
	public Creature(Monde monde, Position position)
	{
		super(monde, position, new Dimension(5.0, 5.0));
		this.organisme = new Organisme();		
		this.systemeSensoriel = (SystemeSensoriel) this.organisme.getOrgane(TypeOrgane.SYSTEME_SENSORIEL);
		
		//Action actuelle
		action = null;
	}
	
	/**
	 * @return l'environnement actuelle de la cr�ature
	 */
	public Environnement getEnvironnementActuel()
	{
		return environnementActuel;
	}
	
	/**
	 * Renvoie la valeur d'un canal sensoriel
	 * @param canal le canal sensoriel d�sir�
	 * @return la valeur du canal sensoriel
	 */
	public int getValeurCanalSensoriel(TypeCanalSensoriel canal)
	{
		return systemeSensoriel.getValeurCanal(canal);
	}
	
	/**
	 * Renvoie l'organisme de la cr�ature
	 * @return l'organisme de la cr�ature
	 */
	public Organisme getOrganisme()
	{
		return this.organisme;
	}
	
	/**
	 * D�fini l'action actuelle que doit entreprendre cette cr�ature
	 * 
	 * @param action l'action que doit entreprendre cette cr�ature
	 */
	public void setActionActuelle(Action action)
	{
		this.action = action;
	}
	
	/**
	 * @return l'action actuellement entreprise par la cr�ature
	 */
	public Action getActionActuelle()
	{
		return action;
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.CREATURE;
	}
	
	@Override
	public void metAJour(int frame)
	{
		//IA et ex�cution des actions
		if (action != null)
		{
			if (!action.metAJour(frame))
				this.action = null;
		}
		else if (frame % 10 == 0)
			this.action = determineAction(frame);
		
		//Mise � jour de l'environnement
		environnementActuel = monde.getEnvironnement(getPosition());
		
		//Mise � jour du metabolisme

		long dateActuelle = System.currentTimeMillis();
		if(dateActuelle >= dateDernierCycleMetabolique+500)
		{
			this.organisme.effectueCycleMetabolique();
			dateDernierCycleMetabolique = dateActuelle;
		}
	}
	
	/**
	 * D�termine la prochaine action � entreprendre
	 */
	private Action determineAction(int frame)
	{
		//Focus al�atoire
		ArrayList<Entite> entites = monde.getListeEntites();
		Entite focus = entites.get(Random.nextInt(entites.size()));
		
		if (focus.getType() == TypeEntite.NOURRITURE && systemeSensoriel.getValeurCanal(TypeCanalSensoriel.SATIETE) < 20)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (focus.getType() == TypeEntite.NOURRITURE && systemeSensoriel.getValeurCanal(TypeCanalSensoriel.SATIETE) < 20)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (focus.getType() == TypeEntite.NOURRITURE && systemeSensoriel.getValeurCanal(TypeCanalSensoriel.SATIETE) < 20)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (focus.getType() == TypeEntite.NOURRITURE && systemeSensoriel.getValeurCanal(TypeCanalSensoriel.SATIETE) < 20)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (systemeSensoriel.getValeurCanal(TypeCanalSensoriel.INTESTINS)>70)
			return new ActionSeDeplacer(this, focus, new ActionPopo(monde, this, focus));
		if (systemeSensoriel.getValeurCanal(TypeCanalSensoriel.ENERGIE)<10)
			return new ActionSeDeplacer(this, focus, new ActionDormir(this, focus));
		return null;
	};
}
