package net.feerie.creatura.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private List<Entite> nouvellesEntites = new ArrayList<>();
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
	 * Génére et renvoie un nouvel ID d'entité
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
	 * @return l'entité correspondant à l'ID ou <tt>null</tt> sinon.
	 */
	public Entite getEntite(int ID)
	{
		return entites.get(ID);
	}
	
	/**
	 * Récupère la liste des entités à une position
	 * 
	 * @param position
	 * @return
	 */
	public List<Entite> getEntiteAPosition(Position position)
	{
		double xc = position.x;
		double yc = position.y;
		
		List<Entite> entites = new ArrayList<>();
		for (Entite e : getListeEntites())
		{
			if (e.getType() == TypeEntite.ZONE)
				continue;
			
			double xe = e.getPosition().x;
			double ye = e.getPosition().y;
			double rayon = e.getTaille().l / 2;
			
			if ((xc - xe) * (xc - xe) + (yc - ye) * (yc - ye) <= rayon * rayon)
				entites.add(e);
		}
		
		return entites;
	}
	
	/**
	 * Ajoute une entité
	 * 
	 * @param entite l'entité à ajouter
	 */
	void ajouteEntite(Entite entite)
	{
		entites.put(entite.getID(), entite);
		for (ObservateurEntiteAjoutee observateur : observateursEntiteAjoutee)
			observateur.onEntiteAjoutee(entite);
	}
	
	public void nouvelleEntite(Entite entite)
	{
		nouvellesEntites.add(entite);
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
		//Ajout des nouvelles entités
		for (Entite entite : nouvellesEntites)
			ajouteEntite(entite);
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
