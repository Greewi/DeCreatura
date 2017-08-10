package net.feerie.creatura.shared;

import java.util.ArrayList;
import java.util.HashMap;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.entites.Zone;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;

/**
 * Représente le monde. Il contiendra les entités.
 */
public class Monde
{
	private HashMap<Integer, Entite> entites = new HashMap<>();
	private int IDEntiteSuivante = 1;
	private ArrayList<Zone> zones = new ArrayList<>();
	private Environnement environnementParDefaut;
	//Observateurs d'événements
	private final ArrayList<ObservateurEntiteAjoutee> observateursEntiteAjoutee = new ArrayList<>();
	private final ArrayList<ObservateurEntiteSupprimee> observateursEntiteSuprimee = new ArrayList<>();
	
	/**
	 * Contructeur
	 */
	public Monde(Environnement environnementParDefaut)
	{
		this.environnementParDefaut = environnementParDefaut;
	}
	
	/**
	 * Génère et renvoie un nouvel ID d'entité
	 * 
	 * @return l'ID généré
	 */
	public int genereIDEntite()
	{
		return IDEntiteSuivante++;
	}
	
	/**
	 * Renvoie une entité
	 * 
	 * @param ID l'ID de l'entité qu'on veut avoir
	 * @return l'entité correspondant à l'IA ou <tt>null</tt> sinon.
	 */
	public Entite getEntite(int ID)
	{
		return entites.get(ID);
	}
	
	/**
	 * Ajoute une entité
	 * 
	 * @param entite l'entité à ajouter
	 */
	public void ajouteEntite(Entite entite)
	{
		entites.put(entite.getID(), entite);
		for (ObservateurEntiteAjoutee observateur : observateursEntiteAjoutee)
			observateur.onEntiteAjoutee(entite);
	}
	
	/**
	 * Supprime une entité (ne supprimera pas une Zone !)
	 * 
	 * @param ID l'ID de l'entité à supprimer
	 */
	public void supprimeEntite(int ID)
	{
		if (entites.containsKey(ID))
		{
			Entite entite = entites.get(ID);
			if (entite.getType() == TypeEntite.ZONE) // On ne supprime pas les zones
				return;
			entites.remove(ID);
			for (ObservateurEntiteSupprimee observateur : observateursEntiteSuprimee)
				observateur.onEntiteSuprimee(entite);
		}
	}
	
	/**
	 * Renvoie la liste des entités contenues dans le monde
	 * 
	 * @return la liste des entités contenues dans le monde
	 */
	public ArrayList<Entite> getListeEntites()
	{
		return new ArrayList<>(entites.values());
	}
	
	/**
	 * Renvoie la zone contenant les coordonnées passées en paramètre
	 * 
	 * @param position la position évaluée
	 * @return la zone contenant les coordonnées ou <tt>null</tt>
	 */
	public Zone getZone(Position position)
	{
		for (Zone zone : zones)
			if (zone.contient(position))
				return zone;
		return null;
	}
	
	/**
	 * Ajoute une zone dans le monde
	 * 
	 * @param zone la zone à ajouter
	 */
	public void ajouteZone(Zone zone)
	{
		ajouteEntite(zone);
		zones.add(zone);
	}
	
	/**
	 * Détermine l'environnement à la position donnée
	 * 
	 * @param position la position où on souhaite connaitre l'environnement
	 * @return l'environnement de la position
	 */
	public Environnement getEnvironnement(Position position)
	{
		Zone zone = getZone(position);
		if (zone == null)
			return environnementParDefaut;
		else
			return zone.getEnvironnement();
	}
	
	/**
	 * Met le monde à jour .
	 * 
	 * @param frame le numéro de la nouvelle frame
	 */
	public void metAJour(int frame)
	{
		for (Entite entite : entites.values())
			entite.metAJour(frame);
		//Nettoyage des entités disparues
		for (Entite entite : new ArrayList<>(entites.values()))
			if (!entite.existe())
				supprimeEntite(entite.getID());
	}
	
	/**
	 * Ajoute un observateur sur les événements d'ajout d'entite
	 * 
	 * @param observateur un observateur qui écoutera les ajouts d'entites
	 */
	public void onEntiteAjoutee(ObservateurEntiteAjoutee observateur)
	{
		observateursEntiteAjoutee.add(observateur);
	}
	
	/**
	 * Ajoute un observateur sur les événements de suppressions d'entite
	 * 
	 * @param observateur un observateur qui écoutera les supressions d'entites
	 */
	public void onEntiteSupprimee(ObservateurEntiteSupprimee observateur)
	{
		observateursEntiteSuprimee.add(observateur);
	}
}
